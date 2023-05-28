package com.example.tutorial.MiniGame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.tutorial.BasicGameApp;
import com.example.tutorial.GameController;

public class MiniGameInputManager {
    public void update(){
        if(GameController.getIsLeftMousePressed() && BasicGameApp.getScene() == "Mini Game"){
            Entity particle = new Entity();
            particle.addComponent(new Particle());
            FXGL.getGameWorld().addEntity(particle);
            cut();
        }
    }

    public void cut(){
        FXGL.getGameWorld().getEntities().stream()
            .filter(m -> m.hasComponent(MiniGameObject.class))
            .toList()
            .forEach(m -> {
                if(m.getComponent(MiniGameObject.class).getHitBox().contains(FXGL.getInput().getMousePositionWorld())
                    && !m.getComponent(MiniGameObject.class).isBeingCut()){
                        m.getComponent(MiniGameObject.class).setBeingCut(true);
                        m.getComponent(MiniGameObject.class).generateCutting(FXGL.getInput().getMousePositionWorld());
                }

                if(!m.getComponent(MiniGameObject.class).getHitBox().contains(FXGL.getInput().getMousePositionWorld())
                    && m.getComponent(MiniGameObject.class).isBeingCut()
                    && !m.getComponent(MiniGameObject.class).isCut()){
                        m.getComponent(MiniGameObject.class).endCutting(FXGL.getInput().getMousePositionWorld());
                }
            });
    }
}