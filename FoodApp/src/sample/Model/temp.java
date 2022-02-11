package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class temp {
	static ObservableList<Tables> editoblist = FXCollections.observableArrayList();
	static ObservableList<Tables> detailoblist = FXCollections.observableArrayList();
	
	public static final void setedit(ObservableList<Tables> t) {
		temp.editoblist=t;
	}
	public static final ObservableList<Tables> getedit() {
		return editoblist;
	}
	
	public static final void setdetail(ObservableList<Tables> t) {
		temp.detailoblist=t;
	}
	public static final ObservableList<Tables> getdetail() {
		return detailoblist;
	}
}
