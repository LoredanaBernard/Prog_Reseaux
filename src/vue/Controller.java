package vue;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller implements Initializable {
	
	@FXML 
	private TextField txtServ;
	@FXML
	private Button btnSelection;
	@FXML
	private Button btnOk;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
	}
	
	@FXML
	private void handleButtonOk(ActionEvent event) throws IOException {
		Stage stage = null;
		Parent root = null;
		 if(event.getSource()==btnOk){        
		        stage=(Stage) btnOk.getScene().getWindow();
		        //load up OTHER FXML document
		  root = FXMLLoader.load(getClass().getResource("Synchro2.fxml"));
		 }
		 Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();
		    
	}
	
}
