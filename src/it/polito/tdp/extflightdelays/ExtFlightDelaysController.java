

/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import it.polito.tdp.extflightdelays.model.NeighborsAirport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="numeroVoliTxtInput"
    private TextField numeroVoliTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	double distanza = Double.parseDouble(this.distanzaMinima.getText());
    		this.model.creaGrafo(distanza);
    		this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.getOriginAirport());

    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	 Airport nameAirport = this.cmbBoxAeroportoPartenza.getValue();
    	 txtResult.clear();
    	 txtResult.setText("Lista aereoporti vicini:\n");
    	 for(NeighborsAirport na : this.model.getNeighbour(nameAirport)) {
    		 txtResult.appendText("- " +na.toString() + "\n");
    	 }
    	 

    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	txtResult.clear();
    	double distanzaMax = Double.parseDouble(this.numeroVoliTxtInput.getText());
    	System.out.println(distanzaMax);
    	 Airport nameAirport = this.cmbBoxAeroportoPartenza.getValue();
    	 double sum = 0.0;
    	 txtResult.setText("Viaggio con più aereoporti: \n");
    	 for(NeighborsAirport na : this.model.listNeigbourAirport(nameAirport, distanzaMax)) {
    		 sum += na.getDistance();
    		 txtResult.appendText(na.toString() + "\n");
    	 }
    	 txtResult.appendText("Distanza totale: " + sum);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;
		
		
		
	}
}

