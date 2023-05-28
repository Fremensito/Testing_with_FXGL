package com.example.tutorial.MiniGame;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import javafx.geometry.Point2D;

public class Particle extends Component{
    private Texture texture;
    private int direction;
    private Point2D position;

    private byte disappearCounter;

    public Particle(){
        switch(FXGLMath.random(1, 4)){
            case 1:
                texture = FXGL.getAssetLoader().loadTexture("miniGame/woodParticle1.png");
                break;
            case 2:
                texture = FXGL.getAssetLoader().loadTexture("miniGame/woodParticle2.png");
                break;
            case 3:
                texture = FXGL.getAssetLoader().loadTexture("miniGame/woodParticle3.png");
                break;
            case 4:
                texture = FXGL.getAssetLoader().loadTexture("miniGame/woodParticle4.png");
                break;
        }

        position = FXGL.getInput().getMousePositionWorld();
        direction = FXGLMath.random(1, 8);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        entity.setPosition(position);
    }

    public void update(){
        disappearCounter++;
        switch(direction){
            case 1: position = position.add(-2, -2); entity.setPosition(position); break;
            case 2: position = position.add(0, -2); entity.setPosition(position); break;
            case 3: position = position.add(2, -2); entity.setPosition(position); break;
            case 4: position = position.add(2, 0); entity.setPosition(position); break;
            case 5: position = position.add(2, 2); entity.setPosition(position); break;
            case 6: position = position.add(0, 2); entity.setPosition(position); break;
            case 7: position = position.add(-2, 2); entity.setPosition(position); break;
            case 8: position = position.add(-2, 0); entity.setPosition(position); break;
        }
        if(disappearCounter == 17)
            entity.removeFromWorld();
    }
}
