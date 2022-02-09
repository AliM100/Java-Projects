package sample.Model;

public class Tables {
private int tabid;
private int capacity;
private int isreserved;
private String reservtime;



public Tables(int tabid, int capacity, int isreserved, String reservetime) {
	super();
	this.tabid = tabid;
	this.capacity = capacity;
	this.isreserved = isreserved;
	this.reservtime = reservetime;
}

public int getTabid() {
	return tabid;
}
public void setTabid(int tabid) {
	this.tabid = tabid;
}
public int getCapacity() {
	return capacity;
}
public void setCapacity(int capacity) {
	this.capacity = capacity;
}
public int getIsreserved() {
	return isreserved;
}
public void setIsreserved(int isreserved) {
	this.isreserved = isreserved;
}
public String getReservetime() {
	return reservtime;
}
public void setReservetime(String reservetime) {
	this.reservtime = reservetime;
}


}
