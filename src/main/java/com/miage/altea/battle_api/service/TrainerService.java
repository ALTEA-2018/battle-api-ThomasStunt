package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Trainer.Trainer;

import java.util.List;

public interface TrainerService {

    List<Trainer> listTrainers();
    Trainer getTrainer(String name);

}