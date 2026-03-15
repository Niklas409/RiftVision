import { useState } from "react";

function RegisterForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  const handleRegister = async () => {
    setError("");
    setSuccess("");
    setLoading(true);
    try {
      const response = await fetch("http://localhost:8080/auth/register", {
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
        setError(data.message || "Registrierung fehlgeschlagen");
        setLoading(false);
        return;
      }

      setSuccess("Registrierung erfolgreich");
      setEmail("");
      setPassword("");
      setLoading(false);

      console.log("Register Response:", data);
    } catch (error) {
      console.error("Register Fehler:", error);
      setError("Server nicht erreichbar");
      setLoading(false);
    }
  };

  return (
    <div style={{ marginTop: "40px" }}>
      {loading ? "Lädt..." : "Registrieren"}

      {error && <p style={{ color: "red" }}>{error}</p>}
      {success && <p style={{ color: "lightgreen" }}>{success}</p>}

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

      <button onClick={handleRegister}>Registrieren</button>
    </div>
  );
}

export default RegisterForm;
