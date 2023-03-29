package com.example.tutorial;

import com.almasb.fxgl.input.UserAction;

public class GameController {
    private static boolean isRightMousePressed = false;
    private static UserAction mouseController = new UserAction("press space"){
        @Override
        protected void onActionBegin() {
            isRightMousePressed = true;
        }
        @Override
        protected void onActionEnd() {
            isRightMousePressed = false;
        }
    };
    public static boolean getIsRightMousePressed(){
        return isRightMousePressed;
    }

    public static UserAction getMouseController(){
        return mouseController;
    }
}
