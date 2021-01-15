package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class WheelRotate extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(WheelRotate.class);
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private final float WORLD_WIDTH = 14.4f;
    private final float WORLD_HEIGHT = 21.6f;
    private Viewport viewport;
    private Texture texture,bg;
    private BitmapFont font;
    private float width=WORLD_HEIGHT;
    private float height = WORLD_HEIGHT;
    private String msg;
    private float x,y,originX, originY,w,h,scale,rot,srcx,srcy,srcW, srcH;
    private int y1,y2;
    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("raw/wheel.png"));
        bg = new Texture(Gdx.files.internal("raw/bggg.png"));
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));
        Gdx.input.setInputProcessor(this);
        msg = "MESSAGES";
        scale = 1.0f;
        w=WORLD_WIDTH;
        h=w;
        originX=WORLD_WIDTH/2;
        originY=WORLD_WIDTH/2;
        srcW=texture.getWidth();
        srcH=texture.getHeight();
        x=0;
        y=WORLD_HEIGHT/2-WORLD_WIDTH/2;
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        GdxUtils.clearScreen();
        batch.begin();
        draw();
        batch.end();
    }

    private void draw() {
        batch.draw(texture,
                x,y,                                    //x,y
                originX, originY,        //originx, originy
                w, h,                              //width, height
                scale,scale,                     //scale
                rot,                              //rotation
                (int)srcx,(int)srcy,                            //srcx srcy
                (int)srcW, (int)srcH,    //src width, src height
                false,false
        );
        font.draw(batch, msg, WORLD_WIDTH-200,720);
        batch.draw(bg,0,0,WORLD_WIDTH,WORLD_HEIGHT);
        if (rot>0){
            rot-=10f;
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.Q){
            msg = "x++"+x;
            x+=0.2;
        }
        if (keycode == Input.Keys.W){
            msg = "y++"+y;
            y+=0.2;
        }
        if (keycode == Input.Keys.E){
            msg = "originX++";
            originX+=0.2;
        }
        if (keycode == Input.Keys.R){
            msg = "originY++";
            originY+=0.2;
        }
        if (keycode == Input.Keys.T){
            msg = "w++";
            w+=0.2;
        }
        if (keycode == Input.Keys.Y){
            msg = "h++";
            h+=0.2;
        }

        if (keycode == Input.Keys.A){
            msg = "scale++";
            scale+=0.2;
        }
        if (keycode == Input.Keys.S){
            msg = "rotate";
            rot+=100f;
        }

        if (keycode == Input.Keys.D){
            msg = "srcX++";
            srcx++;
        }
        if (keycode == Input.Keys.F){
            msg = "srcY++";
            srcy++;
        }
        if (keycode == Input.Keys.G){
            msg = "srcW++";
            srcW ++;
        }
        if (keycode == Input.Keys.H){
            msg = "srcH++";
            srcH ++;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        y1=screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        y2 = screenY;
        rot=y2-y1;
        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
