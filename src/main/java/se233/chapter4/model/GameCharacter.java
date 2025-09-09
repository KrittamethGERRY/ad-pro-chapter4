package se233.chapter4.model;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.view.GameStage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameCharacter extends Pane {
    private static final Logger logger = LogManager.getLogger(GameCharacter.class);
    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;
    private Image gameCharacterImage;
    private AnimatedSprite imageView;
    private int x;
    private int y;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int xVelocity = 0;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    int yVelocity = 0;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity = 7;
    int yMaxVelocity = 17;

    public GameCharacter(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey
            , String spriteSheetPath, int spriteHeight, int spriteWidth, int xMaxVelocity, int yMaxVelocity, int columns, int rows, int count) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.gameCharacterImage = new Image(Launcher.class.getResourceAsStream(spriteSheetPath));
        this.imageView = new AnimatedSprite(gameCharacterImage, count, columns, rows, offsetX, offsetY, spriteWidth, spriteHeight);
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.xMaxVelocity = xMaxVelocity;
        this.yMaxVelocity = yMaxVelocity;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().add(this.imageView);
        System.out.println("Character Created" + "(" + x + "," + y + ")");
    }

    public void moveX() {
        setTranslateX(x);
        if (isMoveLeft) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x-=xVelocity;
        }
        if (isMoveRight) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x+=xVelocity;
        }
    }

    public void moveY() {
        setTranslateY(y);

        if (isFalling) {
            yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yVelocity + yAcceleration;
            y += yVelocity;
        } else if  (isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAcceleration;
            y -= yVelocity;
        }
    }

    public AnimatedSprite getImageView() { return this.imageView; }

    public void checkReachFloor() {
        if (isFalling & y >= GameStage.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }

    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
            debugCollision();
        } else if (x + getWidth() >= GameStage.WIDTH) {
            x = GameStage.WIDTH - (int) getWidth();
            debugCollision();
        }
    }

    public void repaint() {
        moveX();
        moveY();
    }

    public void moveLeft() {
        setScaleX(-1);
        isMoveLeft = true;
        isMoveRight = false;
    }

    public void moveRight() {
        setScaleX(1);
        isMoveLeft = false;
        isMoveRight = true;
    }

    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }

    public void checkReachHighest() {
        if (isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }

    public void stop() {
        xVelocity = 0;
        isMoveLeft = false;
        isMoveRight = false;
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public KeyCode getUpKey() {
        return upKey;
    }

    public void trace() {
        logger.info("x:{} y:{} vx:{} vy:{}", x, y, xVelocity, yVelocity);
    }

    public void debugCollision() {
        logger.debug("Hit the wall boundary");
    }
}
