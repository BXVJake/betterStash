package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.run;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CTR_procScan
{
	@FXML
	private Button BUT_back;

    @FXML
    private Button BUT_scan;

    @FXML
    private Label LBL_direc;

    @FXML
    private VBox VBOX_toggle;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private void ACT_back(ActionEvent e)
    {
    	try
		{
			Parent root = FXMLLoader.load((getClass().getResource(run.prevScreen)));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} 
    	catch (IOException e1)
		{
			e1.printStackTrace();
		}
    }
}
