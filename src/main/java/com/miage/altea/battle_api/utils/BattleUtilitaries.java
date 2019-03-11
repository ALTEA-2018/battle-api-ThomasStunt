package com.miage.altea.battle_api.utils;

public class BattleUtilitaries {

    public static float computeStat(int level, int baseStat) {
        return 5 + (baseStat * level / 50);
    }

    public static float computeHp(int level, int baseHp) {
        return 10 + level + (baseHp * level / 50);
    }

    public static float hpAfterAttack(int attackingLevel, float attackingAttackStat, float defendingDefendStat) {
        return ((2*attackingLevel)/5) + (2*(attackingAttackStat/defendingDefendStat))+2;
    }

}
