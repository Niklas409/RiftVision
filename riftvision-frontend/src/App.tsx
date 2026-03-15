import LoginForm from "./components/LoginForm";
import RegisterForm from "./components/RegisterForm";
import { useState } from "react";

function App() {
  const token = localStorage.getItem("token");
  const [showRegister, setShowRegister] = useState(false);

  return (
    <div style={{ padding: "40px", fontFamily: "Arial" }}>
      <h1>RiftVision</h1>
      <p>League of Legends Coaching-Plattform</p>

      {!token && !showRegister && (
        <>
          <LoginForm />

          <p style={{ marginTop: "20px" }}>Noch kein Account?</p>

          <button onClick={() => setShowRegister(true)}>Registrieren</button>
        </>
      )}

      {!token && showRegister && (
        <>
          <RegisterForm />

          <p style={{ marginTop: "20px" }}>Bereits einen Account?</p>

          <button onClick={() => setShowRegister(false)}>Zum Login</button>
        </>
      )}

      {token && (
        <div>
          <h2>Du bist eingeloggt</h2>

          <button
            onClick={() => {
              localStorage.removeItem("token");
              window.location.reload();
            }}
          >
            Logout
          </button>
        </div>
      )}
    </div>
  );
}

export default App;
