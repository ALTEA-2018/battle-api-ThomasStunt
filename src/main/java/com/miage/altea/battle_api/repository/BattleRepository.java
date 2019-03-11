package com.miage.altea.battle_api.repository;

import com.miage.altea.battle_api.bo.Battle.Battle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BattleRepository extends CrudRepository<Battle, UUID> {

    List<Battle> findAll();
    Optional<Battle> findById(UUID battleUuid);
    Battle save(Battle battle);

}
