package com.sampler;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

public class OrthographicCameraSample extends SampleBase {
    public static final Logger log = new Logger(OrthographicCameraSample.class.getName(), Logger.DEBUG);
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(OrthographicCameraSample.class);
    public static final float WORLD_WIDTH = 10.8f;
    public static final float WORLD_HEIGHT = 7.2f;
    public static final float CAMERA_SPEED = 6.0f;
    public static final float CAMERA_ZOOM_SPEED = 6.0f;

    private OrthographicCamera camera;
    private Batch batch;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private Texture texture;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("raw/level-bg.png"));
    }

    @Override
    public void render() {
        queryInput();
        GdxUtils.clearScreen();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        batch.end();
    }

    private void queryInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= deltaTime * CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += deltaTime * CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y -= deltaTime * CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y += deltaTime * CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom += deltaTime * CAMERA_ZOOM_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom -= deltaTime * CAMERA_ZOOM_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {

        }
        camera.update();
    }

    private void draw() {
        batch.draw(texture,
                0, 0,
                WORLD_WIDTH, WORLD_HEIGHT);
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
