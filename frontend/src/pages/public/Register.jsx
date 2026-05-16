import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PATHS } from "../../routes/paths";
import { api } from "../../services/api";

export default function Register() {
  const [form, setForm] = useState({ name: "", lastname: "", email: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    try {
      await api.post("/register", form);
      navigate(PATHS.login);
    } catch {
      setError("Erro ao cadastrar. Verifique os dados e tente novamente.");
    }
  }

  return (
    <div className="page-center">
      <div className="card">
        <h1>Cadastro</h1>
        <form onSubmit={handleSubmit}>
          <input type="text" name="name" placeholder="Nome"
            value={form.name} onChange={handleChange}
          />
          <input type="text" name="lastname" placeholder="Sobrenome"
            value={form.lastname} onChange={handleChange}
          />
          <input type="email" name="email" placeholder="Email"
            value={form.email} onChange={handleChange}
          />
          <input type="password" name="password" placeholder="Senha"
            value={form.password} onChange={handleChange}
          />
          {error && <p>{error}</p>}
          <button type="submit">Cadastrar</button>
        </form>
        <p>Já tem conta? <Link to={PATHS.login}>Entrar</Link></p>
      </div>
    </div>
  );
}
