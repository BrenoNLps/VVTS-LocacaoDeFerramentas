import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { api } from "../../services/api";
import { PATHS } from "../../routes/paths";

export default function Home() {
  const [tools, setTools] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/tools")
      .then(data => setTools(data.filter(t => t.status === "AVAILABLE")))
      .catch(() => setError("Erro ao carregar ferramentas."));
  }, []);

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Ferramentas disponíveis</h1>
        {error && <p>{error}</p>}
        <table className="data-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            {tools.map(tool => (
              <tr key={tool.id}>
                <td>{tool.name}</td>
                <td className="table-actions">
                  <button onClick={() => navigate(PATHS.rental)}>Locar</button>
                  <button onClick={() => navigate(`${PATHS.maintenance}?toolId=${tool.id}`)}>Manutenção</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </main>
    </>
  );
}
