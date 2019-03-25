package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Trainer.Trainer;

public interface TrainerService {
    Trainer getTrainer(String name);
}