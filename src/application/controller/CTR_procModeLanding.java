package application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CTR_procModeLanding implements Initializable
{

    @FXML
    private Button BUT_back;

    @FXML
    private Button BUT_help;

    @FXML
    private Button BUT_openExpl1;

    @FXML
    private Button BUT_openExpl2;

    @FXML
    private Button BUT_proceed;

    @FXML
    private Button BUT_validate;
    
    @FXML
    private Button BUT_openXpl3;
    
    @FXML
    private TextField FLD_direcPath;

    @FXML
    private TextField FLD_schemaPath;
    
    @FXML
    private TextField FLD_instPath;

    @FXML
    private Tooltip TTP_proceed;
    
    @FXML
    private CheckBox CHK_saveSchema;
    
    @FXML
    private CheckBox CHK_saveDirec;
    
    @FXML
    private CheckBox CHK_mediaType;
    
    @FXML 
    private CheckBox CHK_saveInst;
    
    @FXML
    private ChoiceBox<String> CH_mediaType;
    
    private String[] mediaType = {"Images", "Scenes"};
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    	FLD_schemaPath.setText(AppData.getSetting("schemaPath"));
		FLD_direcPath.setText(AppData.getSetting("direcPath"));
		FLD_instPath.setText(AppData.getSetting("instLoc"));
		CH_mediaType.setValue(AppData.getSetting("mediaType"));

		if (!FLD_schemaPath.getText().isEmpty())
		{
			CHK_saveSchema.setSelected(true);
		}
		
		if (!FLD_direcPath.getText().isEmpty())
		{
			CHK_saveDirec.setSelected(true);
		}
		
		CH_mediaType.getItems().addAll(mediaType);
    }
    
    @FXML
    void ACT_back(ActionEvent e) 
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
    	chooser.setTitle("Open Directory to Process");
    	
    	File selected = chooser.showDialog(null);
    	FLD_direcPath.setText(selected.getAbsolutePath());
    }
    
    @FXML
    void ACT_openExpl3(ActionEvent e) 
    {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("Open .stash Installation Directory");
    	
    	File selected = chooser.showDialog(null);
    	FLD_instPath.setText(selected.getAbsolutePath());
    }

    @FXML
    void ACT_proceed(ActionEvent e) throws FileNotFoundException
    {
    	try
		{
			Parent root = FXMLLoader.load((getClass().getResource("/SCR_procScan.fxml")));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
			if (CHK_saveSchema.isSelected())
			{
				AppData.saveSetting("schemaPath", FLD_schemaPath.getText());
			}
			
			if (CHK_saveDirec.isSelected())
			{
				AppData.saveSetting("direcPath", FLD_direcPath.getText());
			}
			
			if (CHK_mediaType.isSelected())
			{
				AppData.saveSetting("mediaType", CH_mediaType.getValue());
			}
			
			if (CHK_saveInst.isSelected())
			{
				AppData.saveSetting("instLoc", FLD_instPath.getText());
			}
		} 
    	catch (IOException e1)
		{
			e1.printStackTrace();
		}
    	
    	System.out.println(processingMode.findTypes());
    }

    @FXML
    void ACT_validate(ActionEvent e) 
    {
    	String rawPath = FLD_schemaPath.getText().trim();
    	processingMode.schema = new File(rawPath);
    	processingMode.directory = new File(FLD_direcPath.getText().trim());
    	processingMode.instLoc = new File(FLD_instPath.getText().trim());
    	
    	boolean validMediaType;
    	
    	switch(CH_mediaType.getValue())
    	{
    		case "Images":
    			processingMode.mediaType = true;
    			CH_mediaType.setStyle("-fx-border-color: green;");
    			validMediaType = true;
    			break;
    		case "Scenes":
    			processingMode.mediaType = false;
    			CH_mediaType.setStyle("-fx-border-color: green;");
    			validMediaType = true;
    			break;
    		case null:
    			CH_mediaType.setStyle("-fx-border-color: red;");
    			validMediaType = false;
    			break;
    		default:
    			CH_mediaType.setStyle("-fx-border-color: red;");
    			validMediaType = false;
    			break;
    	}
    	
    	boolean validSchema = processingMode.testSchema();
    	boolean validDirectory = processingMode.testDirectory();
    	boolean validInst = processingMode.testInst();
    	
    	if (validSchema)
    	{
    		FLD_schemaPath.setStyle("-fx-border-color: green;");
    	}
    	else
    	{
    		FLD_schemaPath.setStyle("-fx-border-color: red;");
    	}
    	
    	if(validDirectory)
    	{
    		FLD_direcPath.setStyle("-fx-border-color: green;");
    	}
    	else
    	{
    		FLD_direcPath.setStyle("-fx-border-color: red;");
    	}
    	
    	if(validInst)
    	{
    		FLD_instPath.setStyle("-fx-border-color: green;");
    	}
    	else
    	{
    		FLD_instPath.setStyle("-fx-border-color: red;");
    	}
    	
    	if (validSchema && validDirectory && validMediaType && validInst)
    	{
    		BUT_proceed.setDisable(!(validSchema && validDirectory&& validMediaType && validInst));
    	}
    }
}
