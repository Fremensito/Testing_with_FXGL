package com.example.tutorial.MiniGame;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.tutorial.BasicGameApp;
import com.example.tutorial.MainScene.Cat;

import javafx.scene.text.Text;

public class MiniGameScene{
    MiniGameInputManager manager;

    private double spawnProc;
    private double spawnProcBoost;
    private double bombProc;
    private double miniGameObjectSpeed;
    private double speedBoost;

    private Heart[] hearts;
    private int lifes;
    private int points;

    private Text scoreText;
    private Result result;
    public MiniGameScene(){
        manager = new MiniGameInputManager();

        spawnProc = 5;
        bombProc = 10;

        speedBoost = 0.0006;
        spawnProcBoost = 0.008;
        miniGameObjectSpeed = 4;

        hearts = new Heart[3];
        hearts[0] = new Heart(20, 10);
        hearts[1] = new Heart(80, 10);
        hearts[2] = new Heart(140, 10);
        lifes = 3;
        points = 0;
        scoreText = new Text("Score: " + points);
        scoreText.setFill(javafx.scene.paint.Color.BROWN);
        scoreText.setStyle("-fx-font: 30 arial;");
        FXGL.addUINode(scoreText, 20, 680);
    }

    public void update(Entity cat){
        if(lifes != 0){
            manager.update();
            FXGL.getGameWorld().getEntities().stream()
            .filter(m -> m.hasComponent(MiniGameObject.class))
            .toList()
            .forEach(m -> {
                m.getComponent(MiniGameObject.class).update();
            });
            FXGL.getGameWorld().getEntities().stream()
                .filter(m -> m.hasComponent(MiniGameObject.class))
                .toList()
                .forEach(m -> {
                    if(m.getComponent(MiniGameObject.class).isCut()
                        && m.getComponent(MiniGameObject.class).getObjectType() == "bomb")
                            loosingAllLifes();
                    if(m.getComponent(MiniGameObject.class).getGivePoint()){
                        points++;
                        scoreText.setText("Score: " + points);
                        m.getComponent(MiniGameObject.class).setGivePoint(false);
                    }
            });
            generateObjects();
            IncreseDifficulty();
            eraseObjects();
            for(Heart heart : hearts)
                heart.update();
        }
        else{
            if(result == null)
                result = new Result(points);
            result.update();
        }
        
        FXGL.getGameWorld().getEntities().stream()
            .filter(m -> m.hasComponent(Particle.class))
            .toList()
            .forEach(m -> m.getComponent(Particle.class).update());
        
        if(BasicGameApp.getScene() == "Main Scene"){
            FXGL.removeUINode(scoreText);
            result = null;
            for(Heart heart : hearts)
                heart.update();
            cat.getComponent(Cat.class).setGold(cat.getComponent(Cat.class).getGold() + points/3);
        }
    }

    public void loosingAllLifes(){
        while(lifes > 0){
            lifes--;
            hearts[lifes].setAlive(false);
        }
    }

    public void generateObjects(){
        double positionX = FXGLMath.random(10, 740);
        double positionY = -150;
        Entity miniGameObject;

        if(FXGLMath.random(1,1001) <= spawnProc){
            miniGameObject = new Entity();

            if(FXGLMath.random(1,100) <= bombProc){
                miniGameObject.addComponent(new MiniGameObject(positionX, positionY, "miniGame/bomb.png", "bomb", miniGameObjectSpeed));
            }
            else{
                miniGameObject.addComponent(new MiniGameObject(positionX, positionY, "miniGame/wood.png", "wood", miniGameObjectSpeed));
            }
            addObject(miniGameObject);
        }
    }
    
    public void addObject(Entity miniGameObject){
        FXGL.getGameWorld().addEntity(miniGameObject);
        FXGL.getGameWorld().getEntities().stream()
            .filter(m -> m.hasComponent(MiniGameObject.class))
            .toList()
            .forEach(m -> {
                if(m.getComponent(MiniGameObject.class).doesIntersect(miniGameObject)
                    && m != miniGameObject)
                        miniGameObject.removeFromWorld();
            });
    }


    public void IncreseDifficulty(){
        miniGameObjectSpeed += speedBoost;
        FXGL.getGameWorld().getEntities().stream()
            .filter(m -> m.hasComponent(MiniGameObject.class))
            .toList().forEach(m -> {
                m.getComponent(MiniGameObject.class).setSpeed(miniGameObjectSpeed);
        });
        spawnProc += spawnProcBoost;
    }

    public void eraseObjects(){
        FXGL.getGameWorld().getEntities().stream().toList().forEach(m -> {
            if(m.hasComponent(MiniGameObject.class) && m.getY() >= 700){
                if(m.getComponent(MiniGameObject.class).getObjectType() == "wood"
                    && !m.getComponent(MiniGameObject.class).isCut()){
                    lifes--;
                    hearts[lifes].setAlive(false);
                }
                m.removeFromWorld();
            }
        });
    }
}


