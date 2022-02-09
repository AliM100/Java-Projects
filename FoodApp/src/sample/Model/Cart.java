package sample.Model;

public class Cart {
	private String type,name;
	private double price;
	private int quantity;
	public Cart(String name,String type,double price,int quantity) {
		this.type=type;
		this.name=name;
		this.price=price;
		this.quantity=quantity;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
}
