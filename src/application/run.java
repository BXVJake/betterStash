package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class run extends Application 
{
	@Override
	public void start(Stage stage) 
	{
		try
		{
			Parent root = FXMLLoader.load(getClass().getResource("/SCR_home.fxml"));
			Scene scene1 = new Scene(root);
			stage.setScene(scene1);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
