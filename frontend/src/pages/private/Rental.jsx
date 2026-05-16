import { useEffect, useRef, useState } from "react";
import Header from "../../components/Header";
import Tabs from "../../components/Tabs";
import ToolTable from "../../components/ToolTable";
import { api } from "../../services/api";

const now = new Date();
const today = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;

const GUARANTEE_OPTIONS = [
  { value: "PROMISSORY_NOTE", label: "Promissória" },
  { value: "CREDIT_CARD", label: "Reserva em cartão" },
  { value: "CASH_DEPOSIT", label: "Dinheiro" },
];

const TABS = [
  { key: "new", label: "Nova locação" },
  { key: "active", label: "Locações ativas" },
];

export default function Rental() {
  const [tab, setTab] = useState("new");
  const [tools, setTools] = useState([]);
  const [selectedToolIds, setSelectedToolIds] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [customerSearch, setCustomerSearch] = useState("");
  const [selectedCustomer, setSelectedCustomer] = useState(null);
  const [showDropdown, setShowDropdown] = useState(false);
  const [guarantee, setGuarantee] = useState("");
  const [activeRentals, setActiveRentals] = useState([]);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const customerRef = useRef(null);

  useEffect(() => {
    loadTools();
    loadCustomers();
    loadActiveRentals();
  }, []);

  useEffect(() => {
    function handleClickOutside(e) {
      if (customerRef.current && !customerRef.current.contains(e.target)) {
        setShowDropdown(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  function loadTools() {
    api.get("/tools")
      .then(data => setTools(data.filter(t => t.status === "AVAILABLE")))
      .catch(() => {});
  }

  function loadCustomers() {
    api.get("/customers")
      .then(data => setCustomers(data ?? []))
      .catch(() => {});
  }

  function loadActiveRentals() {
    api.get("/rentals")
      .then(data => setActiveRentals(data.filter(r => r.status === "ACTIVE")))
      .catch(() => {});
  }

  function toggleTool(toolId) {
    setSelectedToolIds(prev =>
      prev.includes(toolId) ? prev.filter(id => id !== toolId) : [...prev, toolId]
    );
  }

  function selectCustomer(customer) {
    setSelectedCustomer(customer);
    setCustomerSearch(customer.name);
    setShowDropdown(false);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setSuccess("");
    if (selectedToolIds.length === 0) { setError("Selecione ao menos uma ferramenta."); return; }
    if (!selectedCustomer) { setError("Selecione um cliente."); return; }
    if (!guarantee) { setError("Selecione o tipo de garantia."); return; }
    try {
      await api.post("/rentals", {
        customerId: selectedCustomer.id,
        toolIds: selectedToolIds,
        startDate: today,
        guaranteeType: guarantee,
      });
      setSuccess("Locação registrada com sucesso.");
      setSelectedToolIds([]);
      setSelectedCustomer(null);
      setCustomerSearch("");
      setGuarantee("");
      loadTools();
      loadActiveRentals();
    } catch {
      setError("Erro ao registrar locação.");
    }
  }

  async function finalizeRental(rentalId) {
    setError("");
    setSuccess("");
    try {
      const total = await api.put(`/rentals/${rentalId}/finalize`, { endDate: today });
      loadActiveRentals();
      loadTools();
      setSuccess(`Locação finalizada. Valor total: R$ ${Number(total).toFixed(2).replace(".", ",")}`);
    } catch {
      setError("Erro ao finalizar locação.");
    }
  }

  const filteredCustomers = customerSearch.trim()
    ? customers.filter(c => c.name.toLowerCase().includes(customerSearch.toLowerCase()))
    : customers;

  const tabs = TABS.map(t =>
    t.key === "active" ? { ...t, badge: activeRentals.length } : t
  );

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Locações</h1>

        <Tabs
          tabs={tabs}
          active={tab}
          onChange={key => { setTab(key); setError(""); setSuccess(""); }}
        />

        {tab === "new" && (
          <section className="section-form mt-xl">
            <form onSubmit={handleSubmit}>
              <ToolTable tools={tools} selectedIds={selectedToolIds} onToggle={toggleTool} />

              <div className="customer-field mt-md" ref={customerRef}>
                <label className="field-label">Cliente</label>
                <input
                  type="text"
                  placeholder="Buscar pelo nome..."
                  value={customerSearch}
                  autoComplete="off"
                  onChange={e => {
                    setCustomerSearch(e.target.value);
                    setSelectedCustomer(null);
                    setShowDropdown(true);
                    setSuccess("");
                  }}
                  onFocus={() => setShowDropdown(true)}
                />
                {showDropdown && customerSearch.trim() && !selectedCustomer && (
                  <ul className="customer-dropdown">
                    {filteredCustomers.length > 0
                      ? filteredCustomers.map(c => (
                          <li key={c.id} className="customer-dropdown__item" onClick={() => selectCustomer(c)}>
                            {c.name}
                          </li>
                        ))
                      : <li className="customer-dropdown__item">Nenhum cliente encontrado.</li>
                    }
                  </ul>
                )}
              </div>

              <div className="field mt-md">
                <label className="field-label">Garantia</label>
                <div className="radio-group">
                  {GUARANTEE_OPTIONS.map(opt => (
                    <label key={opt.value} className="radio-label">
                      <input
                        type="radio"
                        name="guarantee"
                        value={opt.value}
                        checked={guarantee === opt.value}
                        onChange={e => setGuarantee(e.target.value)}
                      />
                      {opt.label}
                    </label>
                  ))}
                </div>
              </div>

              {error && <p className="error-text">{error}</p>}
              {success && <p className="success-text">{success}</p>}
              <button type="submit" className="btn-auto">Confirmar locação</button>
            </form>
          </section>
        )}

        {tab === "active" && (
          <section className="mt-xl">
            {error && <p className="error-text">{error}</p>}
            {success && <p className="success-text">{success}</p>}
            <div className="table-wrapper">
              <table className="data-table">
                <thead>
                  <tr>
                    <th>Ferramentas</th>
                    <th>Início</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {activeRentals.map(rental => (
                    <tr key={rental.id}>
                      <td>{rental.tools.map(t => t.name).join(", ")}</td>
                      <td>{rental.startDate}</td>
                      <td>
                        <button className="btn-primary" onClick={() => finalizeRental(rental.id)}>
                          Finalizar
                        </button>
                      </td>
                    </tr>
                  ))}
                  {activeRentals.length === 0 && (
                    <tr><td colSpan={3}>Nenhuma locação ativa.</td></tr>
                  )}
                </tbody>
              </table>
            </div>
          </section>
        )}
      </main>
    </>
  );
}