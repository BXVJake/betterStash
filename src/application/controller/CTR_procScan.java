package application.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.run;
import application.functional.Exec;
import application.functional.Filetype;
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
import javafx.scene.control.ChoiceBox;
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
    private Button BUT_go;

    @FXML
    private Label LBL_direc;

    @FXML
    private ScrollPane SCRL_toggle;
    
    @FXML
    private VBox VBOX_toggle;
    
    @FXML
    private ChoiceBox<String> CH_module;
    
    private String[] modules = {"Rule34.xxx"};
    
    private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();

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
    	
    	CH_module.getItems().addAll(modules);
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
    		toggleButtons.add(button);
    		VBOX_toggle.getChildren().add(button);

    		if (iter < labels.size() - 1) 
    		{
    			Separator separator = new Separator();
    			separator.setPadding(new Insets(5, 0, 5, 0));
    			VBOX_toggle.getChildren().add(separator);
    		}
    	}
    }
    
    @FXML
    private void ACT_go()
    {
    	Exec.setList();
    	
    	for (ToggleButton button : toggleButtons)
    	{
    		if (button.isSelected())
    		{
    			String label = button.getText();
    			String ext = label.substring(1, label.indexOf("(")).toUpperCase();
    			
    			switch (ext)
    			{
    				case "M4V": Exec.typeToggle.add(Filetype.M4V); break;
    				case "MP4": Exec.typeToggle.add(Filetype.MP4); break;
    				case "MOV": Exec.typeToggle.add(Filetype.MOV); break;
    				case "WMV": Exec.typeToggle.add(Filetype.WMV); break;
    				case "AVI": Exec.typeToggle.add(Filetype.AVI); break;
    				case "MPG": Exec.typeToggle.add(Filetype.MPG); break;
    				case "MPEG": Exec.typeToggle.add(Filetype.MPEG); break;
    				case "RMVB": Exec.typeToggle.add(Filetype.RMVB); break;
    				case "RM": Exec.typeToggle.add(Filetype.RM); break;
    				case "FLV": Exec.typeToggle.add(Filetype.FLV); break;
    				case "ASF": Exec.typeToggle.add(Filetype.ASF); break;
    				case "MKV": Exec.typeToggle.add(Filetype.MKV); break;
    				case "WEBM": Exec.typeToggle.add(Filetype.WEBM); break;
    				case "F4V": Exec.typeToggle.add(Filetype.F4V); break;
    				case "PNG": Exec.typeToggle.add(Filetype.PNG); break;
    				case "JPG": Exec.typeToggle.add(Filetype.JPG); break;
    				case "JPEG": Exec.typeToggle.add(Filetype.JPEG); break;
    				case "GIF": Exec.typeToggle.add(Filetype.GIF); break;
    				case "WEBP": Exec.typeToggle.add(Filetype.WEBP); break;
    				default: Exec.typeToggle.add(Filetype.UNKN); break;
    			}
    		}
    	}
    }
}
