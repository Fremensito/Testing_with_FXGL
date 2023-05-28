package com.example.tutorial.MiniGame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.example.tutorial.BasicGameApp;
import com.example.tutorial.GameController;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Result {
    private byte counterToAppear;
    private Rectangle toContinue;
    private Text scoreText;
    private Text continueText;
    private Texture sheet;

    public Rectangle getToContinue() {
        return toContinue;
    }

    public Result(int points){
        counterToAppear = 0;
        toContinue = new Rectangle(365, 360, 160, 40);
        sheet = FXGL.getAssetLoader().loadTexture("sheet.png");
        scoreText = new Text("Score: " + points + " = " + points/3 + " Gold");
        scoreText.setFill(javafx.scene.paint.Color.BROWN);
        scoreText.setStyle("-fx-font: 30 arial;");
        continueText = new Text("Continue");
        continueText.setFill(javafx.scene.paint.Color.BROWN);
        continueText.setStyle("-fx-font: 30 arial;");
    }
    
    public void update(){
        if(counterToAppear < 60)
            counterToAppear++;
        
        if(counterToAppear >= 60){
            if(counterToAppear == 60){
                FXGL.addUINode(sheet, 180, 180);
                FXGL.addUINode(scoreText, 330, 300);
                FXGL.addUINode(continueText, 385, 390);
                counterToAppear++;
            }
            if(toContinue.contains(FXGL.getInput().getMousePositionWorld())){
                continueText.setFill(javafx.scene.paint.Color.YELLOW);
                if(GameController.getIsLeftMousePressed()){
                    BasicGameApp.setScene("Main Scene");
                }
            }
            else
                continueText.setFill(javafx.scene.paint.Color.BROWN);
        }

        if(BasicGameApp.getScene() == "Main Scene"){
            FXGL.removeUINode(sheet);
            FXGL.removeUINode(scoreText);
            FXGL.removeUINode(continueText);
        }
    }
}
