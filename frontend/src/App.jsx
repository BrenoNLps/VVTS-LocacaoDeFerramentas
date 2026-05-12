import { BrowserRouter, Routes, Route } from "react-router-dom";
import { PATHS } from "./routes/paths";
import { Login, Register, Home, Rental, Maintenance } from "./pages";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={PATHS.login} element={<Login />} />
        <Route path={PATHS.register} element={<Register />} />
        <Route path={PATHS.home} element={<Home />} />
        <Route path={PATHS.rental} element={<Rental />} />
        <Route path={PATHS.maintenance} element={<Maintenance />} />
      </Routes>
    </BrowserRouter>
  );
}
