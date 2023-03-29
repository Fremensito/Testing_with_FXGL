package com.example.tutorial;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.Node;
import java.util.List;

public class MainScene extends Component {
    private Texture backGround;
    private Texture rod;
    private Texture rodSelected;

    private List<Node> textures;
    @Override
    public void onUpdate(double tpf){
        if(textures != null){
            if(textures.get(textures.indexOf(this.rod)).getBoundsInLocal().contains(FXGL.getInput().getMousePositionWorld())){
                textures.get(textures.indexOf(this.rod)).setVisible(false);
                textures.get(textures.indexOf(this.rodSelected)).setVisible(true);
            }
            else{
                textures.get(textures.indexOf(this.rod)).setVisible(true);
                textures.get(textures.indexOf(this.rodSelected)).setVisible(false);
            }
        }
    }
    public void loadTextures(){
        backGround = FXGL.getAssetLoader().loadTexture("fondo.png");
        entity.getViewComponent().addChild(backGround);
        entity.getComponent(MainScene.class);
    }
}
