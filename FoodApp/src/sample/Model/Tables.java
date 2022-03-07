package sample.Model;

public class Tables {
private int tid;
private int capacity;
private String isreserved;
private String time;
private int cid,tel;


public Tables(int tid,int cid,int capacity, String isreserved, String time,int tel) {
	super();
	this.tid = tid;
	this.cid=cid;
	this.capacity = capacity;
	this.isreserved = isreserved;
	this.time = time;
	this.tel=tel;
}

public int getTel() {
	return tel;
}

public void setTel(int tel) {
	this.tel = tel;
}

public int getCid() {
	return cid;
}

public void setCid(int cid) {
	this.cid = cid;
}

public int getTid() {
	return tid;
}
public void setTid(int tid) {
	this.tid = tid;
}
public int getCapacity() {
	return capacity;
}
public void setCapacity(int capacity) {
	this.capacity = capacity;
}
public String getIsreserved() {
	return isreserved;
}
public void setIsreserved(String isreserved) {
	this.isreserved = isreserved;
}

public String getTime() {
	return time;
}

public void setTime(String time) {
	this.time = time;
}



}
