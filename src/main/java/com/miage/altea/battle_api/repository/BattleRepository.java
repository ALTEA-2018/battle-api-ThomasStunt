package com.miage.altea.battle_api.repository;

import com.miage.altea.battle_api.bo.Battle.Battle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BattleRepository {
    List<Battle> findAll();
    Battle findById(UUID uuid);
    void update(Battle battle);
}