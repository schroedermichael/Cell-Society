package controller;

import java.io.File;
import org.w3c.dom.Element;
import javafx.scene.Group;
import simulation.Simulation;
import xml.XMLParser;


public class SimulationController {

    private Simulation simulation;
    private XMLParser parser = new XMLParser();
    private Group simulationRoot;

    SimulationController (Group simulationRoot) {
        
        //File simulationConfig = new File("src/resources/Fire.xml");
        this.simulationRoot = simulationRoot;
        File simulationConfig = new File("src/resources/GameOfLife.xml");
        initializeSimulation(simulationConfig.getAbsolutePath());
    }

    /**
     * 
     * @param xmlFilename
     */
    void initializeSimulation (String xmlFilename) {
        Element rootElement = parser.getRootElement(xmlFilename);
        this.simulation = parser.createSimulation(rootElement);
        simulation.removeGridViewSceneGraph(simulation.getGridView().getRoot());
        simulation.addGridViewSceneGraph(simulationRoot);
    }

    public Simulation getSimulation () {
        return simulation;
    }
}
