package com.example.tutorial.MainScene;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.example.tutorial.BasicGameApp;
import com.example.tutorial.GameController;
import com.example.tutorial.MiniGame.MiniGameScene;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainScene {
    private MainSceneInputManager manager;
    private MiniGameScene miniGame;
    private MiniGameIcon miniGameIcon;
    private Entity cat;
    private Text gold;
    private Text days;
    private Texture menu;
    private Text saveAndExit;
    private Rectangle saveAndExitRect;
    private Text exit;
    private Rectangle exitRect;
    private Text gameOver;
    private boolean day;

    public Entity getCat() {
        return cat;
    }

    public void setDay(boolean day) {
        this.day = day;
    }

    public void setDays() {
        days = new Text("Days: " + (int) ChronoUnit.DAYS.between(
        cat.getComponent(Cat.class).getGameStart()
        ,cat.getComponent(Cat.class).getHunger().getLastUpdated()));
        days.setFill(Color.GOLDENROD);
        days.setStyle("-fx-font: 30 arial;");
        FXGL.addUINode(days, 20, 450);
    }

    public MainScene(){
        manager = new MainSceneInputManager();
        miniGameIcon = new MiniGameIcon();
        cat = new Entity();
        cat.addComponent(new Cat());
        FXGL.getGameWorld().addEntity(cat);
        gold = new Text("Gold: " + cat.getComponent(Cat.class).getGold());
        generateText(gold, 20, 350);
        menu = FXGL.getAssetLoader().loadTexture("sheet.png");
        FXGL.addUINode(menu, 180, 180);
        menu.setVisible(false);
        saveAndExit = new Text("Save and Exit");
        generateText(saveAndExit, 355, 290);
        saveAndExit.setVisible(false);
        saveAndExitRect = new Rectangle(355, 270, 200, 30);
        exit = new Text("Exit");
        generateText(exit, 415, 390);
        exit.setVisible(false);
        exitRect = new Rectangle(415, 370, 150, 30);
    }

    private void generateText(Text text, int x, int y){
        text.setFill(Color.BROWN);
        text.setStyle("-fx-font: 30 arial;");
        FXGL.addUINode(text, x, y);
    }

    public void update(){
        if(cat.getComponent(Cat.class).isAlive()){
            if(BasicGameApp.getScene() == "Main Scene")
                mainSceneUpdate();
            if(BasicGameApp.getScene() == "Mini Game")
                miniGameSceneUpdate();
        }
        else{
            if(gameOver == null){
                FXGL.getGameScene().setBackgroundColor(Color.BLACK);
                gameOver = new Text("Your pet has escaped cause of starvation after " + (int) ChronoUnit.DAYS.between(
                    cat.getComponent(Cat.class).getGameStart() 
                    ,cat.getComponent(Cat.class).getHunger().getLastUpdated()) + " days");
                gameOver.setFill(Color.GOLDENROD);
                gameOver.setStyle("-fx-font: 30 arial;");
                FXGL.addUINode(gameOver, 100, 350);
                setMainSceneInvisible();
            }
            showMenu();
            manager.update(miniGameIcon, cat);
            if(manager.getExitMenuIsOpen())
                gameOver.setVisible(false);
            else
                gameOver.setVisible(true);
            if(BasicGameApp.getScene() == "Mini Game" || BasicGameApp.getScene() == "Main Menu"){
                setMainSceneInvisible();
                FXGL.getGameScene().setBackgroundRepeat("mainScene/background.png");
                gameOver.setVisible(false);
            }
        }
    }

    public void mainSceneUpdate(){
        manager.update(miniGameIcon, cat);
        showMenu();
        miniGameIcon.update();
        if(cat.getComponent(Cat.class).isSleeping() && day){
            day = false;
            FXGL.getGameScene().setBackgroundRepeat("mainScene/backgroundNight.png");
            cat.setVisible(false);
        }
        if(!cat.getComponent(Cat.class).isSleeping() && !day){
            day = true;
            FXGL.getGameScene().setBackgroundRepeat("mainScene/background.png");
            cat.setVisible(true);
        }
        updateCat();
        if(BasicGameApp.getScene() == "Mini Game" || BasicGameApp.getScene() == "Main Menu"){
            setMainSceneInvisible();
            if(BasicGameApp.getScene() == "Main Menu")
                FXGL.getGameScene().setBackgroundRepeat("mainScene/background.png");
        }
    }

    public void showMenu(){
        if(manager.getExitMenuIsOpen()){
            menu.setVisible(true);
            exit.setVisible(true);
            saveAndExit.setVisible(true);
            if(exitRect.contains(FXGL.getInput().getMousePositionWorld())){
                exit.setFill(Color.YELLOW);
                if(GameController.getIsLeftMousePressed())
                    BasicGameApp.setScene("Main Menu");
            }
            else{
                exit.setFill(Color.BROWN);
            }
            if(saveAndExitRect.contains(FXGL.getInput().getMousePositionWorld())){
                saveAndExit.setFill(Color.YELLOW);
                if(GameController.getIsLeftMousePressed()){
                    BasicGameApp.setScene("Main Menu");
                    saveGame();
                }
            }
            else{
                saveAndExit.setFill(Color.BROWN);
            }
        }
        else{
            exit.setVisible(false);
            saveAndExit.setVisible(false);
            menu.setVisible(false);
        }
    }

    public void saveGame(){
        try{
            PrintWriter writer = new PrintWriter("save.txt");
            writer.println("Energy;" + cat.getComponent(Cat.class).getEnergy().getQuantity());
            writer.println(cat.getComponent(Cat.class).getEnergy().getLastUpdated().toString());
            writer.println("Hunger;" + cat.getComponent(Cat.class).getHunger().getQuantity());
            writer.println("Famine;" + cat.getComponent(Cat.class).getHunger().getFamineLevel());
            writer.println("Price;" + cat.getComponent(Cat.class).getHunger().getPrice());
            writer.println(cat.getComponent(Cat.class).getHunger().getLastUpdated().toString());
            writer.println("Gold;" + cat.getComponent(Cat.class).getGold());
            writer.println("Sleeping;" + cat.getComponent(Cat.class).isSleeping());
            writer.println("Alive;" + cat.getComponent(Cat.class).isAlive());
            writer.println("Day;" + day);
            writer.println(cat.getComponent(Cat.class).getGameStart());
            writer.close();
        }
        catch(Exception e){
            System.out.println("Error saving game");
        }
    }

    public void loadGame(){
        try{
            BufferedReader inputFile = null;
            inputFile = new BufferedReader(new FileReader("save.txt"));
            //read line and split it by ;
            cat.getComponent(Cat.class).getEnergy().setQuantity(Integer.parseInt(inputFile.readLine().split(";")[1]));
            cat.getComponent(Cat.class).getEnergy().setLastUpdated(LocalDateTime.parse(inputFile.readLine()));
            cat.getComponent(Cat.class).getHunger().setQuantity(Integer.parseInt(inputFile.readLine().split(";")[1]));
            cat.getComponent(Cat.class).getHunger().setFamineLevel(Integer.parseInt(inputFile.readLine().split(";")[1]));
            cat.getComponent(Cat.class).getHunger().setPrice(Integer.parseInt(inputFile.readLine().split(";")[1]));
            cat.getComponent(Cat.class).getHunger().setLastUpdated(LocalDateTime.parse(inputFile.readLine()));
            cat.getComponent(Cat.class).setGold(Integer.parseInt(inputFile.readLine().split(";")[1]));
            cat.getComponent(Cat.class).setSleeping(Boolean.parseBoolean(inputFile.readLine().split(";")[1]));
            cat.getComponent(Cat.class).setAlive(Boolean.parseBoolean(inputFile.readLine().split(";")[1]));
            day = Boolean.parseBoolean(inputFile.readLine().split(";")[1]);
            cat.getComponent(Cat.class).setGameStart(LocalDateTime.parse(inputFile.readLine()));
            inputFile.close();
            setDays();
            updateCat();
            if(cat.getComponent(Cat.class).isAlive()){
                if(day){
                    FXGL.getGameScene().setBackgroundRepeat("mainScene/background.png");
                    cat.setVisible(true);
                }
                else{
                    FXGL.getGameScene().setBackgroundRepeat("mainScene/backgroundNight.png");
                    cat.setVisible(false);
                }
            }
        }
        catch(Exception e){
            System.out.println("Error loading game");
        }
    }

    public void updateCat(){
        cat.getComponent(Cat.class).update();
        if(cat.getComponent(Cat.class).getHunger().isCursorInside()){
            gold.setText("Gold: " + (cat.getComponent(Cat.class).getGold() - cat.getComponent(Cat.class).getHunger().getPrice()));
            gold.setFill(Color.RED);
        }
        else{
            gold.setText("Gold: " + cat.getComponent(Cat.class).getGold());
            gold.setFill(Color.GOLDENROD);
        }
    }

    public void setMainSceneInvisible(){
        if(BasicGameApp.getScene() == "Mini Game"){
            FXGL.getGameScene().setBackgroundColor(Color.CADETBLUE);
            miniGame = new MiniGameScene();
        }
        miniGameIcon.setVisible(false);
        cat.getComponent(Cat.class).setVisibility(false);
        miniGameIcon.setCursorInside(false);
        gold.setVisible(false);
        menu.setVisible(false);
        manager.setExitMenuIsOpen(false);
        menu.setVisible(false);
        exit.setVisible(false);
        saveAndExit.setVisible(false);
        days.setVisible(false);
    }

    public void miniGameSceneUpdate(){
        miniGame.update(cat);
        setMainSceneVisible();
    }

    private void setMainSceneVisible(){
        if(BasicGameApp.getScene() == "Main Scene"){
            miniGameIcon.setVisible(true);
            cat.getComponent(Cat.class).setVisibility(true);
            FXGL.getGameScene().setBackgroundRepeat("mainScene/background.png");
            cat.setVisible(true);
            gold.setVisible(true);
            days.setVisible(true);
        }
    }
}
