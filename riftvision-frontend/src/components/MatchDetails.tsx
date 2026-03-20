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

type TeamSectionProps = {
  title: string;
  participants: MatchParticipant[];
};

function TeamSection({ title, participants }: TeamSectionProps) {
  return (
    <div
      style={{
        flex: 1,
        minWidth: "280px",
      }}
    >
      <h4 style={{ marginBottom: "12px" }}>{title}</h4>

      {participants.map((participant) => (
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

  const blueTeam = matchDetails.participants.slice(0, 5);
  const redTeam = matchDetails.participants.slice(5, 10);

  return (
    <div style={{ marginTop: "12px", paddingTop: "12px" }}>
      <h3>Participants</h3>

      <div
        style={{
          display: "flex",
          gap: "16px",
          flexWrap: "wrap",
        }}
      >
        <TeamSection title="Team Blau" participants={blueTeam} />
        <TeamSection title="Team Rot" participants={redTeam} />
      </div>
    </div>
  );
}
