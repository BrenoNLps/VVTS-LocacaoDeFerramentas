export default function Tabs({ tabs, active, onChange }) {
  return (
    <div className="tabs">
      {tabs.map(tab => (
        <button
          key={tab.key}
          type="button"
          className={`tab-btn${active === tab.key ? " tab-btn--active" : ""}`}
          onClick={() => onChange(tab.key)}
        >
          {tab.label}
          {tab.badge > 0 && <span className="tab-badge">{tab.badge}</span>}
        </button>
      ))}
    </div>
  );
}