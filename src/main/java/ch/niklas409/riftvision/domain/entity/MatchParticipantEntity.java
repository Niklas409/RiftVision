package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "match_participants",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"match_id", "player_id"})
        }
)
public class MatchParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="match_id", nullable=false)
    private MatchEntity match;

    @ManyToOne
    @JoinColumn(name = "player_id")
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

    protected MatchParticipantEntity() {
    }

    public MatchParticipantEntity(MatchEntity match, PlayerEntity player, String champion, int kills, int deaths, int assists, boolean win) {
        this.match = match;
        this.player = player;
        this.champion = champion;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.win = win;
    }

    public Long getId() {
        return id;
    }

    public MatchEntity getMatch() {
        return match;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setMatch(MatchEntity match) {
        this.match = match;
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

}
