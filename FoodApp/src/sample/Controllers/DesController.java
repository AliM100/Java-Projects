package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class DesController {
	@FXML
	private HBox loc,mail,call;
	@FXML
	public void tipLoc() {
		Tooltip tt = new Tooltip();
		tt.setText("Sayed Hadi Highway Near Bin Adnan");
		Tooltip.install(loc, tt);
	}
	@FXML 
	public void tipCall() {
		Tooltip tt = new Tooltip();
		tt.setText("78/888888");
		Tooltip.install(call, tt);
	}
	@FXML 
	public void tipMail() {
		Tooltip tt = new Tooltip();
		tt.setText("AbuAli.Way@outlook.com");
		Tooltip.install(mail, tt);
	}
}
