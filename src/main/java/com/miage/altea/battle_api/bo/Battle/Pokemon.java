package com.miage.altea.battle_api.bo.Battle;

public class Pokemon {

    private int pokemonType;
    private int level;
    private float maxHp;
    private float attack;
    private float defense;
    private float speed;
    private float hp;

    public int getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(int pokemonType) {
        this.pokemonType = pokemonType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(float maxHp) {
        this.maxHp = maxHp;
    }

    public float getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    public float getDefense() {
        return defense;
    }

    public void setDefense(float defense) {
        this.defense = defense;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }
}
