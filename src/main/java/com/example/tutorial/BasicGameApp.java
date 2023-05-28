package com.example.tutorial;

import java.time.LocalDateTime;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.example.tutorial.MainScene.Cat;
import com.example.tutorial.MainScene.MainScene;

import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;


public class BasicGameApp extends GameApplication {
    private final int width = 900;
    private final int height = 700;
    private static String scene;
    private Texture menu;
    private Text newGame;
    private Rectangle newGameRect;
    private Text loadGame;
    private Rectangle loadGameRect;
    private boolean inputBlocked = false;
    private byte timer = 0;

    public static String getScene() {
        return scene;
    }

    public static void setScene(String scene) {
        BasicGameApp.scene = scene;
    }
    
    MainScene mainScene;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setTicksPerSecond(60);
        settings.setGameMenuEnabled(false);
    }
    @Override
    protected void initGame() {
        FXGL.getGameScene().setBackgroundRepeat("mainScene/background.png");
        menu = FXGL.getAssetLoader().loadTexture("sheet.png");
        FXGL.addUINode(menu, 180, 180);
        newGame = new Text("New Game");
        newGameRect = new Rectangle(375, 270, 150, 30);
        loadGame = new Text("Load Game");
        loadGameRect = new Rectangle(370, 370, 150, 30);
        addOptions(newGame, 375, 290);
        addOptions(loadGame, 370, 390);
        scene = "Main Menu";
    }

    private void addOptions(Text text, int x, int y){
        text.setFill(javafx.scene.paint.Color.BROWN);
        text.setStyle("-fx-font: 30 arial;");
        FXGL.addUINode(text, x, y);
    }
    @Override
    protected void initInput() {
        FXGL.getInput().addAction(GameController.getMouseController(), MouseButton.PRIMARY);
        FXGL.getInput().addAction(GameController.getEscController(), javafx.scene.input.KeyCode.ESCAPE);
    }

    @Override
    protected void onUpdate(double tpf){
        switch(scene){
            case "Main Menu":
                menuUpdate();
                break;
            case "Main Scene":
                mainScene.update();
                break;
            case "Mini Game":
                mainScene.update();
                break;
        }
    }
    private void menuUpdate(){
        if(inputBlocked){
            timer++;
            if(timer == 20){
                inputBlocked = false;
                timer = 0;
            }
        }
        if(!menu.visibleProperty().get()){
            menu.setVisible(true);
            newGame.setVisible(true);
            loadGame.setVisible(true);
        }
        if(newGameRect.contains(FXGL.getInput().getMousePositionWorld())){
            newGame.setFill(javafx.scene.paint.Color.YELLOW);
            if(GameController.getIsLeftMousePressed() && !inputBlocked){
                changeScene();
                createGame();
            }
        }
        else
            newGame.setFill(javafx.scene.paint.Color.BROWN);
        if(loadGameRect.contains(FXGL.getInput().getMousePositionWorld())){
            loadGame.setFill(javafx.scene.paint.Color.YELLOW);
            if(GameController.getIsLeftMousePressed() && !inputBlocked){
                changeScene();
                mainScene.loadGame();
            }
        }
        else
            loadGame.setFill(javafx.scene.paint.Color.BROWN);
    }
    private void changeScene(){
        inputBlocked = true;
        scene = "Main Scene";
        menu.setVisible(false);
        newGame.setVisible(false);
        loadGame.setVisible(false);
        mainScene = new MainScene();
    }
    private void createGame(){
        mainScene.getCat().getComponent(Cat.class).setGold(0);
        mainScene.getCat().getComponent(Cat.class).getHunger().setQuantity(0);
        mainScene.getCat().getComponent(Cat.class).getHunger().setPrice(5);
        mainScene.getCat().getComponent(Cat.class).getHunger().setLastUpdated(LocalDateTime.now());
        mainScene.getCat().getComponent(Cat.class).getHunger().setPrice(5);
        mainScene.getCat().getComponent(Cat.class).getEnergy().setQuantity(7);
        mainScene.getCat().getComponent(Cat.class).getEnergy().setLastUpdated(LocalDateTime.now());
        mainScene.getCat().getComponent(Cat.class).setGold(0);
        mainScene.getCat().getComponent(Cat.class).setAlive(true);
        mainScene.getCat().getComponent(Cat.class).setSleeping(false);
        mainScene.getCat().getComponent(Cat.class).setGameStart(LocalDateTime.now());
        mainScene.setDay(true);
        mainScene.setDays();
    }
    public static void main(String[] args) {
        launch(args);
    }
}