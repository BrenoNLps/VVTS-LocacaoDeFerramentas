import { BrowserRouter, Routes, Route } from "react-router-dom";
import { PATHS } from "./routes/paths";
import { Login, Register, Home, Tools, Rental, Maintenance } from "./pages";
import PrivateRoute from "./components/PrivateRoute";
import PublicRoute from "./components/PublicRoute";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={PATHS.login} element={<PublicRoute><Login /></PublicRoute>} />
        <Route path={PATHS.register} element={<PublicRoute><Register /></PublicRoute>} />
        <Route path={PATHS.home} element={<PrivateRoute><Home /></PrivateRoute>} />
        <Route path={PATHS.tools} element={<PrivateRoute><Tools /></PrivateRoute>} />
        <Route path={PATHS.rental} element={<PrivateRoute><Rental /></PrivateRoute>} />
        <Route path={PATHS.maintenance} element={<PrivateRoute><Maintenance /></PrivateRoute>} />
      </Routes>
    </BrowserRouter>
  );
}
