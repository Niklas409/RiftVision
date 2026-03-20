import { useState } from "react";
import "./App.css";
import LoginForm from "./components/LoginForm";
import RegisterForm from "./components/RegisterForm";
import MatchList from "./components/MatchList";
import LinkedAccountsList from "./components/LinkedAccountsList";

function App() {
  const [showRegister, setShowRegister] = useState(false);
  const [matchRefreshKey, setMatchRefreshKey] = useState(0);

  const token = localStorage.getItem("token");

  function handleLogout() {
    localStorage.removeItem("token");
    window.location.reload();
  }

  function handleMatchesImported() {
    setMatchRefreshKey((prev) => prev + 1);
  }

  if (token) {
    return (
      <div style={{ maxWidth: "900px", margin: "0 auto", padding: "24px" }}>
        <h1>RiftVision</h1>
        <p>Du bist eingeloggt.</p>

        <button onClick={handleLogout} style={{ marginBottom: "20px" }}>
          Logout
        </button>

        <LinkedAccountsList
          token={token}
          onImportSuccess={handleMatchesImported}
        />

        <MatchList token={token} refreshKey={matchRefreshKey} />
      </div>
    );
  }

  return (
    <div style={{ maxWidth: "500px", margin: "0 auto", padding: "24px" }}>
      <h1>RiftVision</h1>

      {showRegister ? <RegisterForm /> : <LoginForm />}

      <div style={{ marginTop: "16px" }}>
        {showRegister ? (
          <p>
            Schon einen Account?{" "}
            <button onClick={() => setShowRegister(false)}>Zum Login</button>
          </p>
        ) : (
          <p>
            Noch keinen Account?{" "}
            <button onClick={() => setShowRegister(true)}>Registrieren</button>
          </p>
        )}
      </div>
    </div>
  );
}

export default App;
