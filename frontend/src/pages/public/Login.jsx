import { Link } from "react-router-dom";
import { PATHS } from "../../routes/paths";

export default function Login() {
  return (
    <div className="page-center">
      <div className="card">
        <h1>Login</h1>
        <form>
          <input type="email" placeholder="Email" />
          <input type="password" placeholder="Senha" />
          <button type="submit">Entrar</button>
        </form>
        <p>Não tem conta? <Link to={PATHS.register}>Cadastre-se</Link></p>
      </div>
    </div>
  );
}
