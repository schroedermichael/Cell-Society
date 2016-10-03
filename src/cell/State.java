package cell;
import javafx.scene.paint.Color;

public interface State {
    //getState()
    Color getColor();
    double getProbability();
    void setProbability(double probability);
    String name();
}

//public enum State {
//    
//    EMPTY(0,Color.BLUE),
//    FISH(1,Color.YELLOW),
//    SHARK(2, Color.RED),
//    DEAD(3, Color.WHITE),
//    FIRE(4, Color.RED),
//    X(7, Color.RED),
//    O(8, Color.GREEN),
//    LIVING(9, Color.DARKGREEN),
//    FOODSEARCH(10, Color.BLACK),
//    HOMESEARCH(11, Color.BLACK);
//    
//    
//    private final int myValue;
//    private final Color myColor;
//    State(int value, Color color){
//        myValue = value;
//        myColor = color;
//    }
//    
//    public int getIntValue() {
//        return myValue;
//    }
//    
//    public Color getColor() {
//        return myColor;
//    }
//}
