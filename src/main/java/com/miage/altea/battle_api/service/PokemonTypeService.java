package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.PokemonType.PokemonType;

public interface PokemonTypeService {
    PokemonType getPokemonByTypeId(Integer id);
}