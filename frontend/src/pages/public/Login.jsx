import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PATHS } from "../../routes/paths";
import { api } from "../../services/api";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    try {
      const data = await api.post("/authenticate", form);
      localStorage.setItem("token", data.token);
      navigate(PATHS.home);
    } catch {
      setError("Email ou senha inválidos.");
    }
  }

  return (
    <div className="page-center">
      <div className="card">
        <h1>Login</h1>
        <form onSubmit={handleSubmit}>
          <input type="email" name="username" placeholder="Email"
            value={form.username} onChange={handleChange}
          />
          <input type="password" name="password" placeholder="Senha"
            value={form.password} onChange={handleChange}
          />
          {error && <p>{error}</p>}
          <button type="submit">Entrar</button>
        </form>
        <p>Não tem conta? <Link to={PATHS.register}>Cadastre-se</Link></p>
      </div>
    </div>
  );
}
