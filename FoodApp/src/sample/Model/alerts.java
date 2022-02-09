package sample.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class alerts {
	public static void Error(String text) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(text);
		alert.setHeaderText(null);
		ImageView icon = new ImageView(new Image(alerts.class.getResourceAsStream("../Resources/error.png")));
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(alerts.class.getResourceAsStream("../Resources/error.png")));
		stage.showAndWait();
	}
	public static void Noti(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Notification");
		alert.setContentText(text);
		alert.setHeaderText(null);
		ImageView icon = new ImageView(new Image(alerts.class.getResourceAsStream("../Resources/noti.png")));
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(alerts.class.getResourceAsStream("../Resources/noti.png")));
		stage.showAndWait();
	}
}
