import { useEffect, useState } from "react";
import Header from "../../components/Header";
import { api } from "../../services/api";

export default function Tools() {
  const [tools, setTools] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/tools")
      .then(setTools)
      .catch(() => setError("Erro ao carregar ferramentas."));
  }, []);

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Ferramentas</h1>
        {error && <p>{error}</p>}
        <table className="data-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Status</th>
              <th>Diária</th>
              <th>Semanal</th>
              <th>Mensal</th>
            </tr>
          </thead>
          <tbody>
            {tools.map(tool => (
              <tr key={tool.id}>
                <td>{tool.name}</td>
                <td>{tool.status}</td>
                <td>R$ {tool.dailyRate}</td>
                <td>R$ {tool.weeklyDailyRate}</td>
                <td>R$ {tool.monthlyDailyRate}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </main>
    </>
  );
}