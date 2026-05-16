import { Navigate } from "react-router-dom";
import { PATHS } from "../routes/paths";

export default function PrivateRoute({ children }) {
  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to={PATHS.login} replace />;
  }

  return children;
}
