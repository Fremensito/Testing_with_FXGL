package com.example.tutorial;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class BasicGameApp extends GameApplication {
    private Entity cat;
    private Entity mainScene;
    private int width = 900;
    private int height = 700;

    UserAction stopWalking;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setTicksPerSecond(30);
    }

    protected void initGame(){
        MainSceneGeneration();
    }

    @Override
    protected void onUpdate(double tpf) {
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void MainSceneGeneration(){
        Cat cat = new Cat();
        MainScene mainScene = new MainScene();

        this.mainScene = FXGL.entityBuilder()
                .view(mainScene
                .getBackGround())
                .buildAndAttach();

        this.cat = FXGL.entityBuilder()
                .at(cat.getPositionX(), cat.getPositionY())
                .view(cat.getImage())
                .buildAndAttach();

        this.cat.addComponent(new Cat());
    }
}