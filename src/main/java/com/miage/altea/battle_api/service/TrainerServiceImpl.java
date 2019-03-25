package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.PokemonType.PokemonType;
import com.miage.altea.battle_api.bo.Trainer.Trainer;
import com.miage.altea.battle_api.utils.BattleUtilitaries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrainerServiceImpl implements TrainerService {

    private RestTemplate restTemplate;
    private String url;
    private PokemonTypeService pokemonTypeService;

    @Autowired
    @Qualifier("trainerApiRestTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setPokemonTypeService(PokemonTypeService pokemonTypeService) {
        this.pokemonTypeService = pokemonTypeService;
    }

    @Value("${trainers.service.url}")
    public void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.url = pokemonServiceUrl;
    }

    @Cacheable("trainers")
    @Override
    public Trainer getTrainer(String name) {
        Trainer trainer = this.restTemplate.getForObject(this.url+"/trainers/{name}", Trainer.class, name);

        trainer.getTeam().forEach(pokemon -> {
            PokemonType pokemonType = this.pokemonTypeService.getPokemonByTypeId(pokemon.getPokemonType().getId());

            pokemon.setPokemonType(pokemonType);
            pokemon.setHp(BattleUtilitaries.pokemonLife(pokemonType.getStats().getHp(), pokemon.getLevel()));
            pokemon.setSpeed(BattleUtilitaries.pokemonStat(pokemonType.getStats().getSpeed(), pokemon.getLevel()));
            pokemon.setAttack(BattleUtilitaries.pokemonStat(pokemonType.getStats().getAttack(), pokemon.getLevel()));
            pokemon.setDefense(BattleUtilitaries.pokemonStat(pokemonType.getStats().getDefense(), pokemon.getLevel()));
            pokemon.setMaxHp(BattleUtilitaries.pokemonLife(pokemonType.getStats().getHp(), pokemon.getLevel()));
            pokemon.setAlive(true);
            pokemon.setKo(false);
        });

        return trainer;
    }
}
