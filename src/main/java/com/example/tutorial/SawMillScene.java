package com.example.tutorial;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class SawMillScene extends Component {
    private Texture backGround;
    private List<Entity> woods;
    private double woodsSpeed;
    private double spawnProc;

    public SawMillScene(){
        backGround = FXGL.getAssetLoader().loadTexture("fondo.png");
        woods = new ArrayList<>();
        woodsSpeed = 4;
        spawnProc = 5;
    }

    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(backGround);
    }

    @Override
    public void onUpdate(double tpf) {
        woodsSpeed += 0.0006;
        spawnProc += 0.008;
        if( FXGL.random(1, 1000) <= spawnProc){
            woods.add(FXGL.entityBuilder()
                    .with(new Wood(woodsSpeed))
                    .buildAndAttach()
            );
        }
    }
}
