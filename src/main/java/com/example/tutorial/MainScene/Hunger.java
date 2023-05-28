package com.example.tutorial.MainScene;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.almasb.fxgl.dsl.FXGL;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;

public class Hunger extends Vitality{
    private int famineLevel;
    private final int FAMINE_INCREASE_H = 1;
    private final int HUNGER_INCREASE_M = 40;
    private int price;

    public int getFamineLevel() {
        return famineLevel;
    }
    public void setFamineLevel(int famineLevel) {
        this.famineLevel = famineLevel;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public void updatePrice(LocalDateTime startGame){
        price = 5 + 5*((int) (ChronoUnit.DAYS.between(startGame, LocalDateTime.now())));
    }

    public Hunger(){
        positionX = 14;
        positionY = 130;
        icon = FXGL.getAssetLoader().loadTexture("mainScene/hunger.png");
        hitbox = new Rectangle(positionX, positionY, SPRITE_WIDTH, SPRITE_WIDTH);
    }

    /**
     * Increase hunger by 1 every 40 minutes
     * Increase famine level by 1 every hour if hunger is full
     */
    public void increaseHunger(){
        long difference = ChronoUnit.MINUTES.between(lastUpdated, LocalDateTime.now());
        if(difference >= HUNGER_INCREASE_M){
            while(quantity < QUANTITY_LIMIT && difference >= HUNGER_INCREASE_M){
                quantity++;
                FXGL.removeUINode(subTexture);
                subTexture = icon.subTexture(new Rectangle2D(SPRITE_WIDTH * quantity, 0, SPRITE_WIDTH, SPRITE_WIDTH));
                FXGL.addUINode(subTexture, positionX, positionY);
                difference -= HUNGER_INCREASE_M;
                lastUpdated = lastUpdated.plusMinutes(HUNGER_INCREASE_M);
            }
        }
        difference = ChronoUnit.HOURS.between(lastUpdated, LocalDateTime.now());
        if(difference >= FAMINE_INCREASE_H){
            while(famineLevel < QUANTITY_LIMIT && difference >= FAMINE_INCREASE_H){
                famineLevel++;
                difference -= FAMINE_INCREASE_H;
                lastUpdated = lastUpdated.plusHours(FAMINE_INCREASE_H);
            }
        }
    }
}
