package com.example.tutorial.MainScene;

import java.time.LocalDateTime;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;

import javafx.geometry.Rectangle2D;

public abstract class Vitality extends Icon{
    protected final int SPRITE_WIDTH = 150;
    public LocalDateTime lastUpdated;
    protected final int QUANTITY_LIMIT = 7;
    protected int quantity;
    protected Texture subTexture;
    protected  int positionX;
    protected  int positionY;
    
    public int getQuantityLimit(){
        return QUANTITY_LIMIT;
    }
    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Texture getSubTexture() {
        return subTexture;
    }

    public void addNode(){
        FXGL.addUINode(subTexture, positionX, positionY);
    }

    public void setSubTexture() {
        subTexture = icon.subTexture(new Rectangle2D(SPRITE_WIDTH * quantity, 0, SPRITE_WIDTH, SPRITE_WIDTH));
    }

    public void update(){
        if(subTexture == null){
            subTexture = icon.subTexture(new Rectangle2D(SPRITE_WIDTH * quantity, 0, SPRITE_WIDTH, SPRITE_WIDTH));
            FXGL.addUINode(subTexture, positionX, positionY);
        }
        checkCursor();
    }

    @Override
    public void checkCursor(){
        if(hitbox.contains(FXGL.getInput().getMousePositionWorld())){
            subTexture.setScaleX(1.1);
            subTexture.setScaleY(1.1);
            cursorInside = true;
        }
        else{
            subTexture.setScaleX(1);
            subTexture.setScaleY(1);
            cursorInside = false;
        }
    }

    @Override
    public void setVisible(boolean visible){
        subTexture.setVisible(visible);
    }
}
