import { useEffect, useState } from "react";

type Match = {
  id: number;
  matchId: string;
  playedAt: string;
};

type MatchApiResponse = {
  message: string;
  data: Match[];
};

type MatchListProps = {
  token: string;
};

function formatPlayedAt(value: string): string {
  const date = new Date(value);

  if (Number.isNaN(date.getTime())) {
    return value;
  }

  return date.toLocaleString("de-DE");
}

export default function MatchList({ token }: MatchListProps) {
  const [matches, setMatches] = useState<Match[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  async function loadMatches(): Promise<void> {
    try {
      setLoading(true);
      setError("");

      const response = await fetch("http://localhost:8080/matches", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        if (response.status === 401) {
          throw new Error("Nicht autorisiert. Bitte erneut einloggen.");
        }

        throw new Error("Matches konnten nicht geladen werden.");
      }

      const data: MatchApiResponse = await response.json();
      setMatches(data.data);
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError("Unbekannter Fehler beim Laden der Matches.");
      }
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadMatches();
  }, []);

  if (loading) {
    return <p>Matches werden geladen...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>{error}</p>;
  }

  if (matches.length === 0) {
    return <p>Keine Matches gefunden.</p>;
  }

  return (
    <div style={{ marginTop: "20px" }}>
      <h2>Deine Matches</h2>

      <div style={{ display: "grid", gap: "12px", marginTop: "16px" }}>
        {matches.map((match) => (
          <div
            key={match.id}
            style={{
              border: "1px solid #ccc",
              borderRadius: "8px",
              padding: "12px",
              textAlign: "left",
            }}
          >
            <p>
              <strong>Match ID:</strong> {match.matchId}
            </p>
            <p>
              <strong>Gespielt am:</strong> {formatPlayedAt(match.playedAt)}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
}
