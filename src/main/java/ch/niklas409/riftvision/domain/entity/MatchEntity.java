package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matchId;

    @ManyToOne(optional=false)
    @JoinColumn(name="player_id", nullable=false)
    private PlayerEntity player;

    @Column(nullable=false)
    private String champion;

    @Column(nullable=false)
    private int kills;

    @Column(nullable=false)
    private int deaths;

    @Column(nullable=false)
    private int assists;

    @Column(nullable=false)
    private boolean win;

    @Column(nullable=false)
    private Instant playedAt;

    @OneToMany(mappedBy = "match")
    private List<MatchParticipantEntity> participants;

    protected MatchEntity() {
    }

    public MatchEntity(String matchId, PlayerEntity player, String champion, int kills, int deaths, int assists, boolean win, Instant playedAt) {
        this.matchId = matchId;
        this.player = player;
        this.champion = champion;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.win = win;
        this.playedAt = playedAt;
    }

    public Long getId() {
        return id;
    }

    public String getMatchId() {
        return matchId;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public String getChampion() {
        return champion;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public boolean isWin() {
        return win;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public List<MatchParticipantEntity> getParticipants() {
        return participants;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }

    public void setParticipants(List<MatchParticipantEntity> participants) {
        this.participants = participants;
    }
}
