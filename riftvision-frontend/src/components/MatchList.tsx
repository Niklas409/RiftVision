import { useEffect, useState } from "react";
import MatchDetails from "./MatchDetails";

type Match = {
  matchId: string;
  playedAt: string;
};

type MatchParticipant = {
  playerId: string;
  champion: string;
  kills: number;
  deaths: number;
  assists: number;
  win: boolean;
};

type MatchDetails = {
  matchId: string;
  playedAt: string;
  participants: MatchParticipant[];
};

type MatchesApiResponse = {
  message: string;
  data: Match[];
};

type MatchDetailsApiResponse = {
  message: string;
  data: MatchDetails;
};

type Props = {
  token: string;
  refreshKey: number;
};

export default function MatchList({ token, refreshKey }: Props) {
  const [matches, setMatches] = useState<Match[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [selectedMatchId, setSelectedMatchId] = useState<string | null>(null);
  const [matchDetails, setMatchDetails] = useState<MatchDetails | null>(null);
  const [detailsLoading, setDetailsLoading] = useState(false);
  const [detailsError, setDetailsError] = useState<string | null>(null);

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

  async function handleMatchClick(matchId: string) {
    try {
      if (selectedMatchId === matchId) {
        setSelectedMatchId(null);
        setMatchDetails(null);
        setDetailsError(null);
        return;
      }

      setSelectedMatchId(matchId);
      setDetailsLoading(true);
      setDetailsError(null);

      const response = await fetch(`http://localhost:8080/matches/${matchId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Fehler beim Laden der Match-Details");
      }

      const json: MatchDetailsApiResponse = await response.json();
      setMatchDetails(json.data);
    } catch (err: any) {
      setDetailsError(err.message || "Unbekannter Fehler");
      setMatchDetails(null);
    } finally {
      setDetailsLoading(false);
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

          <button onClick={() => handleMatchClick(match.matchId)}>
            {selectedMatchId === match.matchId
              ? "Details ausblenden"
              : "Details anzeigen"}
          </button>

          {selectedMatchId === match.matchId && (
            <MatchDetails
              matchDetails={matchDetails}
              loading={detailsLoading}
              error={detailsError}
            />
          )}
        </div>
      ))}
    </div>
  );
}
