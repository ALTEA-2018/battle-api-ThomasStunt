package com.miage.altea.battle_api.service;


import com.miage.altea.battle_api.bo.Battle.Battle;
import com.miage.altea.battle_api.bo.Battle.Pokemon;
import com.miage.altea.battle_api.bo.Trainer.Trainer;
import com.miage.altea.battle_api.repository.BattleRepository;
import com.miage.altea.battle_api.utils.BattleState;
import com.miage.altea.battle_api.utils.BattleUtilitaries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class BattleServiceImpl implements BattleService {
    private BattleRepository battleRepository;
    private TrainerService trainersService;

    @Autowired
    public void setBattleRepository(BattleRepository battleRepository) {
        this.battleRepository = battleRepository;
    }

    @Autowired
    public void setTrainersService(TrainerService trainersService) {
        this.trainersService = trainersService;
    }

    @Override
    public Iterable<Battle> getBattles() {
        return this.battleRepository.findAll();
    }

    @Override
    public Battle getBattle(UUID uuid) {
        return this.battleRepository.findById(uuid);
    }

    @Override
    public Battle createBattle(String trainer, String opponent) {
        Battle battle = new Battle();
        Trainer first = this.trainersService.getTrainer(trainer);
        Trainer second = this.trainersService.getTrainer(opponent);

        UUID uuid = UUID.randomUUID();
        battle.setUuid(uuid);

        Pokemon one = first.getTeam().stream().filter(x -> x.hp > 0).findFirst().orElse(null);
        Pokemon two = second.getTeam().stream().filter(x -> x.hp > 0).findFirst().orElse(null);

        if (one == null || two == null) {
            throw new IllegalArgumentException("Can't create game!");
        }

        if (one.getSpeed() > two.getSpeed()) {
            first.setNextTurn(true);
            second.setNextTurn(false);
        } else if (two.getSpeed() > one.getSpeed()) {
            first.setNextTurn(false);
            second.setNextTurn(true);
        } else {
            var random = new Random();
            int index = random.nextInt(2);

            switch (index) {
                case 0: {
                    first.setNextTurn(true);
                    second.setNextTurn(false);
                    break;
                }
                case 1: {
                    first.setNextTurn(false);
                    second.setNextTurn(true);
                    break;
                }
            }
        }

        battle.setTrainer(first);
        battle.setOpponent(second);
        battle.setBattleState(BattleState.STARTING);
        this.battleRepository.update(battle);
        return battle;
    }

    @Override
    public Battle attack(UUID uuid, String trainer) {
        Battle battle = this.getBattle(uuid);

        if (battle == null) {
            throw new IllegalArgumentException("No battle found");
        }

        if (battle.battleState.equals(BattleState.TERMINATED)) {
            throw new IllegalArgumentException("Battle currently terminated");
        }

        Trainer first;
        Trainer second;

        if (trainer.equals(battle.getTrainer().getName())) {
            first = battle.getTrainer();
            second = battle.getOpponent();

            if (!first.nextTurn) {
                throw new IllegalArgumentException("Not you'r nextTurn!");
            }
        } else {
            first = battle.getOpponent();
            second = battle.getTrainer();

            if (!first.nextTurn) {
                throw new IllegalArgumentException("Not you'r nextTurn!");
            }
        }

        first.getTeam()
                .stream()
                .filter(x -> x.hp > 0)
                .findFirst()
                .ifPresent(one -> {
                    second.getTeam()
                            .stream()
                            .filter(x -> x.hp > 0)
                            .findFirst()
                            .ifPresent(x -> {
                                x.setHp(BattleUtilitaries.attack(x.getHp(), one.getLevel(), one.getAttack(), x.getDefense()));

                                if (x.getHp() == 0) {
                                    x.setKo(true);
                                    x.setAlive(false);
                                }
                            });
                });

        if (first.getName().equals(battle.getTrainer().getName())) {
            battle.setTrainer(first);
            battle.setOpponent(second);

            battle.getTrainer().setNextTurn(false);
            battle.getOpponent().setNextTurn(true);
        } else {
            battle.setOpponent(first);
            battle.setTrainer(second);

            battle.getOpponent().setNextTurn(false);
            battle.getTrainer().setNextTurn(true);
        }

        if (battle.getTrainer().getTeam().stream().allMatch(x -> x.getHp() == 0)
                || battle.getOpponent().getTeam().stream().allMatch(x -> x.getHp() == 0)) {
            battle.setBattleState(BattleState.TERMINATED);
        } else {
            battle.setBattleState(BattleState.INPROGRESS);
        }

        this.battleRepository.update(battle);
        return battle;
    }
}
