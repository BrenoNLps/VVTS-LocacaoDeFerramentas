import { useEffect, useState } from "react";
import Header from "../../components/Header";
import { api } from "../../services/api";

const STATUS_LABELS = {
  FINALIZED: "Finalizada",
  CANCELLED: "Cancelada",
};

export default function RentalHistory() {
  const [rentals, setRentals] = useState([]);

  useEffect(() => {
    api.get("/rentals").then(data => setRentals(data.filter(r => r.status !== "ACTIVE"))).catch(() => {});
  }, []);

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Histórico de locações</h1>
        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Ferramentas</th>
              <th>Início</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {rentals.map(rental => (
              <tr key={rental.id}>
                <td title={rental.id}>{rental.id.slice(0, 8)}...</td>
                <td>{rental.tools.map(t => t.name).join(", ")}</td>
                <td>{rental.startDate}</td>
                <td>{STATUS_LABELS[rental.status] ?? rental.status}</td>
              </tr>
            ))}
            {rentals.length === 0 && (
              <tr><td colSpan={4}>Nenhuma locação encerrada.</td></tr>
            )}
          </tbody>
        </table>
      </main>
    </>
  );
}