package application.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.run;
import application.functional.processingMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CTR_procScan implements Initializable
{
	@FXML
	private Button BUT_back;

    @FXML
    private Button BUT_scan;

    @FXML
    private Label LBL_direc;

    @FXML
    private ScrollPane SCRL_toggle;
    
    @FXML
    private VBox VBOX_toggle;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	LBL_direc.setText(processingMode.directory.toString());
    	
    	try
		{
			updateTypeList();
		} 
    	catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
    }
    
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
    
    @FXML
    private void ACT_scan(ActionEvent e)
    {
    	processingMode.runScan();
    }
    
    private void updateTypeList() throws FileNotFoundException
    {
    	ArrayList<String> labels = processingMode.findTypes();
		
		for (int iter = 0; iter < labels.size(); iter++)
		{
			ToggleButton button = new ToggleButton(labels.get(iter));
			VBOX_toggle.getChildren().add(button);
			
			if (iter < labels.size() - 1) 
			{
	            Separator separator = new Separator();
	            separator.setPadding(new Insets(5, 0, 5, 0));
	            VBOX_toggle.getChildren().add(separator);
	        }
		}
    }
}
