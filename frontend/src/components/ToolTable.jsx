export default function ToolTable({ tools, selectedIds, onToggle, onMaintenance }) {
  return (
    <div className="tool-table-container">
      <table className="data-table">
        <thead>
          <tr>
            <th className="col-checkbox"></th>
            <th>Nome</th>
            <th>Diária</th>
            <th>Semanal</th>
            <th>Mensal</th>
            {onMaintenance && <th></th>}
          </tr>
        </thead>
        <tbody>
          {tools.map(tool => (
            <tr
              key={tool.id}
              className={`tool-row${selectedIds.includes(tool.id) ? " tool-row--selected" : ""}`}
              onClick={() => onToggle(tool.id)}
            >
              <td>
                <input
                  type="checkbox"
                  checked={selectedIds.includes(tool.id)}
                  onChange={() => onToggle(tool.id)}
                  onClick={e => e.stopPropagation()}
                />
              </td>
              <td>{tool.name}</td>
              <td>R$ {tool.dailyRate}</td>
              <td>R$ {tool.weeklyDailyRate}</td>
              <td>R$ {tool.monthlyDailyRate}</td>
              {onMaintenance && (
                <td>
                  <button
                    className="btn-auto"
                    onClick={e => { e.stopPropagation(); onMaintenance(tool.id); }}
                  >
                    Manutenção
                  </button>
                </td>
              )}
            </tr>
          ))}
          {tools.length === 0 && (
            <tr><td colSpan={onMaintenance ? 6 : 5}>Nenhuma ferramenta disponível.</td></tr>
          )}
        </tbody>
      </table>
    </div>
  );
}