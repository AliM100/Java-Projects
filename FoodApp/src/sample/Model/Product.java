package sample.Model;

public class Product {
	private String type,name;
	private double price;
	public Product(String type,String name,double price) {
		this.type=type;
		this.name=name;
		this.price=price;
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
}
