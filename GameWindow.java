package SwormGame_v2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow {

    private JFrame frame;

    private GamePlace gamePlace;

    private GameWindow(){

        frame=new JFrame();

        JPanel mainPanel=new JPanel();
        frame.getContentPane().add(mainPanel);

        int sizeX=800;
        int sizeY=600;
        int cellSize=20;

        sizeX=sizeX-sizeX%cellSize;
        sizeY=sizeY-sizeY%cellSize;
        mainPanel.setSize(sizeX,sizeY);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.setSize(sizeX,sizeY);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));

        gamePlace=new GamePlace(new Coords(sizeX,sizeY),cellSize);
        mainPanel.add(BorderLayout.CENTER,gamePlace);

        mainPanel.setPreferredSize(new Dimension(sizeX,sizeY));

        int changedSizeX=sizeX+cellSize;
        int changedSizeY=sizeY+cellSize;
        mainPanel.setPreferredSize(new Dimension(changedSizeX,changedSizeY));

        frame.pack();

        frame.setResizable(false);

    }

    private void startGame(){

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='w' || e.getKeyChar()=='ц'){
                    gamePlace.tryToMoveWorm(Direction.UP);
                    frame.repaint();
                }
                else
                    if(e.getKeyChar()=='s' || e.getKeyChar()=='ы'){
                        gamePlace.tryToMoveWorm(Direction.DOWN);
                        frame.repaint();
                    }
                    else
                        if(e.getKeyChar()=='a' || e.getKeyChar()=='ф'){
                            gamePlace.tryToMoveWorm(Direction.LEFT);
                            frame.repaint();
                        }
                        else
                            if(e.getKeyChar()=='d' || e.getKeyChar()=='в'){
                                gamePlace.tryToMoveWorm(Direction.RIGHT);
                                frame.repaint();
                            }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        gamePlace.paintWorm();
        gamePlace.addFruit();

        Timer gameCycleTimer=new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameCycle();
            }
        });
        gameCycleTimer.start();

        Timer additionalCycle=new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.repaint();
                gameCycleTimer.setDelay(gamePlace.gameSpeed);
            }
        });
        additionalCycle.start();

    }
    private void gameCycle(){
        gamePlace.autoMoveWorm();

    }

    public static void main(String[] args) {
        GameWindow window=new GameWindow();
        window.startGame();
    }
}
