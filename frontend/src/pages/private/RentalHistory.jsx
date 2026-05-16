import { useEffect, useState } from "react";
import Header from "../../components/Header";
import Tabs from "../../components/Tabs";
import { api } from "../../services/api";

const STATUS_LABELS = {
  FINALIZED: "Finalizada",
  CANCELLED: "Cancelada",
};

const TABS = [
  { key: "rentals", label: "Locações" },
  { key: "maintenance", label: "Manutenções" },
];

export default function RentalHistory() {
  const [tab, setTab] = useState("rentals");
  const [rentals, setRentals] = useState([]);
  const [maintenances, setMaintenances] = useState([]);

  useEffect(() => {
    api.get("/rentals").then(data => setRentals(data.filter(r => r.status !== "ACTIVE"))).catch(() => {});
    api.get("/maintenance").then(data => setMaintenances(data.filter(m => m.closed))).catch(() => {});
  }, []);

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Histórico</h1>

        <Tabs tabs={TABS} active={tab} onChange={setTab} />

        {tab === "rentals" && (
          <section className="mt-xl">
            <div className="table-wrapper">
              <table className="data-table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Ferramentas</th>
                    <th>Início</th>
                    <th>Fim</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {rentals.map(rental => (
                    <tr key={rental.id}>
                      <td title={rental.id}>{rental.id.slice(0, 8)}...</td>
                      <td>{rental.tools.map(t => t.name).join(", ")}</td>
                      <td>{rental.startDate}</td>
                      <td>{rental.endDate ?? "—"}</td>
                      <td>{STATUS_LABELS[rental.status] ?? rental.status}</td>
                    </tr>
                  ))}
                  {rentals.length === 0 && (
                    <tr><td colSpan={5}>Nenhuma locação encerrada.</td></tr>
                  )}
                </tbody>
              </table>
            </div>
          </section>
        )}

        {tab === "maintenance" && (
          <section className="mt-xl">
            <div className="table-wrapper">
              <table className="data-table">
                <thead>
                  <tr>
                    <th>Ferramenta</th>
                    <th>Envio</th>
                    <th>Retorno</th>
                  </tr>
                </thead>
                <tbody>
                  {maintenances.map(m => (
                    <tr key={m.id}>
                      <td>{m.toolName}</td>
                      <td>{m.sentDate}</td>
                      <td>{m.returnDate}</td>
                    </tr>
                  ))}
                  {maintenances.length === 0 && (
                    <tr><td colSpan={3}>Nenhuma manutenção encerrada.</td></tr>
                  )}
                </tbody>
              </table>
            </div>
          </section>
        )}
      </main>
    </>
  );
}