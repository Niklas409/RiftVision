import { useEffect, useState } from "react";

type LinkedAccount = {
  playerId: string;
  name: string;
  region: string;
};

type LinkedAccountsApiResponse = {
  message: string;
  data: LinkedAccount[];
};

type Props = {
  token: string;
  onImportSuccess: () => void;
};

export default function LinkedAccountsList({ token, onImportSuccess }: Props) {
  const [accounts, setAccounts] = useState<LinkedAccount[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  async function loadLinkedAccounts() {
    try {
      setLoading(true);
      setError(null);

      const response = await fetch("http://localhost:8080/players", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Fehler beim Laden der Accounts");
      }

      const json: LinkedAccountsApiResponse = await response.json();
      setAccounts(json.data);
    } catch (err: any) {
      setError(err.message || "Unbekannter Fehler");
    } finally {
      setLoading(false);
    }
  }

  async function importMatches(gameName: string) {
    try {
      const tagLine = "0409";

      const response = await fetch(
        `http://localhost:8080/internal/riot/import/${gameName}/${tagLine}?count=5`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      if (!response.ok) {
        throw new Error("Import fehlgeschlagen");
      }

      onImportSuccess();
      alert("Import erfolgreich!");
    } catch (err: any) {
      alert(err.message || "Fehler beim Import");
    }
  }

  useEffect(() => {
    loadLinkedAccounts();
  }, []);

  if (loading) {
    return <p>Verknüpfte Accounts werden geladen...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>Fehler: {error}</p>;
  }

  if (accounts.length === 0) {
    return <p>Keine verknüpften Accounts gefunden.</p>;
  }

  return (
    <div>
      <h2>Verknüpfte Riot Accounts</h2>

      {accounts.map((acc) => (
        <div
          key={acc.playerId}
          style={{
            border: "1px solid #ccc",
            padding: "10px",
            marginBottom: "10px",
            borderRadius: "8px",
          }}
        >
          <p>
            <strong>Name:</strong> {acc.name}
          </p>
          <p>
            <strong>Player ID:</strong> {acc.playerId}
          </p>
          <p>
            <strong>Region:</strong> {acc.region}
          </p>

          <button onClick={() => importMatches(acc.name)}>
            Matches importieren
          </button>
        </div>
      ))}
    </div>
  );
}
