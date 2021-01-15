package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class ShapeRendererSample extends SampleBase {

    public static SampleInfo SAMPLE_INFO = new SampleInfo(ShapeRendererSample.class);
    private static final float WORLD_HEIGHT = 40f;
    private static final float WORLD_WIDTH = 20f;
    private static OrthographicCamera camera;
    private static Viewport viewport;
    private static SpriteBatch batch;
    private ShapeRenderer renderer;
    private boolean drawGrids = true;
    private boolean drawCircle = true;
    private boolean drawRectangle = true;
    private boolean drawPoints = true;
    private BitmapFont font;
    public static final Logger log = new Logger(ShapeRendererSample.class.getName(), Logger.DEBUG);
    private float touchY=0;
    private float touchX=0;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));
        font.getData().setScale(0.2f);

        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render() {
        GdxUtils.clearScreen();
        renderer.setProjectionMatrix(camera.combined);
        if (drawGrids) {
            drawGrids();
        }
        if (drawCircle) {
            drawCircle();
        }

        if (drawRectangle) {
            drawRectangle();
        }

        if (drawPoints){
            drawPoints();
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.draw(batch,
                String.valueOf(touchX)+", "+String.valueOf(touchY),
                -9f,18f);

        batch.end();
    }

    private void drawGrids() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        int worldWidth = (int) WORLD_WIDTH;
        int worldHeight = (int) WORLD_HEIGHT;
        renderer.line(0, 0, 0, 100);
        for (int x = -worldWidth; x < worldHeight; x++) {
            renderer.line(x, -worldHeight, x, worldHeight);
        }
        for (int y = -worldHeight; y < worldWidth; y++) {
            renderer.line(-worldWidth, y, worldWidth, y);
        }
        renderer.setColor(Color.RED);
        renderer.line(-worldWidth, 0.0f, worldWidth, 0.0f);
        renderer.line(0.0f, -worldHeight, 0.0f, worldHeight);
        renderer.end();
    }

    private void drawCircle() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.circle(0, 0, 4f, 100);
        renderer.end();
    }

    private void drawRectangle() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.rect(-9f, -10f, 1f, 10f);
        renderer.rect(4f, 0, 4f, 4f);
        renderer.end();
    }

    private void drawPoints(){
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.x(8f,10f,0.25f);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.point(8f,-10f,0f);
        renderer.point(8f,-8f,1f);
        renderer.line(0,0,touchX,-touchY);
        renderer.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) {
            drawCircle = !drawCircle;
        }
        if (keycode == Input.Keys.S) {
            drawRectangle = !drawRectangle;
        }
        if (keycode == Input.Keys.D) {
            drawPoints = !drawPoints;
        }

        if (keycode == Input.Keys.F) {
            drawGrids = !drawGrids;
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        touchY = Gdx.input.getY()*40/Gdx.graphics.getHeight()-20;
        touchX = Gdx.input.getX()*20/Gdx.graphics.getWidth()-20;
        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }
}
