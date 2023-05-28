package com.example.tutorial.MainScene;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.almasb.fxgl.dsl.FXGL;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;

public class Energy extends Vitality{
    private final int ENERGY_INCREASE_M = 40;
    private final int ENERGY_DECREASE_H = 1;

    public Energy(){
        positionX = 14;
        positionY = -30;
        icon = FXGL.getAssetLoader().loadTexture("mainScene/energySet.png");
        hitbox = new Rectangle(positionX, positionY, SPRITE_WIDTH, SPRITE_WIDTH);
    }

    public void increaseEnergy(){
        long difference = ChronoUnit.MINUTES.between(lastUpdated, LocalDateTime.now());
        if(quantity < QUANTITY_LIMIT){
            while(quantity < QUANTITY_LIMIT && difference >= ENERGY_INCREASE_M){
                quantity++;
                FXGL.removeUINode(subTexture);
                subTexture = icon.subTexture(new Rectangle2D(SPRITE_WIDTH * quantity, 0, SPRITE_WIDTH, SPRITE_WIDTH));
                FXGL.addUINode(subTexture, positionX, positionY);
                difference -= ENERGY_INCREASE_M;
                lastUpdated = lastUpdated.plusMinutes(ENERGY_INCREASE_M);
            }
        }
    }

    public void looseEnergy(){
        long difference = ChronoUnit.HOURS.between(lastUpdated, LocalDateTime.now());
        if(quantity > 0){
            while(quantity > 0 && difference >= ENERGY_DECREASE_H){
                quantity--;
                FXGL.removeUINode(subTexture);
                subTexture = icon.subTexture(new Rectangle2D(SPRITE_WIDTH * quantity, 0, SPRITE_WIDTH, SPRITE_WIDTH));
                FXGL.addUINode(subTexture, positionX, positionY);
                difference -= ENERGY_DECREASE_H;
                lastUpdated = lastUpdated.plusHours(ENERGY_DECREASE_H);
            }
        }
    }
}
