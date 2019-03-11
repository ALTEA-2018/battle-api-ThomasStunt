package com.miage.altea.battle_api.bo.Battle;

import com.miage.altea.battle_api.bo.Trainer.Trainer;
import com.miage.altea.battle_api.utils.BattleState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Battle {

    @Id
    private UUID uuid;

    @Column
    private Trainer trainer;

    @Column
    private Trainer opponent;

    @Column
    private Trainer toPlay;

    private BattleState battleState;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Trainer getOpponent() {
        return opponent;
    }

    public void setOpponent(Trainer opponent) {
        this.opponent = opponent;
    }

    public Trainer toPlay() {
        return toPlay;
    }

    public void toPlay(Trainer turn) {
        this.toPlay = toPlay;
    }

    public Trainer getToPlay() {
        return toPlay;
    }

    public void setToPlay(Trainer toPlay) {
        this.toPlay = toPlay;
    }

    public BattleState getBattleState() {
        return battleState;
    }

    public void setBattleState(BattleState battleState) {
        this.battleState = battleState;
    }
}
