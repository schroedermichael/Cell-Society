package applicationView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import controller.ApplicationController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SimulationToolbar {
 
    VBox myToolbar;
    List<Slider> mySliders;
    
    public void addSlider (Slider slider, String label) {
        mySliders.add(slider);
        Label sliderLabel = new Label(label);
        sliderLabel.setTextFill(Color.WHITE);
        myToolbar.getChildren().addAll(slider, sliderLabel);
    }
    public VBox getRoot () {
        return myToolbar;
    }
    
    
}