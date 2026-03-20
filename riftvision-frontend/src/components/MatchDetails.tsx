type MatchParticipant = {
  playerId: string;
  champion: string;
  kills: number;
  deaths: number;
  assists: number;
  win: boolean;
};

type MatchDetailsType = {
  matchId: string;
  playedAt: string;
  participants: MatchParticipant[];
};

type Props = {
  matchDetails: MatchDetailsType | null;
  loading: boolean;
  error: string | null;
};

function shortenPlayerId(playerId: string): string {
  if (playerId.length <= 18) {
    return playerId;
  }

  return `${playerId.substring(0, 18)}...`;
}

export default function MatchDetails({ matchDetails, loading, error }: Props) {
  if (loading) {
    return <p>Match-Details werden geladen...</p>;
  }

  if (error) {
    return <p style={{ color: "red" }}>Fehler: {error}</p>;
  }

  if (!matchDetails) {
    return null;
  }

  return (
    <div style={{ marginTop: "12px", paddingTop: "12px" }}>
      <h3>Participants</h3>

      {matchDetails.participants.map((participant) => (
        <div
          key={participant.playerId}
          style={{
            border: "1px solid #eee",
            padding: "10px",
            marginBottom: "10px",
            borderRadius: "6px",
          }}
        >
          <p>
            <strong>Champion:</strong> {participant.champion}
          </p>
          <p>
            <strong>Player ID:</strong> {shortenPlayerId(participant.playerId)}
          </p>
          <p>
            <strong>KDA:</strong> {participant.kills}/{participant.deaths}/
            {participant.assists}
          </p>
          <p>
            <strong>Ergebnis:</strong> {participant.win ? "Sieg" : "Niederlage"}
          </p>
        </div>
      ))}
    </div>
  );
}
