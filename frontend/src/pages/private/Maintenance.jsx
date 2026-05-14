import Header from "../../components/Header";

export default function Maintenance() {
  const available = [];
  const inMaintenance = [];

  return (
    <>
      <Header />
      <main className="page-content">
        <h2>Disponíveis para manutenção</h2>
        <table className="data-table data-table--fixed">
          <colgroup>
            <col style={{ width: "50%" }} />
            <col style={{ width: "50%" }} />
          </colgroup>
          <thead>
            <tr>
              <th>Nome</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {available.length === 0 && (
              <tr><td colSpan={2}>Nenhuma ferramenta disponível.</td></tr>
            )}
          </tbody>
        </table>

        <h2 style={{ marginTop: "var(--spacing-xl)" }}>Em manutenção</h2>
        <table className="data-table data-table--fixed">
          <colgroup>
            <col style={{ width: "50%" }} />
            <col style={{ width: "50%" }} />
          </colgroup>
          <thead>
            <tr>
              <th>Nome</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {inMaintenance.length === 0 && (
              <tr><td colSpan={2}>Nenhuma ferramenta em manutenção.</td></tr>
            )}
          </tbody>
        </table>
      </main>
    </>
  );
}