import { Link, useNavigate } from "react-router-dom";
import { PATHS } from "../routes/paths";

export default function Header() {
  const navigate = useNavigate();

  function handleLogout() {
    localStorage.removeItem("token");
    navigate(PATHS.login);
  }

  return (
    <header className="header">
      <nav className="header-nav">
        <Link to={PATHS.home}>Home</Link>
        <Link to={PATHS.rental}>Locações</Link>
        <Link to={PATHS.maintenance}>Manutenções</Link>
      </nav>
      <button className="header-logout" onClick={handleLogout}>Sair</button>
    </header>
  );
}
