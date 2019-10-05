package SwormGame_v2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

class GamePlace extends JPanel {

    private ArrayList<ArrayList<Cell>> cellsList;

    private static final int DEFAULT_GAME_SPEED=500;

    int gameSpeed=DEFAULT_GAME_SPEED;

    private Coords placeSize;

    private Coords wormShadow;

    private Worm worm;

    private Coords fruitCoords;

    private static final Color DEFAULT_FRUIT_COLOR=Color.orange;

    private int cellSize;

    private static final int spaceSize=1;

    private void speedUpGame(){
        gameSpeed=gameSpeed-(10*gameSpeed/DEFAULT_GAME_SPEED);
    }

    void addFruit(){
        fruitCoords=getRandomEmptyCell();
        cellsList.get(fruitCoords.getX()).get(fruitCoords.getY()).setColor(DEFAULT_FRUIT_COLOR);
        speedUpGame();
    }

    private Coords getRandomEmptyCell(){

        LinkedList<Coords> acceptableCells=new LinkedList<>();

        for (int i = 0; i < cellsList.size(); i++) {
            for (int j = 0; j < cellsList.get(i).size(); j++) {
                if(cellsList.get(i).get(j).getColor()==Cell.DEFAULT_COLOR){
                    acceptableCells.add(new Coords(i,j));
                }
            }
        }

        if(acceptableCells.size()==0){
            return new Coords(0,0);
        }
        else{
            return acceptableCells.get((int)(Math.random()*acceptableCells.size()));
        }

    }

    private void goToExit(){
        System.exit(0);
    }

    void autoMoveWorm(){

        if(worm.swormParts.get(0).getX()> worm.swormParts.get(1).getX()){
            tryToMoveWorm(Direction.RIGHT);
        }
        else if(worm.swormParts.get(0).getX()< worm.swormParts.get(1).getX()){
            tryToMoveWorm(Direction.LEFT);
        }
        else if(worm.swormParts.get(0).getY()> worm.swormParts.get(1).getY()){
            tryToMoveWorm(Direction.DOWN);
        }
        else if(worm.swormParts.get(0).getY()< worm.swormParts.get(1).getY()){
            tryToMoveWorm(Direction.UP);
        }
    }

    void tryToMoveWorm(Direction direction) {

        Coords nextCoord = new Coords();
        nextCoord.setNewValue(worm.swormParts.getFirst());

        if (direction == Direction.UP) {
            nextCoord.setY(nextCoord.getY() - 1);
        } else if (direction == Direction.DOWN) {
            nextCoord.setY(nextCoord.getY() + 1);
        } else if (direction == Direction.LEFT) {
            nextCoord.setX(nextCoord.getX() - 1);
        } else if (direction == Direction.RIGHT) {
            nextCoord.setX(nextCoord.getX() + 1);
        }

        if(worm.swormParts.get(1).isEquals(nextCoord)){
            return;
        }

        moveSworm(nextCoord);

    }

    private void moveSworm(Coords nextCoord) {

        if (isInPlace(nextCoord) && !isStuckInHimself(nextCoord)){

            cellsList.get(wormShadow.getX()).get(wormShadow.getY()).setColor(Cell.DEFAULT_COLOR);

            Coords newPart = new Coords(worm.swormParts.getLast());

            if (worm.swormParts.size() > 1) {
                for (int i = worm.swormParts.size() - 1; i > 0; i--) {
                    worm.swormParts.get(i).setNewValue(worm.swormParts.get(i - 1));
                }
            }
            worm.swormParts.getFirst().setNewValue(nextCoord);

            if (nextCoord.isEquals(fruitCoords)) {
                worm.swormParts.add(newPart);
                addFruit();
            }
            wormShadow = worm.swormParts.getLast();

        } else {
            goToExit();
        }
        paintWorm();
    }

    private boolean isStuckInHimself(Coords nextCoord){
        for (Coords currentPart : worm.swormParts) {
            if(nextCoord.isEquals(currentPart)){
                return true;
            }
        }
        return false;
    }

    private boolean isInPlace(Coords nextCoord){
        if(nextCoord.getX()>=0 && nextCoord.getX()<cellsList.size() && nextCoord.getY()>=0 && nextCoord.getY()<cellsList.get(0).size()){
            return true;
        }
        else{
            return false;
        }
    }

    void paintWorm(){
        for (Coords swormPart : worm.swormParts) {
            cellsList
                    .get(swormPart.getX())
                    .get(swormPart.getY())
                    .setColor(Worm.DEFAULT_COLOR);
        }
    }

    private Coords chechToMinimumSize(Coords inSize, int inCellSize){
        int minimunSizeXY=6;
        if(inSize.getX()<minimunSizeXY*inCellSize){
            inSize.setX(minimunSizeXY*inCellSize);
        }
        if(inSize.getY()<minimunSizeXY*inCellSize){
            inSize.setY(minimunSizeXY*inCellSize);
        }

        return inSize;
    }

    GamePlace(Coords size, int inCellSize) {

        size=chechToMinimumSize(size,inCellSize);

        cellsList = new ArrayList<>();

        placeSize = new Coords(size.getX(), size.getY());
        cellSize = inCellSize;

        int counterX=0;
        for (int i = 0; i <= placeSize.getX() - spaceSize; i += cellSize + spaceSize) {
            cellsList.add(new ArrayList<>());

            for (int j = 0; j <= placeSize.getY() - spaceSize; j += cellSize + spaceSize) {
                cellsList.get(counterX).add(new Cell());
            }
            counterX++;
        }

        worm =new Worm(new Coords(size.getX()/inCellSize/2-1,size.getY()/inCellSize/2-1));
        wormShadow = worm.swormParts.getLast();

    }

    @Override
    protected void paintComponent(Graphics g) {

        int counterX = 0;
        int counterY = 0;

        for (int i = 0; i <= placeSize.getX() - spaceSize; i += cellSize + spaceSize) {
            for (int j = 0; j <= placeSize.getY() - spaceSize; j += cellSize + spaceSize) {

                Color thisColor = cellsList.get(counterX).get(counterY).getColor();

                g.setColor(thisColor);
                g.fillRect(i, j, cellSize, cellSize);

                counterY++;
            }
            counterX++;
            counterY = 0;
        }
    }

}
