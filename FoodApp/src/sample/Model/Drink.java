package sample.Model;

import javafx.scene.image.ImageView;

public class Drink extends Product{
	private ImageView image;
	
	public Drink(String name,double price,ImageView image) {
		super("Drink", name, price);
		this.image=image;
	}
	public ImageView getimage() {
		return image;
	}
}
