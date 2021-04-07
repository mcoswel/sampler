package com.sampler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sampler.common.LetterTile;
import com.sampler.common.SampleBase;
import com.sampler.common.SampleInfo;
import com.sampler.utils.GdxUtils;
import com.sampler.utils.ViewportUtils;

public class TileBreaks extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(TileBreaks.class);
    private Viewport viewport;
    private Viewport hudViewport;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private Stage stage;
    private SpriteBatch batch;
    private TextureAtlas letterTiles;
    private ShapeRenderer shapeRenderer;
    private Array<LetterTile> letterTilesArray;
    private Array<LetterTile> blankArray;
    private float initialPos;
    private final float SIZE = 1f;
    private static final Logger log = new Logger(TileBreaks.class.getName(), 3);
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private Array<Vector2> shuffledPosition;
    private DragAndDrop dragAndDrop;
    private Image sourceImage;
    private Rectangle debugRect;
    private static final Color NORMAL = new Color(1f, 1f, 1f, 1f);
    private static final Color LOWALPHA = new Color(1f, 1f, 1f, 0.6f);

    private String question;
    private String debugString = "debug";

    public TileBreaks() {


    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        letterTiles = new TextureAtlas(Gdx.files.internal("letter/letter.atlas"));
        camera = new OrthographicCamera();
        hudCamera = new OrthographicCamera();
        viewport = new FitViewport(16f, 9f, camera);
        hudViewport = new FitViewport(850f, 550f, camera);
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));
        stage = new Stage(viewport);
        question = "KERUSI";
        question = question.toLowerCase();
        Gdx.input.setInputProcessor(stage);
        assignLetterTilesArray();
        declareShufflePosition();

    }

    private void assignLetterTilesArray() {
        initialPos = 10 - question.length() / 2;
        int index = 0;
        letterTilesArray = new Array<LetterTile>();
        blankArray = new Array<LetterTile>();
        for (char c : question.toCharArray()) {
            String cStr = String.valueOf(c);
            blankArray.add(new LetterTile(initialPos + index, 7f, letterTiles.findRegion("blank"), "blank"));
            stage.addActor(blankArray.get(index).getImage());
            letterTilesArray.add(new LetterTile(initialPos + index, 7f, letterTiles.findRegion(String.valueOf(c)), cStr));
            index++;
        }
        for (int i = 0; i < letterTilesArray.size; i++) {
            final Image thisActor = letterTilesArray.get(i).getImage();
            final String letter = letterTilesArray.get(i).getLetter();
            thisActor.addListener(new DragListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    thisActor.remove();
                    stage.addActor(thisActor);
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    super.touchDragged(event, x, y, pointer);
                    thisActor.moveBy(x - thisActor.getWidth() / 2, y - thisActor.getHeight() / 2);
                    thisActor.setColor(LOWALPHA);
                    highlightOverlap(thisActor);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    setPosition(thisActor, letter);
                    thisActor.setColor(NORMAL);
                    checkBlankOccupation();
                }
            });
            stage.addActor(letterTilesArray.get(i).getImage());
        }
    }

    private void declareShufflePosition() {
        shuffledPosition = new Array<Vector2>();
        for (int i = 4; i < 16; i++) {
            shuffledPosition.add(new Vector2(i, MathUtils.random(1f, 3f)));
        }
        shuffledPosition.shuffle();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {

                for (int i = 0; i < letterTilesArray.size; i++) {
                    letterTilesArray.get(i).getImage().addAction(Actions.moveTo(shuffledPosition.get(i).x, shuffledPosition.get(i).y, 0.3f));
                }
            }
        }, 0.5f);
    }

    @Override
    public void resize(int width, int height) {
        hudViewport.update(width, height, true);
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        GdxUtils.clearScreen();
        drawDebug();

        viewport.apply();
        stage.act();
        stage.draw();
        ViewportUtils.drawGrid(viewport, shapeRenderer, 1);

        if (debugRect != null) {
            //  drawRectDebug();
        }
    }

    private void drawDebug() {
        hudViewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        glyphLayout = new GlyphLayout(font,debugString);
        font.draw(batch,glyphLayout,50,60);
        batch.end();
    }




    @Override
    public void dispose() {
        batch.dispose();
        letterTiles.dispose();
    }

    private void highlightOverlap(Image source) {
        for (LetterTile blank : blankArray) {
            Rectangle r = new Rectangle((int) blank.getImage().getX(), (int) blank.getImage().getY(),
                    (int) blank.getImage().getWidth(), (int) blank.getImage().getHeight());
            Rectangle r1 = new Rectangle(source.getX(), source.getY(), source.getWidth(), source.getHeight());
            debugRect = r1;
            source.setPosition(debugRect.x, debugRect.y);
            Rectangle result = new Rectangle();
            Intersector.intersectRectangles(r, r1, result);

            if (result.area() > 0.5f) {
                blank.getImage().setColor(Color.RED);
                //   source.setPosition(blank.getX(), blank.getY());
            } else {
                blank.getImage().setColor(NORMAL);
            }
        }
    }

    private void setPosition(Image source, String letter) {

        for (LetterTile blank : blankArray) {
            if (blank.getImage().getColor().equals(Color.RED)) {
                source.setPosition(blank.getX(), blank.getY());
                blank.setLetter(letter);
                break;
            }
        }

        for (LetterTile blank : blankArray) {
            blank.getImage().setColor(NORMAL);
        }

    }

    private void checkBlankOccupation() {
        int count = 0;

        boolean correctAnswer = false;

        StringBuilder checkAnswer = new StringBuilder();

        for (LetterTile blank : blankArray) {
            checkAnswer.append(blank.getLetter());
            for (LetterTile letter : letterTilesArray) {
                if (letter.getImage().getX() == blank.getImage().getX() && letter.getImage().getY() == blank.getImage().getY()) {
                    count++;
                }
            }
        }

        if (checkAnswer.toString().equals(question)) {
            correctAnswer = true;
        }

        if (count == letterTilesArray.size && !correctAnswer) {
            declareShufflePosition();
        }
        debugString = checkAnswer.toString();
    }
}
