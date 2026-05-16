import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import ToolTable from "../../components/ToolTable";
import { api } from "../../services/api";
import { PATHS } from "../../routes/paths";

const _now = new Date();
const today = `${_now.getFullYear()}-${String(_now.getMonth() + 1).padStart(2, "0")}-${String(_now.getDate()).padStart(2, "0")}`;

export default function Home() {
  const [tools, setTools] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);
  const [endDate, setEndDate] = useState("");
  const [simulationValue, setSimulationValue] = useState(null);
  const [simulationError, setSimulationError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/tools")
      .then(data => setTools(data.filter(t => t.status === "AVAILABLE")))
      .catch(() => {});
  }, []);

  function toggleTool(toolId) {
    setSelectedIds(prev =>
      prev.includes(toolId) ? prev.filter(id => id !== toolId) : [...prev, toolId]
    );
    setSimulationValue(null);
  }

  async function handleSimulate() {
    setSimulationError("");
    setSimulationValue(null);
    if (selectedIds.length === 0) { setSimulationError("Selecione ao menos uma ferramenta."); return; }
    if (!endDate) { setSimulationError("Informe a data de devolução."); return; }
    try {
      const value = await api.post("/rentals/query-value", {
        toolIds: selectedIds,
        startDate: today,
        endDate,
      });
      setSimulationValue(value);
    } catch {
      setSimulationError("Erro ao consultar valor.");
    }
  }

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Ferramentas disponíveis</h1>

        <section className="section-form mt-md">
          <ToolTable
            tools={tools}
            selectedIds={selectedIds}
            onToggle={toggleTool}
            onMaintenance={toolId => navigate(`${PATHS.maintenance}?toolId=${toolId}`)}
          />

          <div className="query-controls">
            <div className="field">
              <label className="field-label">Data de devolução</label>
              <input
                type="date"
                value={endDate}
                min={today}
                onChange={e => { setEndDate(e.target.value); setSimulationValue(null); }}
              />
            </div>
            <button type="button" className="btn-auto" onClick={handleSimulate}>
              Simular valor
            </button>
            <button type="button" className="btn-auto" onClick={() => navigate(PATHS.rental)}>
              Ir para locações
            </button>
          </div>

          {simulationError && <p className="error-text">{simulationError}</p>}
          {simulationValue !== null && (
            <p className="query-result">Valor estimado: R$ {Number(simulationValue).toFixed(2)}</p>
          )}
        </section>
      </main>
    </>
  );
}