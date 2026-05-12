import { useState } from "react";
import { Link } from "react-router-dom";
import { PATHS } from "../../routes/paths";

export default function Register() {
  const [form, setForm] = useState({ name: "", lastname: "", email: "", password: "" });

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  return (
    <div className="page-center">
      <div className="card">
        <h1>Cadastro</h1>
        <form>
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
          <button type="submit">Cadastrar</button>
        </form>
        <p>Já tem conta? <Link to={PATHS.login}>Entrar</Link></p>
      </div>
    </div>
  );
}
