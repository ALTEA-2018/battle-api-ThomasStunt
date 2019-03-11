package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.PokemonType.PokemonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {

    @Autowired
    RestTemplate restTemplate;

    String actualUrl;

    @Override
    public List<PokemonType> listPokemonType() {
        return Arrays.asList(restTemplate.getForObject(this.actualUrl + "/pokemon-types/", PokemonType[].class));
    }

    @Override
    public PokemonType getPokemonTypeById(Integer id) {
        return this.restTemplate.getForObject(this.actualUrl + "/pokemon-types", PokemonType.class, id);
    }

    @Value("${pokemonType.service.url}")
    void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.actualUrl = pokemonServiceUrl;
    }
}
