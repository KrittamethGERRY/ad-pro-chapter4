package se233.chapter4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.model.Keys;

public class GameStage extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public final static int GROUND = 300;
    private Image gameStageImg;
    private GameCharacter gameCharacter;
    private GameCharacter enemyCharacter;
    private Keys keys;

    public GameStage() {
        keys = new Keys();
        gameStageImg = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImg);
        backgroundImg.setFitWidth(WIDTH);
        backgroundImg.setFitHeight(HEIGHT);
        gameCharacter = new GameCharacter(30, 30, 0, 0, KeyCode.A, KeyCode.D, KeyCode.W, "assets/mariosheet.png", 32, 16, 10, 15,4, 1, 4, 32, 64);
        enemyCharacter = new GameCharacter(300, 30, 0, 0, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, "assets/rockman.png", 1028/2, 2705/5, 15,20, 5, 2, 10,64, 64);
        getChildren().addAll(backgroundImg, gameCharacter, enemyCharacter);
    }

    public GameCharacter getGameCharacter() {
        return gameCharacter;
    }

    public GameCharacter getEnemyCharacter() { return enemyCharacter;}

    public Keys getKeys() {
        return keys;
    }
}
