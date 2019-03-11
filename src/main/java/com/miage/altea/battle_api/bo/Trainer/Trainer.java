package com.miage.altea.battle_api.bo.Trainer;

import com.miage.altea.battle_api.bo.Battle.Pokemon;

import java.util.List;

public class Trainer {

    private String name;
    private List<Pokemon> team;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pokemon> getTeam() {
        return team;
    }

    public void setTeam(List<Pokemon> team) {
        this.team = team;
    }
}
