package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id", unique = true, nullable = false)
    private String playerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String region;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    protected PlayerEntity() {
    }

    public PlayerEntity(String playerId, String name, String region) {
        this.playerId = playerId;
        this.name = name;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
