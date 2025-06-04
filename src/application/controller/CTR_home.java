package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CTR_home
{
    @FXML
    private Button BUT_config;

    @FXML
    private Button BUT_intake;

    @FXML
    private Button BUT_processing;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void ACT_processing(ActionEvent e)
    {
    	try
		{
			Parent root = FXMLLoader.load((getClass().getResource("/SCR_procModeLanding.fxml")));
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
    
    public void ACT_config(ActionEvent e)
    {
    	try
		{
			Parent root = FXMLLoader.load((getClass().getResource("/SCR_comingSoon.fxml")));
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
    
    public void ACT_intake(ActionEvent e)
    {
    	try
		{
			Parent root = FXMLLoader.load((getClass().getResource("/SCR_comingSoon.fxml")));
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
