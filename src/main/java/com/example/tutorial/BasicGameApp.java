package com.example.tutorial;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.MouseButton;

public class BasicGameApp extends GameApplication {
    private final int width = 900;
    private final int height = 700;
    Entity cat;
    Entity cuttingScene;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setTicksPerSecond(60);
    }

    protected void initGame() {
        cat = FXGL.entityBuilder().buildAndAttach();
        cat.addComponent(new Cat());

        cuttingScene = FXGL.entityBuilder().buildAndAttach();
        cuttingScene.addComponent(new SawMillScene());
    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(GameController.getMouseController(), MouseButton.PRIMARY);
    }
    @Override
    protected void onUpdate(double tpf){
        if(GameController.getIsRightMousePressed() && !cat.getComponent(Cat.class).getIsCutting()){
            cat.getComponent(Cat.class).cut();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}