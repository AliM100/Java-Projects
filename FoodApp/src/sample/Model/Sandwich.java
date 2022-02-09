package sample.Model;

import javafx.scene.image.ImageView;

public class Sandwich extends Product{
	private ImageView image;
	
	public Sandwich(String name,double price,ImageView image) {
		super("Sandwich", name, price);
		this.image=image;
	}
	public ImageView getimage() {
		return image;
	}
}
