package com.example.tutorial.MiniGame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.example.tutorial.BasicGameApp;

public class Heart extends Component{
    private Texture aliveHeart;
    private Texture deadHeart;

    private boolean isAlive;

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Heart(double positionX, double positionY){
        isAlive = true;
        aliveHeart = FXGL.getAssetLoader().loadTexture("miniGame/HeartA.png");
        deadHeart = FXGL.getAssetLoader().loadTexture("miniGame/HeartADead.png");
        FXGL.addUINode(aliveHeart, positionX, positionY);
        FXGL.addUINode(deadHeart, positionX, positionY);
        deadHeart.setVisible(!isAlive);
    }

    public void update(){
        if(!isAlive){
            aliveHeart.setVisible(isAlive);
            deadHeart.setVisible(!isAlive);
            isAlive = true;
        }
        if(BasicGameApp.getScene() == "Main Scene"){
            FXGL.removeUINode(aliveHeart);
            FXGL.removeUINode(deadHeart);
        }
    }
}
