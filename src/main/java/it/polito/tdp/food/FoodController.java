/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Correlate;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo... \n");
    	String p1 = boxPorzioni.getValue();
    	if(p1 == null) {
    		txtResult.appendText("Devi scegliere una porzione");
    		return;
    	}
    	String sn = txtPassi.getText();
    	int N = 0;
    	try {
    		N = Integer.parseInt(sn);
    	} catch(NumberFormatException ne) {
    		txtResult.appendText("Devi inserire un numero intero!");
    		return;
    	}
    	this.model.camminoOttimo(p1, N);
    	for(String s: this.model.getCammino()) {
    		txtResult.appendText(s +"\n");
    	}
    	txtResult.appendText("Peso: " + this.model.getPesoMax());
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate... \n");
    	String p1 = boxPorzioni.getValue();
    	if(p1 == null) {
    		txtResult.appendText("Devi scegliere una porzione");
    		return;
    	}
    	for(Correlate c: this.model.getCorrelate(p1)) {
    		txtResult.appendText(c.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	
    	String cal = txtCalorie.getText();
    	double k = 0.0;
    	try {
    		k = Double.parseDouble(cal);
    	} catch(NumberFormatException nfe) {
    		txtResult.appendText("Devi inserire un numero!!");
    		return;
    	}
    	this.model.creaGrafo(k);
    	txtResult.appendText("GRAFO CREATO! \n");
    	txtResult.appendText("# VERTICI: " + this.model.nVertici()+"\n");
    	txtResult.appendText("# ARCHI: " + this.model.nArchi());
    	boxPorzioni.getItems().addAll(this.model.getVertici());
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
