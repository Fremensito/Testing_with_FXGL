package com.example.tutorial.MainScene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;

import javafx.scene.shape.Rectangle;

public abstract class Icon{
    protected Texture icon;
    protected Rectangle hitbox;
    protected boolean cursorInside;

    public void setCursorInside(boolean set){
        cursorInside = set;
    }

    public boolean isCursorInside(){
        return cursorInside;
    }

    public Texture getIcon(){
        return icon;
    }
    
    /**
     * Check if the cursor is inside the icon
     */
    protected void checkCursor(){
        if(hitbox.contains(FXGL.getInput().getMousePositionWorld())){
            icon.setScaleX(1.1);
            icon.setScaleY(1.1);
            cursorInside = true;
        }
        else{
            icon.setScaleX(1);
            icon.setScaleY(1);
            cursorInside = false;
        }
    }

    /**
     * Set the icon visible or invisible
     * @param visible true if the icon should be visible, false otherwise
     */
    public void setVisible(boolean visible){
        icon.setVisible(visible);
    }
}
