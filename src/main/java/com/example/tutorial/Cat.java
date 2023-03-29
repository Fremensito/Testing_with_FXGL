package com.example.tutorial;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

public class Cat extends Component {

    private boolean isCutting;
    private boolean test = true;
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

    public Cat (){
        /*walking = false;
        mainScreen = true;
        walkAnimation = new AnimationChannel(FXGL.image("catWalkingSprite.png"), 2, 150, 150, Duration.millis(300), 0, 1);
        idleAnimation = new AnimationChannel(FXGL.image("catWalkingSprite.png"), 2, 150, 150, Duration.millis(-1), 0, 0);
        walkTexture = new AnimatedTexture(idleAnimation);*/
        isCutting = false;
    }
    public boolean getIsCutting(){
        return isCutting;
    }
    @Override
    public void onUpdate(double tpf){
        /*if(mainScreen){
            if(entity != null && !entity.getViewComponent().getChildren().contains(walkTexture)){
                entity.getViewComponent().addChild(walkTexture);
                entity.getTransformComponent().setScaleOrigin(new Point2D(75,75));
                rightWay = true;
                walking = false;
            }
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
        }*/
    }

    private void rightStep(){
        walking = true;
        walkTexture.playAnimationChannel(walkAnimation);
        movement(walkingSpeed);
    }

    private void leftStep(){
        walking = true;
        walkTexture.playAnimationChannel(walkAnimation);
        movement(-walkingSpeed);
    }

    private void movement(double walkingSpeed){
        FXGL.getGameTimer().runOnceAfter(() -> {
            entity.translateX(walkingSpeed);
            walkTexture.loopAnimationChannel(idleAnimation);
            walking = false;
        }, Duration.millis(290));
    }
    public void cut(){
        List<Entity> woods = FXGL.getGameWorld().getEntitiesByComponent(Wood.class).stream().toList();
        isCutting = true;
        final Point2D oldOrigin = FXGL.getInput().getMousePositionWorld();

        getGameTimer().runOnceAfter(() -> {
            Point2D newOrigin = oldOrigin;
            Point2D end = FXGL.getInput().getMousePositionWorld();
            Point2D unitary = end.subtract(newOrigin).normalize();

            while(end.subtract(newOrigin).magnitude() > 1){
                Circle circleTexture = new Circle(newOrigin.getX(), newOrigin.getY(), 3, Color.RED);
                Entity circle = FXGL.entityBuilder()
                        .view(circleTexture)
                        .buildAndAttach();
                for(Entity wood:woods){
                    try{
                        if(wood.getComponent(Wood.class).getWoodSprites().contains(circleTexture.getCenterX(), circleTexture.getCenterY())){
                            wood.getComponent(Wood.class).addPoints();
                        }
                    }catch(Exception e){}
                }
                getGameTimer().runOnceAfter(() -> {
                    circle.removeFromWorld();
                }, Duration.millis(100));
                newOrigin = newOrigin.add(unitary.multiply(2));
            }
            isCutting = false;
        }, Duration.millis(16));
    }
}