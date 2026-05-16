import { Navigate } from "react-router-dom";
import { PATHS } from "../routes/paths";

export default function PublicRoute({ children }) {
  const token = localStorage.getItem("token");
  if (token) {
    return <Navigate to={PATHS.home} replace />;
  }
  return children;
}
