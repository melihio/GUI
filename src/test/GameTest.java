package test;

import main.Game;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class GameTest {

    Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testInitGameEasy() {
        game.initGame(0);
    }

    @Test
    public void testInitGameMedium() {
        game.initGame(1);
    }

    @Test
    public void testInitGameHard() {
        game.initGame(2);
    }

    @Test
    public void testCheckSquare() {
        game.initGame(0);
        int mineCount = game.checkSquare(5, 5);
        assertTrue(mineCount >= 0);
    }

    @Test
    public void testGameOverOnHitBomb() {
        game.initGame(0);
        //locations of bomb
        int x = 0;
        int y = 0;
        int result = game.checkSquare(5, 5);
        assertEquals(-1, result);
    }
}
