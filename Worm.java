package SwormGame_v2;


import java.awt.*;
import java.util.LinkedList;


enum Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT
}

class Worm{

    LinkedList<Coords> swormParts;

    static final Color DEFAULT_COLOR=Color.green;

    Worm(Coords inCoords){
        swormParts=new LinkedList<>();

        swormParts.add(new Coords(inCoords));

        for (int i = 1; i <= 2; i++) {
            swormParts.add(new Coords(swormParts.getFirst().getX(),swormParts.getFirst().getY()+i));
        }

    }

}
