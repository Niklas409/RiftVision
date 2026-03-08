package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="player_id", nullable=false)
    private PlayerEntity player;

    @Column(nullable=false)
    private String champion;

    @Column(nullable=false)
    private boolean win;

    @Column(nullable=false)
    private int kills;

    @Column(nullable=false)
    private int deaths;

    @Column(nullable=false)
    private int assists;

    @Column(nullable=false)
    private Instant playedAt;

    protected MatchEntity() {
    }

    public MatchEntity(PlayerEntity player, String champion, boolean win, int kills, int deaths, int assists, Instant playedAt) {
        this.player = player;
        this.champion = champion;
        this.win = win;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.playedAt = playedAt;
    }

    public Long getId() {
        return id;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public String getChampion() {
        return champion;
    }

    public boolean isWin() {
        return win;
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

    public Instant getPlayedAt() {
        return playedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public void setWin(boolean win) {
        this.win = win;
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

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }
}
