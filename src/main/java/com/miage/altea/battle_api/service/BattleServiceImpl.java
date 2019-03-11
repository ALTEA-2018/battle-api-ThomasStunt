package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Battle.Battle;
import com.miage.altea.battle_api.bo.Battle.Pokemon;
import com.miage.altea.battle_api.exception.FinishedBattleException;
import com.miage.altea.battle_api.exception.WrongTrainerException;
import com.miage.altea.battle_api.repository.BattleRepository;
import com.miage.altea.battle_api.utils.BattleState;
import com.miage.altea.battle_api.utils.BattleUtilitaries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class BattleServiceImpl implements BattleService {

    BattleRepository battleRepository;
    TrainerService trainerService;

    @Override
    public List<Battle> listBattles() {
        return battleRepository.findAll();
    }

    @Override
    public Battle getBattle(UUID battleUuid) {
        return battleRepository.findById(battleUuid).orElseThrow(() -> {throw new NullPointerException();});
    }

    @Override
    public UUID createBattle(String trainer, String opponent) {
        // Create battle
        Battle battle = new Battle();

        // Set trainers in battle
        battle.setTrainer(trainerService.getTrainer(trainer));
        battle.setOpponent(trainerService.getTrainer(opponent));

        // Set battle Uuid
        battle.setUuid(UUID.randomUUID());

        // Set who's beginning according to first pokemon of each player
        float speedTrainer = battle.getTrainer()
                .getTeam()
                .stream()
                .filter(pkmn -> pkmn.getHp() > 0)
                .findFirst()
                .get()
                .getSpeed();
        float speedOpponent = battle.getOpponent()
                .getTeam()
                .stream()
                .filter(pkmn -> pkmn.getHp() > 0)
                .findFirst()
                .get()
                .getSpeed();
        if(speedTrainer > speedOpponent) {
            battle.setToPlay(battle.getTrainer());
        } else if(speedTrainer == speedOpponent) {
            battle.setToPlay(new Random().nextInt(2) > 0 ? battle.getTrainer() : battle.getOpponent());
        } else {
            battle.setToPlay(battle.getOpponent());
        }

        // Set battle state
        battle.setBattleState(BattleState.INPROGRESS);

        // Save battle
        battleRepository.save(battle);

        // Return battle Uuid
        return battle.getUuid();
    }

    @Override
    public Battle attack(UUID battleUuid, String trainer) throws Exception {
        // Get the battle
        Battle battle = battleRepository.findById(battleUuid).orElseThrow(() -> {throw new NullPointerException();});

        // Wrong player
        if(!battle.getToPlay().getName().equals(trainer)) {
            throw new WrongTrainerException();
        }

        if(battle.getBattleState().equals(BattleState.TERMINATED)) {
            throw new FinishedBattleException();
        }

        // Get Pokemon's stats
        Pokemon trainerPokemon = battle.getTrainer()
                .getTeam()
                .stream()
                .filter(pkmn -> pkmn.getHp() > 0)
                .findFirst()
                .get();
        Pokemon opponentPokemon = battle.getOpponent()
                .getTeam()
                .stream()
                .filter(pkmn -> pkmn.getHp() > 0)
                .findFirst()
                .get();

        // Do the attack
        if(trainer.equals(battle.getTrainer())) {
            float hpLeft = opponentPokemon.getHp() - BattleUtilitaries.hpAfterAttack(trainerPokemon.getLevel(), trainerPokemon.getAttack(), opponentPokemon.getDefense());
            opponentPokemon.setHp(hpLeft > 0 ? hpLeft : 0);
            battle.setToPlay(battle.getOpponent());
        } else {
            float hpLeft = trainerPokemon.getHp() - BattleUtilitaries.hpAfterAttack(opponentPokemon.getLevel(), opponentPokemon.getAttack(), trainerPokemon.getDefense());
            trainerPokemon.setHp(hpLeft > 0 ? hpLeft : 0);
            battle.setToPlay(battle.getTrainer());
        }

        // Save battle
        battleRepository.save(battle);

        return battle;
    }

    @Autowired
    public void setBattleRepository(BattleRepository battleRepository) {
        this.battleRepository = battleRepository;
    }

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

}
