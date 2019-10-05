package SwormGame_v2;

import java.awt.*;

class Cell{

    private Color color;

    static final Color DEFAULT_COLOR=Color.gray;

    Cell(){
        this(DEFAULT_COLOR);
    }

    Cell(Color newColor){
        color=newColor;
    }

    Color getColor(){
        return color;
    }

    void setColor(Color newColor){
        color=newColor;
    }
}
