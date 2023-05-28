package com.example.tutorial.MainScene;

import java.time.LocalDateTime;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.tutorial.BasicGameApp;
import com.example.tutorial.GameController;

public class MainSceneInputManager {
    private boolean exitMenuIsOpen = false;
    private byte timer = 0;
    private boolean inputBlocked = false;

    public boolean getExitMenuIsOpen(){
        return exitMenuIsOpen;
    }

    public void setExitMenuIsOpen(boolean exitMenuIsOpen){
        this.exitMenuIsOpen = exitMenuIsOpen;
    }
    public void update(MiniGameIcon miniGameIcon, Entity cat){
        if(inputBlocked){
            timer++;
            if(timer == 20){
                timer = 0;
                inputBlocked = false;
            }
        }
        if(!cat.getComponent(Cat.class).isSleeping() && cat.getComponent(Cat.class).isAlive()){
            toPlay(miniGameIcon, cat);
            feed(cat);
            asleep(cat);
        }
        openMenu();
    }

    /**
     * Open the mini game if the cursor is inside the mini game icon and the left mouse button is pressed
     * @param miniGameIcon the mini game icon
     * @param cat the pet to take care of
     */
    private void toPlay(MiniGameIcon miniGameIcon, Entity cat){
        if(miniGameIcon.isCursorInside() && GameController.getIsLeftMousePressed() && !inputBlocked
            && cat.getComponent(Cat.class).getEnergy().getQuantity() > 0){
            if(cat.getComponent(Cat.class).getHunger().getQuantity() < cat.getComponent(Cat.class).getHunger().getQuantityLimit()){
                cat.getComponent(Cat.class).getHunger().setQuantity(cat.getComponent(Cat.class).getHunger().getQuantity() + 1);
                FXGL.removeUINode(cat.getComponent(Cat.class).getHunger().getSubTexture());
                cat.getComponent(Cat.class).getHunger().setSubTexture();
                cat.getComponent(Cat.class).getHunger().addNode();
            }
            if(cat.getComponent(Cat.class).getEnergy().getQuantity() > 0){
                cat.getComponent(Cat.class).getEnergy().setQuantity(cat.getComponent(Cat.class).getEnergy().getQuantity() - 1);
                FXGL.removeUINode(cat.getComponent(Cat.class).getEnergy().getSubTexture());
                cat.getComponent(Cat.class).getEnergy().setSubTexture();
                cat.getComponent(Cat.class).getEnergy().addNode();
            }
            BasicGameApp.setScene("Mini Game");
            inputBlocked = true;
        }
    }

    /**
     * Open the menu if the escape key is pressed
     */
    private void openMenu(){
        if(GameController.getIsEscPressed() && !inputBlocked){
            if(exitMenuIsOpen){
                exitMenuIsOpen = false;
            }
            else{
                exitMenuIsOpen = true;
            }
            inputBlocked = true;
        }
    }

    /**
     * Feed the cat if the cursor is inside the hunger icon and the left mouse button is pressed
     * @param cat the pet to take care of
     */
    private void feed(Entity cat){
        if(cat.getComponent(Cat.class).getHunger().isCursorInside()
            && cat.getComponent(Cat.class).getGold() >= cat.getComponent(Cat.class).getHunger().getPrice()
            && cat.getComponent(Cat.class).getHunger().getQuantity() > 0
            && GameController.getIsLeftMousePressed() && !inputBlocked){
                cat.getComponent(Cat.class).getHunger().setFamineLevel(0);
                cat.getComponent(Cat.class).getHunger().setQuantity(cat.getComponent(Cat.class).getHunger().getQuantity() - 1);
                FXGL.removeUINode(cat.getComponent(Cat.class).getHunger().getSubTexture());
                cat.getComponent(Cat.class).getHunger().setSubTexture();
                cat.getComponent(Cat.class).getHunger().addNode();
                cat.getComponent(Cat.class).setGold(cat.getComponent(Cat.class).getGold() - cat.getComponent(Cat.class).getHunger().getPrice());
                cat.getComponent(Cat.class).getHunger().setLastUpdated(LocalDateTime.now());
                inputBlocked = true;
        }
    }

    /**
     * Make the cat sleep if the cursor is inside the energy icon and the left mouse button is pressed
     * @param cat the pet to take care of
     */
    private void asleep(Entity cat){
        if(cat.getComponent(Cat.class).getEnergy().getQuantity() < cat.getComponent(Cat.class).getEnergy().getQuantityLimit()
            && cat.getComponent(Cat.class).getEnergy().isCursorInside() && GameController.getIsLeftMousePressed() && !inputBlocked){
                cat.getComponent(Cat.class).setSleeping(true);
                cat.getComponent(Cat.class).getEnergy().setLastUpdated(LocalDateTime.now());
        }
    }
}
