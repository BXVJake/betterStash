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


public class CTR_comingSoon
{
	 @FXML
	 private Button buttonBack;

	 private Stage stage;
	 private Scene scene;
	 private Parent root;
	 
	 @FXML
	 void buttonBack_pressed(ActionEvent e)
	 {
		 try
			{
				Parent root = FXMLLoader.load((getClass().getResource("/SCR_home.fxml")));
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
