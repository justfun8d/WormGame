package SwormGame_v2;

class Coords{
    private int x;
    private int y;

    boolean isEquals(Coords checkCoords){
        if(getX()==checkCoords.getX() && getY()==checkCoords.getY()){
            return true;
        }
        return false;
    }

    int getX(){
        return x;
    }

    void setX(int newX){
        x=newX;
    }

    void setY(int newY){
        y=newY;
    }

    int getY(){
        return y;
    }

    void setNewValue(Coords newCoords){
        x=newCoords.x;
        y=newCoords.y;
    }

    Coords(int inX, int inY){
        x=inX;
        y=inY;
    }

    Coords(Coords newCoords){
        x=newCoords.x;
        y=newCoords.y;
    }

    Coords(){

    }

    @Override
    public String toString() {
        return x+" "+y;
    }
}


