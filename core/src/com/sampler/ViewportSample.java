package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;

import sun.awt.geom.AreaOp;

public class ViewportSample extends SampleBase {
    private OrthographicCamera camera;
    private Viewport currentViewport;
    private BitmapFont font;
    private SpriteBatch batch;
    private Texture texture;
    private ArrayMap<String, Viewport> viewports = new ArrayMap<String, Viewport>();
    private int currentViewportIndex;
    private String currentViewportName;
    private static final float WORLD_HEIGHT = 600.0f;
    private static final float WORLD_WIDTH = 800.0f;
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ViewportSample.class);

    @Override
    public void create() {
        camera = new OrthographicCamera();
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("raw/level-bg-small.png"));
        createViewPorts();
        selectNextViewports();
        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void resize(int width, int height) {
        currentViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        GdxUtils.clearScreen();
        batch.begin();
        draw();
        batch.end();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        selectNextViewports();
        return true;
    }

    private void draw() {
        batch.draw(texture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        font.draw(batch, currentViewportName, 50, 100);
    }

    private void selectNextViewports() {
        currentViewportIndex = (currentViewportIndex + 1) % viewports.size;
        currentViewport = viewports.getValueAt(currentViewportIndex);
        currentViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        currentViewportName = viewports.getKeyAt(currentViewportIndex);

    }

    private void createViewPorts() {
        viewports.put(StretchViewport.class.getSimpleName(), new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        viewports.put(FillViewport.class.getSimpleName(), new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        viewports.put(FitViewport.class.getSimpleName(), new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        viewports.put(ScreenViewport.class.getSimpleName(), new ScreenViewport(camera));
        viewports.put(ScalingViewport.class.getSimpleName(), new ScreenViewport(camera));
        viewports.put(ExtendViewport.class.getSimpleName(), new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));
        currentViewportIndex = -1;
    }
}
