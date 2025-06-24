package application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import application.run;
import application.functional.AppData;
import application.functional.processingMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class CTR_config implements Initializable
{
	@FXML
    private Button BUT_home;
	
	@FXML
    private Button BUT_help;

    @FXML
    private Button BUT_openExpl1;

    @FXML
    private Button BUT_openExpl2;
    
    @FXML
    private TextField FLD_schemaPath;
    
    @FXML
    private TextField FLD_instPath;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	FLD_schemaPath.setText(AppData.getSetting("schemaPath"));
    	FLD_instPath.setText(AppData.getSetting("instLoc"));
    }
    
    void ACT_back(ActionEvent e) 
    {
    	try
		{
			Parent root = FXMLLoader.load((getClass().getResource("/rsc/SCR_home.fxml")));
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
    void ACT_help(ActionEvent e) throws IOException, URISyntaxException 
    {
    	Desktop.getDesktop().browse(new URI("https://discourse.stashapp.cc/t/how-to-locate-your-stash-database-schema/1895"));
    }
    
    @FXML
    void ACT_openExpl1(ActionEvent e) 
    {
    	FileChooser chooser = new FileChooser();
    	chooser.setTitle("Open Database Schema");
    	chooser.getExtensionFilters().add(new ExtensionFilter("SQLITE Files", "*.sqlite"));
    	
    	File selected = chooser.showOpenDialog(null);
    	processingMode.schema = selected;
    	
    	FLD_schemaPath.setText(selected.getAbsolutePath());
    }
    
    @FXML
    void ACT_openExpl2(ActionEvent e) 
    {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("Open .stash Installation Directory");
    	
    	File selected = chooser.showDialog(null);
    	FLD_instPath.setText(selected.getAbsolutePath());
    }
    
    @FXML
    void ACT_save(ActionEvent e)
    {
    	String rawPath = FLD_schemaPath.getText().trim();
    	processingMode.schema = new File(rawPath);
    	processingMode.instLoc = new File(FLD_instPath.getText().trim());
    	
    	boolean validSchema = processingMode.testSchema();
    	boolean validInst = processingMode.testInst();
    	
    	if (validSchema)
    	{
    		FLD_schemaPath.setStyle("-fx-border-color: green;");
    	}
    	else
    	{
    		FLD_schemaPath.setStyle("-fx-border-color: red;");
    	}
    	
    	if(validInst)
    	{
    		FLD_instPath.setStyle("-fx-border-color: green;");
    	}
    	else
    	{
    		FLD_instPath.setStyle("-fx-border-color: red;");
    	}
    }

}
