import { useState } from "react";
import { Link } from "react-router-dom";
import { PATHS } from "../../routes/paths";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  return (
    <div className="page-center">
      <div className="card">
        <h1>Login</h1>
        <form>
          <input type="email" name="username" placeholder="Email"
            value={form.username} onChange={handleChange}
          />
          <input type="password" name="password" placeholder="Senha"
            value={form.password} onChange={handleChange}
          />
          <button type="submit">Entrar</button>
        </form>
        <p>Não tem conta? <Link to={PATHS.register}>Cadastre-se</Link></p>
      </div>
    </div>
  );
}
