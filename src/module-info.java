module betterStash {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires java.sql;
	
	opens application to javafx.graphics;
	opens application.controller to javafx.fxml;
}
