import { useEffect, useState } from "react";
import Header from "../../components/Header";
import { api } from "../../services/api";

export default function Maintenance() {
  const [tools, setTools] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    loadTools();
  }, []);

  function loadTools() {
    api.get("/tools")
      .then(data => setTools(data.filter(t => t.status === "AVAILABLE" || t.status === "MAINTENANCE")))
      .catch(() => setError("Erro ao carregar ferramentas."));
  }

  async function sendToMaintenance(toolId) {
    try {
      await api.post(`/maintenance/send/${toolId}`);
      loadTools();
    } catch {
      setError("Erro ao enviar ferramenta para manutenção.");
    }
  }

  async function returnFromMaintenance(toolId) {
    try {
      await api.put(`/maintenance/return/${toolId}`);
      loadTools();
    } catch {
      setError("Erro ao registrar retorno da manutenção.");
    }
  }

  const available = tools.filter(t => t.status === "AVAILABLE");
  const inMaintenance = tools.filter(t => t.status === "MAINTENANCE");

  return (
    <>
      <Header />
      <main className="page-content">
        {error && <p>{error}</p>}

        <h2>Disponíveis para manutenção</h2>
        <table className="data-table data-table--fixed">
          <colgroup>
            <col style={{ width: "50%" }} />
            <col style={{ width: "50%" }} />
          </colgroup>
          <thead>
            <tr>
              <th>Nome</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {available.map(tool => (
              <tr key={tool.id}>
                <td>{tool.name}</td>
                <td>
                  <button className="btn-success" onClick={() => sendToMaintenance(tool.id)}>
                    Enviar para manutenção
                  </button>
                </td>
              </tr>
            ))}
            {available.length === 0 && (
              <tr><td colSpan={2}>Nenhuma ferramenta disponível.</td></tr>
            )}
          </tbody>
        </table>

        <h2 style={{ marginTop: "var(--spacing-xl)" }}>Em manutenção</h2>
        <table className="data-table data-table--fixed">
          <colgroup>
            <col style={{ width: "50%" }} />
            <col style={{ width: "50%" }} />
          </colgroup>
          <thead>
            <tr>
              <th>Nome</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {inMaintenance.map(tool => (
              <tr key={tool.id}>
                <td>{tool.name}</td>
                <td>
                  <button className="btn-warning" onClick={() => returnFromMaintenance(tool.id)}>
                    Registrar retorno
                  </button>
                </td>
              </tr>
            ))}
            {inMaintenance.length === 0 && (
              <tr><td colSpan={2}>Nenhuma ferramenta em manutenção.</td></tr>
            )}
          </tbody>
        </table>
      </main>
    </>
  );
}