package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Trainer.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    RestTemplate restTemplate;

    String actualUrl;

    public List<Trainer> listTrainers() {
        Trainer[] trainers = restTemplate.getForObject(actualUrl+"/trainers/", Trainer[].class);
        return Arrays.asList(trainers);
    }

    public Trainer getTrainer(String name) {
        Trainer t = restTemplate.getForObject(actualUrl+"/trainers/"+name, Trainer.class);
        return t;
    }

    @Autowired
    void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${trainer.service.url}")
    void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.actualUrl = pokemonServiceUrl;
    }
}