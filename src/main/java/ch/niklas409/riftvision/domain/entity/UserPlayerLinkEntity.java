package ch.niklas409.riftvision.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_player_links")
public class UserPlayerLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private PlayerEntity player;

    private Instant linkedAt;

    public UserPlayerLinkEntity(UserEntity user, PlayerEntity player, Instant linkedAt) {
        this.user = user;
        this.player = player;
        this.linkedAt = linkedAt;
    }

    protected UserPlayerLinkEntity() {
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public Instant getLinkedAt() {
        return linkedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public void setLinkedAt(Instant linkedAt) {
        this.linkedAt = linkedAt;
    }
}