package cell;

import javafx.geometry.Point2D;
import javafx.scene.Node;


public class SharkCell extends Cell {

    private int myMaxBreedTime;
    private int myCurrentBreedTime;
    private int myMaxHealth;
    private int myCurrentHealth;
    
    public SharkCell (int row, int col, int breedTime, Node node) {
        super(State.SHARK, row, col, node);
        myMaxBreedTime = breedTime;
        myCurrentBreedTime = myMaxBreedTime;
    }
    
    public void eat(FishCell fish){
        myCurrentHealth = myMaxHealth;
        fish.setMyNextState(State.DEAD);
    }
    
    public void update () {
        myCurrentBreedTime = myCurrentBreedTime == 0 ? myMaxBreedTime : myCurrentBreedTime--;
        if(myCurrentHealth == 0){
            this.setMyNextState(State.DEAD);
        }
        else{
            myCurrentHealth--;
        }
    }

}
