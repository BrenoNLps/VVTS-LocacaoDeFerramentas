import { useEffect, useState } from "react";
import Header from "../../components/Header";
import ToolTable from "../../components/ToolTable";
import { api } from "../../services/api";

export default function Rental() {
  const [tools, setTools] = useState([]);
  const [selectedToolIds, setSelectedToolIds] = useState([]);

  useEffect(() => {
    api.get("/tools")
      .then(data => setTools(data.filter(t => t.status === "AVAILABLE")))
      .catch(() => {});
  }, []);

  function toggleTool(toolId) {
    setSelectedToolIds(prev =>
      prev.includes(toolId) ? prev.filter(id => id !== toolId) : [...prev, toolId]
    );
  }

  return (
    <>
      <Header />
      <main className="page-content">
        <h1>Locações</h1>
        <section className="section-form mt-xl">
          <h2>Nova locação</h2>
          <ToolTable tools={tools} selectedIds={selectedToolIds} onToggle={toggleTool} />
        </section>
      </main>
    </>
  );
}