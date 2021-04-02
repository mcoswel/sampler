package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.LetterTile;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.ViewportUtils;

public class TileBreaks extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(TileBreaks.class);
    private Viewport viewport;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private TextureAtlas letterTiles;
    private ShapeRenderer shapeRenderer;
    private Array<LetterTile> LetterTilesArray;
    private float initialPos;
    private final float SIZE = 1f;

    public TileBreaks() {

    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        letterTiles = new TextureAtlas(Gdx.files.internal("letter/letter.atlas"));
        camera = new OrthographicCamera();
        viewport = new FitViewport(16f, 9f, camera);
        shapeRenderer = new ShapeRenderer();
        String s = "BUKU";
        s = s.toLowerCase();
        LetterTilesArray = new Array<LetterTile>();
        initialPos = 10 - s.length() / 2;
        int index = 0;
        for (char c : s.toCharArray()) {
            LetterTilesArray.add(new LetterTile(initialPos + index, 7f, letterTiles.findRegion(String.valueOf(c))));
            index++;
        }
        LetterTilesArray.shuffle();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        ViewportUtils.drawGrid(viewport, shapeRenderer, 1);
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawLetter();
        batch.end();
    }


    private void drawLetter() {
        for (LetterTile t : LetterTilesArray) {
            batch.draw(t.getTextureRegion(),t.getX(), t.getY(),SIZE,SIZE);
            batch.draw(letterTiles.findRegion("blank"),t.getX(), 5f,SIZE,SIZE);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        letterTiles.dispose();
    }

    private void randomX() {

    }
}
