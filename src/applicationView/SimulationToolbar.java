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
    private final ResourceBundle GUIResources;
    ApplicationController myAppController = new ApplicationController();
    Group root;
    List<Slider> mySliders;
    private Series<Number, Number> firstSeries = new XYChart.Series<Number, Number>();
    private Series<Number, Number> secondSeries= new XYChart.Series<Number, Number>();
    private Series<Number, Number> thirdSeries= new XYChart.Series<Number, Number>();
    private ArrayList<Integer> myGraphValues;
    VBox myToolbar;
    public SimulationToolbar () {
        myToolbar = new VBox();
        mySliders = new ArrayList<Slider>();
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
    private LineChart<Number, Number> myLineChart =
            new LineChart<Number, Number>(new NumberAxis(), new NumberAxis());
    private LineChart<Number, Number> createGraph (Scene myScene) {
        final NumberAxis x_axis = new NumberAxis();
        final NumberAxis y_axis = new NumberAxis();
        x_axis.setLabel(GUIResources.getString("XAxis"));
        // myLineChart.setTitle(GUIResources.getString("ChartTitle"));
        myLineChart.setPrefWidth(500);
        myLineChart.setPrefHeight(40);
        myLineChart.setTranslateY(370);
        return myLineChart;
    }
    
    public Series<Number, Number> updateGraph (List<Integer> myOutput) {
        firstSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(1)));
        if(myOutput.size() > 2) {
            secondSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(2)));
        }
        if(myOutput.size() >3) {
            thirdSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(3)));
        }
        return firstSeries;
    }

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