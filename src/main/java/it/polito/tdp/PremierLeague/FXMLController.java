
/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGiocatoreMigliore"
    private Button btnGiocatoreMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMatch"
    private ComboBox<Match> cmbMatch; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Simulator sim;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Match m= this.cmbMatch.getValue();
    	if(m==null) {
    		this.txtResult.setText("ATTENTO, SELEZIONA IL MATCH!");
    		return;
    	}
    	this.model.creaGrafo(m);
    	this.txtResult.appendText("GRAFO CREATO!" +"\n");
    	this.txtResult.appendText("#VERTICI: "+this.model.getVertici()+"\n");
    	this.txtResult.appendText("#ARCHI: "+this.model.getArchi()+"\n");
    }

    @FXML
    void doGiocatoreMigliore(ActionEvent event) {    	
    	this.txtResult.clear();
    	if(model.getGrafo()==null) {
    		this.txtResult.appendText("CREA PRIMA IL GRAFO!");
    		return;
    	}
    	this.txtResult.appendText(this.model.calcolaGiocatoreMigliore());
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	if(this.model.getGrafo() == null) {
    		this.txtResult.setText("Crea prima il grafo!");
    		return;
    	}
    
	    	String Ns = this.txtN.getText();
	    	Match m = this.cmbMatch.getValue();
    	
    	int N;
    	try {
    		N = Integer.parseInt(Ns);		
    	}
    	catch(NumberFormatException nfe) {
    		this.txtResult.setText("Inserire un numero");
    		return;
    	}
    	this.sim.init(N, m);
		this.sim.run();
    	this.txtResult.appendText("\n\n"+m.getTeamHomeNAME()+" "+this.sim.getGoalCasa()+"-"+this.sim.getGoalOspite()+" "+m.getTeamAwayNAME());
		this.txtResult.appendText("\nEspulsioni "+m.getTeamHomeNAME()+": "+this.sim.getEspulsiC());
		this.txtResult.appendText("\nEspulsioni "+m.getTeamAwayNAME()+": "+this.sim.getEspulsiO());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGiocatoreMigliore != null : "fx:id=\"btnGiocatoreMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMatch != null : "fx:id=\"cmbMatch\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbMatch.getItems().addAll(this.model.listAllMatches());
    	this.sim = new Simulator(model);
    }
}
