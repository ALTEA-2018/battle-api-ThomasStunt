package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Battle.Battle;

import java.util.List;
import java.util.UUID;

public interface BattleService {

    List<Battle> listBattles();
    Battle getBattle(UUID battleUuid);
    UUID createBattle(String trainer, String opponent);
    Battle attack(UUID battleUuid, String trainer) throws Exception;

}
