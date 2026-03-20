type MatchParticipant = {
  playerId: string;
  playerName: string;
  champion: string;
  kills: number;
  deaths: number;
  assists: number;
  win: boolean;
  teamId: number;
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
  isWinner: boolean;
};

function TeamSection({ title, participants, isWinner }: TeamSectionProps) {
  return (
    <div
      style={{
        flex: 1,
        minWidth: "280px",
        backgroundColor: isWinner ? "#e6ffed" : "#ffe6e6",
        padding: "10px",
        borderRadius: "8px",
      }}
    >
      <h4 style={{ marginBottom: "12px" }}>
        {title} {isWinner ? "🏆" : ""}
      </h4>

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
            <strong>Player:</strong> {participant.playerName}
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

  const blueTeam = matchDetails.participants.filter(
    (participant) => participant.teamId === 100,
  );

  const redTeam = matchDetails.participants.filter(
    (participant) => participant.teamId === 200,
  );

  const blueTeamWon = blueTeam.some((p) => p.win);
  const redTeamWon = redTeam.some((p) => p.win);

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
        <TeamSection
          title="Team Blau"
          participants={blueTeam}
          isWinner={blueTeamWon}
        />
        <TeamSection
          title="Team Rot"
          participants={redTeam}
          isWinner={redTeamWon}
        />
      </div>
    </div>
  );
}
