package sample.Model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class FXMLFactory {
	public static Node get(String k) throws IOException {
		if(k.equals("StatHbox")) {
			return FXMLLoader.load(FXMLFactory.class.getResource("../Views/Statistics.fxml"));
		}
		else {
			if(k.equals("AdminHbox")) {
				return FXMLLoader.load(FXMLFactory.class.getResource("../Views/Admin.fxml"));
			}
			else {
				if(k.equals("SandwichHbox")) {
					return FXMLLoader.load(FXMLFactory.class.getResource("../Views/Sandwich.fxml"));
				}
				else {
					if(k.equals("DrinksHbox")) {
						return FXMLLoader.load(FXMLFactory.class.getResource("../Views/Drinks.fxml"));
					}
					else {
						if(k.equals("MyCartHbox")) {
							return FXMLLoader.load(FXMLFactory.class.getResource("../Views/Cart.fxml"));
						}
						else {
							if(k.equals("logo")) {
								return FXMLLoader.load(FXMLFactory.class.getResource("../Views/Description.fxml"));
							}
							else {
								if(k.equals("products")) {
									return FXMLLoader.load(FXMLFactory.class.getResource("../Views/gridItems.fxml"));
								}
								else {
									if(k.equals("login")) {
										return FXMLLoader.load(FXMLFactory.class.getResource("../Views/login.fxml"));
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}
