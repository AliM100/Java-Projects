package sample.Model;
import sample.Model.DB;

public class modifypricesModel {
	
	float oldp,newp;
	public modifypricesModel(float o,float n) {
		this.oldp=o;
		this.newp=n;
	}
	
	public void modify() {
		DB.updateprices(oldp,newp);
	}
}
