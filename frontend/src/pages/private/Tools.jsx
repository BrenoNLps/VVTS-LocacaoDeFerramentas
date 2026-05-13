import { useEffect, useState } from "react";
import Header from "../../components/Header";
import { api } from "../../services/api";

export default function Tools() {
  const [tools, setTools] = useState([]);
  const [form, setForm] = useState({ name: "", dailyRate: "", weeklyDailyRate: "", monthlyDailyRate: "" });
  const [error, setError] = useState("");

  useEffect(() => {
    loadTools();
  }, []);

  function loadTools() {
    api.get("/tools")
      .then(setTools)
      .catch(() => setError("Erro ao carregar ferramentas."));
  }

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    try {
      await api.post("/tools", {
        name: form.name,
        dailyRate: parseFloat(form.dailyRate),
        weeklyDailyRate: parseFloat(form.weeklyDailyRate),
        monthlyDailyRate: parseFloat(form.monthlyDailyRate),
      });
      setForm({ name: "", dailyRate: "", weeklyDailyRate: "", monthlyDailyRate: "" });
      loadTools();
    } catch {
      setError("Erro ao cadastrar ferramenta.");
    }
  }

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Ferramentas</h1>
        <form onSubmit={handleSubmit}>
          <input type="text" name="name" placeholder="Nome" value={form.name} onChange={handleChange} required />
          <input type="number" name="dailyRate" placeholder="Diária (R$)" value={form.dailyRate} onChange={handleChange} required />
          <input type="number" name="weeklyDailyRate" placeholder="Semanal (R$)" value={form.weeklyDailyRate} onChange={handleChange} required />
          <input type="number" name="monthlyDailyRate" placeholder="Mensal (R$)" value={form.monthlyDailyRate} onChange={handleChange} required />
          {error && <p>{error}</p>}
          <button type="submit">Cadastrar</button>
        </form>
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