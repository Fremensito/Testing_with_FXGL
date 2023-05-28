package com.example.tutorial.MainScene;

import java.time.LocalDateTime;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Cat extends Component{
    // "Ability to random walking" block
    private boolean walking;
    private final double walkingSpeed = 20;
    private boolean rightWay;
    private AnimatedTexture walkTexture;
    private AnimationChannel walkAnimation;
    private AnimationChannel idleAnimation;
    private final double rightWalkingLimit = 750;
    private final double leftWalkingLimit = 0;
    private final int walkingProbability = 3;

    private boolean alive;
    private boolean isSleeping;
    private Energy energy;
    private Hunger hunger;
    private int gold;

    private LocalDateTime gameStart;

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean isSleeping) {
        this.isSleeping = isSleeping;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public Hunger getHunger() {
        return hunger;
    }

    public void setHunger(Hunger hunger) {
        this.hunger = hunger;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public LocalDateTime getGameStart() {
        return gameStart;
    }

    public void setGameStart(LocalDateTime gameStart) {
        this.gameStart = gameStart;
    }

    
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Cat (){
        walking = false;
        walkAnimation = new AnimationChannel(FXGL.image("mainScene/walkingCat.png"), 2, 150, 150, Duration.millis(300), 0, 1);
        idleAnimation = new AnimationChannel(FXGL.image("mainScene/walkingCat.png"), 2, 150, 150, Duration.millis(-1), 0, 0);
        walkTexture = new AnimatedTexture(idleAnimation);
        energy = new Energy();
        hunger = new Hunger();
    }

    @Override
    public void onAdded(){
        entity.setPosition(306, 503);
        entity.getViewComponent().addChild(walkTexture);
        entity.getTransformComponent().setScaleOrigin(new Point2D(75,75));
        rightWay = true;
        walking = false;
    }

    public void update(){
        energy.update();
        hunger.update();
        if(!isSleeping){
            toWalk();
            energy.looseEnergy();
        }
        else{
            energy.increaseEnergy();
            if(energy.getQuantity() == energy.getQuantityLimit())
                isSleeping = false;
        }
        hunger.increaseHunger();
        hunger.updatePrice(gameStart);
        if(hunger.getFamineLevel() == hunger.getQuantityLimit())
            alive = false;
    }

    /**
     * Set visibility of cat and its bars
     * @param visible - true if cat and its bars should be visible, false otherwise
     */
    public void setVisibility(boolean visible){
        entity.setVisible(visible);
        hunger.setVisible(visible);
        energy.setVisible(visible);
    }

    /**
     * Makes cat walk randomly
     */
    private void toWalk(){
        int proc = FXGL.random(1, 2000);
        if(proc <= walkingProbability*2 && !walking){
            if(rightWay){
                entity.setScaleX(-1);
                rightWay = false;
            }
            else{
                entity.setScaleX(1);
                rightWay = true;
            }
        }
        proc = FXGL.random(1, 2000);
        if(proc <= walkingProbability && !walking){
            if(rightWay && entity.getX() < rightWalkingLimit)
                rightStep();
            if(!rightWay && entity.getX() > leftWalkingLimit)
                leftStep();
        }
    }

    /**
     * Makes cat walk right
     */
    private void rightStep(){
        walking = true;
        walkTexture.playAnimationChannel(walkAnimation);
        movement(-walkingSpeed);
    }

    /**
     * Makes cat walk left
     */
    private void leftStep(){
        walking = true;
        walkTexture.playAnimationChannel(walkAnimation);
        movement(walkingSpeed);
    }

    /**
     * Makes cat walk
     * @param walkingSpeed - speed of walking
     */
    private void movement(double walkingSpeed){
        FXGL.getGameTimer().runOnceAfter(() -> {
            entity.translateX(walkingSpeed);
            walkTexture.loopAnimationChannel(idleAnimation);
            walking = false;
        }, Duration.millis(290));
    }
}
