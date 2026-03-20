import { useEffect, useState } from "react";

type Match = {
  matchId: string;
  playedAt: string;
};

type MatchesApiResponse = {
  message: string;
  data: Match[];
};

type Props = {
  token: string;
  refreshKey: number;
};

export default function MatchList({ token, refreshKey }: Props) {
  const [matches, setMatches] = useState<Match[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  async function loadMatches() {
    try {
      setLoading(true);
      setError(null);

      const response = await fetch("http://localhost:8080/matches", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Fehler beim Laden der Matches");
      }

      const json: MatchesApiResponse = await response.json();
      setMatches(json.data);
    } catch (err: any) {
      setError(err.message || "Unbekannter Fehler");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadMatches();
  }, [token, refreshKey]);

  if (loading) {
    return <p>Matches werden geladen...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>Fehler: {error}</p>;
  }

  if (matches.length === 0) {
    return <p>Keine Matches gefunden.</p>;
  }

  return (
    <div>
      <h2>Deine Matches</h2>

      {matches.map((match) => (
        <div
          key={match.matchId}
          style={{
            border: "1px solid #ccc",
            padding: "12px",
            marginBottom: "12px",
            borderRadius: "8px",
          }}
        >
          <p>
            <strong>Match ID:</strong> {match.matchId}
          </p>
          <p>
            <strong>Gespielt am:</strong>{" "}
            {new Date(match.playedAt).toLocaleString("de-DE")}
          </p>
        </div>
      ))}
    </div>
  );
}
