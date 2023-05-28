package com.example.tutorial.MiniGame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.example.tutorial.BasicGameApp;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class MiniGameObject extends Component{

    private String objectType;
    
    private Texture compacted;
    private Texture cut;
    private Point2D originCutting;
    private boolean isCut;
    private boolean beingCut;
    private int disappearCounter;
    private boolean givePoint;
    
    
    private double positionX;
    private double positionY;
    private Rectangle hitBox;
    private double speed;

    public Texture getCut() {
        return cut;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }


    public boolean isBeingCut() {
        return beingCut;
    }

    public void setBeingCut(boolean beingCut) {
        this.beingCut = beingCut;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
    

    public boolean isCut() {
        return isCut;
    }

    public String getObjectType() {
        return objectType;
    }
    
    public boolean getGivePoint() {
        return givePoint;
    }

    public void setGivePoint(boolean givePoint) {
        this.givePoint = givePoint;
    }

    public MiniGameObject(double positionX, double positionY, String compacted, String type, double speed) {
        objectType = type;

        isCut = false;
        this.positionX = positionX;
        this.positionY = positionY;
        beingCut = false;
        disappearCounter = 0;
        givePoint = false;
        this.speed = speed;

        this.compacted = FXGL.getAssetLoader().loadTexture(compacted);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(compacted);
        entity.setPosition(positionX, positionY);
        hitBox = new Rectangle(positionX, positionY, compacted.getWidth(), compacted.getHeight());
    }

    public void update() {
        positionY = positionY + speed; 

        if(isCut){
            disappearCounter++;
            if(disappearCounter == 20){
                entity.removeFromWorld();
            }
        }
        entity.setPosition(positionX, positionY);
        hitBox.setX(positionX);
        hitBox.setY(positionY);

    }

    @Override
    public void onUpdate(double tpf){
        if(BasicGameApp.getScene() == "Main Scene"){
            entity.removeFromWorld();
        }
    }

    public void generateCutting(Point2D originCutting){
        this.originCutting = originCutting;
    }

    public void endCutting(Point2D endCutting){
        beingCut = false;

        if(endCutting.distance(originCutting) >= compacted.getWidth() - 40){
            if(objectType == "wood"){
                givePoint = true;
                double angle = endCutting.subtract(originCutting).angle(1, 0);
                checkVerticalCut(angle);
                checkDiagonalCut(angle, endCutting);
                checkHorizontalCut(angle);
            }
            else{
                cut = FXGL.getAssetLoader().loadTexture("miniGame/explosion.png");
                confirmCut();
            }
        }
    }

    private void  checkVerticalCut(double angle){
        if((angle <= 110 && angle >= 70)){
            cut = FXGL.getAssetLoader().loadTexture("miniGame/cutWood.png");
            confirmCut();
        }
    }

    private void checkDiagonalCut(double angle, Point2D endCutting){
        if((angle > 20 && angle < 70 && endCutting.getY() > originCutting.getY()) 
            || (angle < 160 && angle > 110 && endCutting.getY() < originCutting.getY())){
            cut = FXGL.getAssetLoader().loadTexture("miniGame/diagonalCutWood.png");
            cut.setRotate(-90);
            confirmCut();
        }
        if((angle > 20 && angle < 70 && endCutting.getY() < originCutting.getY()) 
            || (angle < 160 && angle > 110 && endCutting.getY() > originCutting.getY())){
            cut = FXGL.getAssetLoader().loadTexture("miniGame/diagonalCutWood.png");
            confirmCut();
        }
    }

    private void checkHorizontalCut(double angle){
        if(angle <= 20 || angle >= 160){
            cut = FXGL.getAssetLoader().loadTexture("miniGame/horizontalCutWood.png");
            confirmCut();
        }
    }

    private void confirmCut(){
        entity.getViewComponent().addChild(cut);
        isCut = true;
        givePoint = true;
        compacted.setVisible(false);
    }

    public boolean doesIntersect(Entity miniGameObject){
        if(hitBox.intersects(miniGameObject.getX(), miniGameObject.getY(), compacted.getWidth(), compacted.getHeight())){
            return true;
        }
        else
            return false;
    }
}
