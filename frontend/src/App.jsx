import { BrowserRouter, Routes, Route } from "react-router-dom";
import { PATHS } from "./routes/paths";
import { Login, Register, Home, Rental, Maintenance } from "./pages";
import PrivateRoute from "./components/PrivateRoute";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={PATHS.login} element={<Login />} />
        <Route path={PATHS.register} element={<Register />} />
        <Route path={PATHS.home} element={<PrivateRoute><Home /></PrivateRoute>} />
        <Route path={PATHS.rental} element={<PrivateRoute><Rental /></PrivateRoute>} />
        <Route path={PATHS.maintenance} element={<PrivateRoute><Maintenance /></PrivateRoute>} />
      </Routes>
    </BrowserRouter>
  );
}
