package com.example.tutorial;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

public class Wood extends Component {
    private Texture woodSprites;
    private Texture cutWoodSprite;
    private int toBeCutPoints;

    private double speed;

    private boolean broken;

    public Wood(double speed){
        woodSprites = FXGL.getAssetLoader().loadTexture("tronco.png");
        woodSprites.setX(FXGL.random(0, 750));
        woodSprites.setY(-150);
        cutWoodSprite = FXGL.getAssetLoader().loadTexture("troncoPartido.png");
        cutWoodSprite.setX(woodSprites.getX());
        cutWoodSprite.setY(woodSprites.getY());
        toBeCutPoints = 0;
        resetPoints();
        this.speed = speed;
        broken = false;
    }

    public Texture getWoodSprites(){
        return woodSprites;
    }
    public boolean getBroken(){
        return broken;
    }

    @Override
    public void onUpdate(double tpf){
        move();
        if((toBeCutPoints > 40 || woodSprites.getY() > 769) && !broken){
            cutWoodSprite.setVisible(true);
            woodSprites.setVisible(false);
            getGameTimer().runOnceAfter(() -> {
                toBreak();
            }, Duration.millis(600));
            broken = true;
        }
    }
    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(woodSprites);
        entity.getViewComponent().addChild(cutWoodSprite);
        cutWoodSprite.setVisible(false);
    }

    public void addPoints(){
        toBeCutPoints++;
    }

    public void toBreak(){
        entity.removeFromWorld();
    }

    private void resetPoints(){
        getGameTimer().runAtInterval(() -> {
            toBeCutPoints = 0;
        }, Duration.millis(300));
    }

    private void move(){
        woodSprites.setY(woodSprites.getY() + speed);
        cutWoodSprite.setY(woodSprites.getY() + speed);
    }
}
