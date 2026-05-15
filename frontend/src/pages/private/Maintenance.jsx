import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Header from "../../components/Header";
import Tabs from "../../components/Tabs";
import { api } from "../../services/api";

const TABS = [
  { key: "available", label: "Disponíveis" },
  { key: "maintenance", label: "Manutenções ativas" },
];

export default function Maintenance() {
  const [tab, setTab] = useState("available");
  const [tools, setTools] = useState([]);
  const [error, setError] = useState("");
  const [searchParams] = useSearchParams();
  const preSelectedId = searchParams.get("toolId");

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

  const tabs = TABS.map(t =>
    t.key === "maintenance" ? { ...t, badge: inMaintenance.length } : t
  );

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Manutenção</h1>
        {error && <p className="error-text">{error}</p>}

        <Tabs tabs={tabs} active={tab} onChange={setTab} />

        {tab === "available" && (
          <section className="mt-xl">
            <div className="table-wrapper">
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
                    <tr key={tool.id} className={tool.id === preSelectedId ? "row-selected" : ""}>
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
            </div>
          </section>
        )}

        {tab === "maintenance" && (
          <section className="mt-xl">
            <div className="table-wrapper">
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
            </div>
          </section>
        )}
      </main>
    </>
  );
}