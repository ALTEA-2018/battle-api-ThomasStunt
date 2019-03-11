package com.miage.altea.battle_api.controller;

import com.miage.altea.battle_api.bo.Battle.Battle;
import com.miage.altea.battle_api.exception.FinishedBattleException;
import com.miage.altea.battle_api.exception.WrongTrainerException;
import com.miage.altea.battle_api.service.BattleService;
import com.miage.altea.battle_api.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/battles")
public class BattleController {

    @Autowired
    BattleService battleService;
    TrainerService trainerService;

    BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @PostMapping("/")
    public UUID createBattle(@RequestBody String trainer, @RequestBody String opponent) {
        return battleService.createBattle(trainerService.getTrainer(trainer).getName(), trainerService.getTrainer(opponent).getName());
    }

    @GetMapping("/")
    public List<Battle> getAllBattles() {
        return battleService.listBattles();
    }

    @GetMapping("/{UUID}")
    public Battle getBattle(@PathVariable("UUID") UUID battleUuid) {
        return battleService.getBattle(battleUuid);
    }

    @PostMapping("/{UUID}/{trainerName}/attack")
    public ResponseEntity<Battle> attack(@PathVariable("UUID") UUID battleUuid, @PathVariable("trainerName") String trainerName) {
        Battle battle;
        try {
            return ResponseEntity.ok(battleService.attack(battleUuid, trainerName));
        } catch (WrongTrainerException wte) {
            ResponseEntity.badRequest().build();
        } catch (NullPointerException npe) {
            ResponseEntity.notFound().build();
        } catch (FinishedBattleException fbe) {
            ResponseEntity.badRequest().build();
        } catch (Exception e) {
            ResponseEntity.noContent().build();
        }
        return null;
    }
}
