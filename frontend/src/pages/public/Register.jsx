import { Link } from "react-router-dom";
import { PATHS } from "../../routes/paths";

export default function Register() {
  return (
    <div className="page-center">
      <div className="card">
        <h1>Cadastro</h1>
        <form>
          <input type="text" placeholder="Nome" />
          <input type="text" placeholder="Sobrenome" />
          <input type="email" placeholder="Email" />
          <input type="password" placeholder="Senha" />
          <button type="submit">Cadastrar</button>
        </form>
        <p>Já tem conta? <Link to={PATHS.login}>Entrar</Link></p>
      </div>
    </div>
  );
}
