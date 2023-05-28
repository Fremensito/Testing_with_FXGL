package com.example.tutorial;

import com.almasb.fxgl.input.UserAction;

public class GameController {
    private static boolean isLeftMousePressed = false;
    private static boolean isEscPressed = false;
    private static UserAction mouseController = new UserAction("press space"){
        @Override
        protected void onActionBegin() {
            isLeftMousePressed = true;
        }
        @Override
        protected void onActionEnd() {
            isLeftMousePressed = false;
        }
    };
    
    private static UserAction escController = new UserAction("press esc"){
        @Override
        protected void onActionBegin() {
            isEscPressed = true;
        }
        @Override
        protected void onActionEnd() {
            isEscPressed = false;
        }
    };

    public static boolean getIsLeftMousePressed(){
        return isLeftMousePressed;
    }
    public static boolean getIsEscPressed(){
        return isEscPressed;
    }

    public static UserAction getEscController(){
        return escController;
    }

    public static UserAction getMouseController(){
        return mouseController;
    }
}
