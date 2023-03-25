package com.example.tutorial;

import com.almasb.fxgl.entity.component.Component;


import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Cat extends Component {

    private String image = "mainChar.png";
    private float positionX = 306f;
    private float positionY = 503f;
    private boolean walking = true;


    public void setImage(String image) {
        this.image = image;
    }

    public boolean getWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public float getPositionX() {
        return positionX;
    }
    public float getPositionY() {
        return positionY;
    }
    public String getImage() {
        return image;
    }

    @Override
    public void onUpdate(double tpf){
        if(walking)
            walk();
    }
    public void walk(){
        positionX++;
        entity.setPosition(this.positionX, this.positionY);
    }
}
