package com.example.tutorial.MainScene;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.shape.Rectangle;

public class MiniGameIcon extends Icon{

    public MiniGameIcon(){
        icon = FXGL.getAssetLoader().loadTexture("mainScene/axe.png");
        FXGL.addUINode(icon, 730, 0);
        hitbox = new Rectangle(780, 20, icon.getWidth(), icon.getHeight());
    }
    public void update(){
        checkCursor();
    }
}
