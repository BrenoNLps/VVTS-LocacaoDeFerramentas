import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PATHS } from "../routes/paths";

export default function Header() {
  const [menuOpen, setMenuOpen] = useState(false);
  const navigate = useNavigate();

  function handleLogout() {
    localStorage.removeItem("token");
    navigate(PATHS.login);
  }

  function closeMenu() {
    setMenuOpen(false);
  }

  return (
    <>
      {menuOpen && <div className="nav-backdrop" onClick={closeMenu} />}
      <header className="header">
        <button className="header-hamburger" onClick={() => setMenuOpen(prev => !prev)} aria-label="Menu">
          <span></span>
          <span></span>
          <span></span>
        </button>

        <nav className={`header-nav${menuOpen ? " header-nav--open" : ""}`}>
          <Link to={PATHS.home} onClick={closeMenu}>Home</Link>
          <Link to={PATHS.tools} onClick={closeMenu}>Ferramentas</Link>
          <Link to={PATHS.rental} onClick={closeMenu}>Locações</Link>
          <Link to={PATHS.maintenance} onClick={closeMenu}>Manutenções</Link>
          <Link to={PATHS.rentalHistory} onClick={closeMenu}>Histórico</Link>
          <Link to={PATHS.customers} onClick={closeMenu}>Clientes</Link>
        </nav>

        <button className="header-logout" onClick={handleLogout}>Sair</button>
      </header>
    </>
  );
}