import { useState } from "react";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    setError("");
    setLoading(true);
    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: email,
          password: password,
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        setError(data.message || "Login fehlgeschlagen");
        setLoading(false);
        return;
      }

      console.log("Login Response:", data);

      localStorage.setItem("token", data.data.token);
      console.log("Gespeicherter Token:", data.data.token);
      window.location.reload();
    } catch (error) {
      console.error("Login Fehler:", error);
      setError("Server nicht erreichbar");
      setLoading(false);
    }
  };

  return (
    <div style={{ marginTop: "40px" }}>
      {loading ? "Lädt..." : "Anmelden"}

      {error && <p style={{ color: "red" }}>{error}</p>}

      <input
        placeholder="E-Mail"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <br />

      <input
        type="password"
        placeholder="Passwort"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <br />

      <button onClick={handleLogin}>Anmelden</button>
    </div>
  );
}

export default LoginForm;
