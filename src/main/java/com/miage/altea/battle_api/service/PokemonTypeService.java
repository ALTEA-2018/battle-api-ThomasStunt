package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.PokemonType.PokemonType;

import java.util.List;

public interface PokemonTypeService {

    List<PokemonType> listPokemonType();
    PokemonType getPokemonTypeById(Integer id);

}