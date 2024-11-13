package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    static {
        System.loadLibrary("mine_sweeper");
    }
    enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
    enum Player {
        PLAYER_ONE,
        PLAYER_TWO
    }

    Player currentPlayer;
    int boardSize;

    public static native void initGame(int difficulty);
    public static native int checkSquare(int x, int y);

    public Game() {
        currentPlayer = Player.PLAYER_ONE;
        displayHome();
    }

    public void displayHome() {
        JFrame f = new JFrame("Minesweeper");
        JButton easyButton = createButton(Difficulty.EASY, "Easy");
        JButton mediumButton = createButton(Difficulty.MEDIUM, "Medium");
        JButton hardButton = createButton(Difficulty.HARD, "Hard");

        f.add(easyButton);
        f.add(mediumButton);
        f.add(hardButton);

        f.setSize(400, 400);
        f.setLayout(new GridLayout(3, 1));
        f.setVisible(true);
    }

    private JButton createButton(Difficulty difficulty, String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (difficulty) {
                    case EASY -> boardSize = 8;
                    case MEDIUM -> boardSize = 16;
                    case HARD -> boardSize = 24;
                }
                initGame(difficulty.ordinal());
                displayBoard();
            }
        });
        return button;
    }

    private void displayBoard() {
        JFrame boardFrame = new JFrame("Game Board");
        boardFrame.setSize(600, 600);
        boardFrame.setLayout(new BorderLayout());

        JPanel indicatorPanel = new JPanel();
        JLabel playerIndicator = new JLabel("Current Player: " + currentPlayer);
        indicatorPanel.add(playerIndicator);
        boardFrame.add(indicatorPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton squareButton = new JButton();
                final int x = i;
                final int y = j;

                squareButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int result = checkSquare(x, y);
                        if (result == -1) {
                            JOptionPane.showMessageDialog(boardFrame, "Game Over! "+ currentPlayer +" hit a bomb.", "Game Over", JOptionPane.ERROR_MESSAGE);
                            boardFrame.dispose();
                        } else {
                            squareButton.setText(String.valueOf(result));
                            squareButton.setEnabled(false);

                            togglePlayer(playerIndicator);
                        }
                    }
                });

                squareButton.setPreferredSize(new Dimension(50, 50));
                boardPanel.add(squareButton);
            }
        }

        boardFrame.add(boardPanel, BorderLayout.CENTER);

        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.setVisible(true);
    }


    private void togglePlayer(JLabel playerIndicator) {
        if (currentPlayer == Player.PLAYER_ONE) {
            currentPlayer = Player.PLAYER_TWO;
        } else {
            currentPlayer = Player.PLAYER_ONE;
        }
        playerIndicator.setText("Current Player: " + currentPlayer); // Update the indicator
    }
}
