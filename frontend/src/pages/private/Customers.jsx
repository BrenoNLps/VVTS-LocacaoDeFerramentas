import { useEffect, useState } from "react";
import Header from "../../components/Header";
import { api } from "../../services/api";

export default function Customers() {
  const [customers, setCustomers] = useState([]);
  const [name, setName] = useState("");
  const [loadError, setLoadError] = useState("");
  const [submitError, setSubmitError] = useState("");

  useEffect(() => {
    loadCustomers();
  }, []);

  function loadCustomers() {
    setLoadError("");
    api.get("/customers")
      .then(data => setCustomers(data ?? []))
      .catch(err => {
        console.error("GET /customers falhou:", err);
        setLoadError("Erro ao carregar clientes. Verifique o console.");
      });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setSubmitError("");
    try {
      await api.post("/customers", { name });
      setName("");
      loadCustomers();
    } catch (err) {
      console.error("POST /customers falhou:", err);
      setSubmitError("Erro ao cadastrar cliente. Verifique o console.");
    }
  }

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Clientes</h1>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Nome do cliente"
            value={name}
            onChange={e => setName(e.target.value)}
            required
          />
          {submitError && <p className="error-text">{submitError}</p>}
          <button type="submit">Cadastrar</button>
        </form>
        {loadError && <p className="error-text mt-md">{loadError}</p>}
        <div className="table-wrapper mt-md">
          <table className="data-table">
            <thead>
              <tr>
                <th>Nome</th>
                <th>ID</th>
              </tr>
            </thead>
            <tbody>
              {customers.map(c => (
                <tr key={c.id}>
                  <td>{c.name}</td>
                  <td>{c.id}</td>
                </tr>
              ))}
              {customers.length === 0 && (
                <tr><td colSpan={2}>Nenhum cliente cadastrado.</td></tr>
              )}
            </tbody>
          </table>
        </div>
      </main>
    </>
  );
}
