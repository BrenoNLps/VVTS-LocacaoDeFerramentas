import { useEffect, useState } from "react";
import Header from "../../components/Header";
import ToolTable from "../../components/ToolTable";
import { api } from "../../services/api";

const today = new Date().toISOString().split("T")[0];

const GUARANTEE_OPTIONS = [
  { value: "PROMISSORY_NOTE", label: "Promissória" },
  { value: "CREDIT_CARD", label: "Reserva em cartão" },
  { value: "CASH_DEPOSIT", label: "Dinheiro" },
];

export default function Rental() {
  const [tools, setTools] = useState([]);
  const [selectedToolIds, setSelectedToolIds] = useState([]);
  const [customerName, setCustomerName] = useState("");
  const [guarantee, setGuarantee] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  useEffect(() => {
    loadTools();
  }, []);

  function loadTools() {
    api.get("/tools")
      .then(data => setTools(data.filter(t => t.status === "AVAILABLE")))
      .catch(() => {});
  }

  function toggleTool(toolId) {
    setSelectedToolIds(prev =>
      prev.includes(toolId) ? prev.filter(id => id !== toolId) : [...prev, toolId]
    );
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setSuccess("");
    if (selectedToolIds.length === 0) { setError("Selecione ao menos uma ferramenta."); return; }
    if (!customerName.trim()) { setError("Informe o nome do cliente."); return; }
    if (!guarantee) { setError("Selecione o tipo de garantia."); return; }
    try {
      await api.post("/rentals", {
        customerName: customerName.trim(),
        toolIds: selectedToolIds,
        startDate: today,
        guaranteeType: guarantee,
      });
      setSuccess("Locação registrada com sucesso.");
      setSelectedToolIds([]);
      setCustomerName("");
      setGuarantee("");
      loadTools();
    } catch {
      setError("Erro ao registrar locação.");
    }
  }

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Locações</h1>

        <section className="section-form mt-xl">
          <h2>Nova locação</h2>
          <form onSubmit={handleSubmit}>
            <ToolTable tools={tools} selectedIds={selectedToolIds} onToggle={toggleTool} />

            <div className="mt-md">
              <label className="field-label">Cliente</label>
              <input
                type="text"
                placeholder="Nome do cliente..."
                value={customerName}
                onChange={e => setCustomerName(e.target.value)}
              />
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

      </main>
    </>
  );
}