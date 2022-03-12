package sample.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

public class DB {
	public static DB d;
	public static Connection c;
	
	private DB () {
        c = null;
    }
	
	public static DB getInstance() {
        if (d == null) 
        	d = new DB();
        return d;
    }
	
	public boolean isConnected() {
		return c!=null;
	}
	
	//connect to ms sql
	public Connection connect(String sr, String dn, String us, String ps) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection("jdbc:sqlserver://"+sr+";database="+dn,us,ps);
        } catch (Exception e) {
            e.printStackTrace();
            c = null;
        }
        return c;
    }
	//establish connection
	public static int setupCon() {
		int k=1;
		DB db = DB.getInstance();
        try {
            if (!db.isConnected()) 
            	db.connect("ALI-HASSAN","foodDB","sa","ali123ali");
            if (!db.isConnected()) throw new Exception();
        } catch (Exception e) {
        	k=0;
            e.printStackTrace();
            alerts.Error("Could not connect to Database!");
        }
        return k;
    }
	
	
	//get all managers
	public static ArrayList<Manager> getManagers(){
		ArrayList<Manager> al=new ArrayList<Manager>();
		try {
            if (!d.isConnected()) throw new Exception();
            String st = "SELECT Mname,Mpass,Email FROM Manager where ismaster=0 ;";
            PreparedStatement p = c.prepareStatement(st);
            ResultSet r = p.executeQuery();
            while(r.next()) {
                al.add(new Manager(r.getString("Mname").toLowerCase(),r.getString("Mpass"),r.getString("Email")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
	}
	//create manager
	public static void createManager(Manager a) throws Exception {
		if (!d.isConnected()) throw new Exception();
        String st = "exec createMan @name="+a.getUsername().toUpperCase()+", @pass="+a.getPass()+", @email='"+a.getEmail()+"', @isM=0";
        PreparedStatement p = c.prepareStatement(st);
        if (p.execute()) throw new Exception();
	}
	//remove manager
	public static void removeManager(String name) throws Exception {
		if (!d.isConnected()) throw new Exception();
	    String st = "delete from Manager where mname=(?);";
	    PreparedStatement p = c.prepareStatement(st);
	    p.setString(1,name.toUpperCase());
	    if (p.execute()) throw new Exception();
	}
	//create Client
	public static void createClient(Customer cus) throws Exception {
		if (!d.isConnected()) throw new Exception();
        String st = "exec createCus @name="+cus.getUsername()+", @pass="+cus.getPass()+", @email='"+cus.getEmail()+"'";
        PreparedStatement p = c.prepareStatement(st);
        if (p.execute()) throw new Exception();
	}
	

//	public static void createClient(Customer cus) throws Exception {
//		if (!d.isConnected()) throw new Exception();
//        String st = "Insert into CLIENT values('"+cus.getUsername()+"','"+1+"','"+cus.getPass()+"','"+cus.getEmail()+"')";
//        PreparedStatement p = c.prepareStatement(st);
//        if (p.execute()) throw new Exception();
//	}
	
	public static void addTable(int capacity) throws Exception {
		
		if (!d.isConnected()) throw new Exception();
        String st = "Insert into TAB values(0,'"+capacity+"',null,null,null,null)";
        PreparedStatement p = c.prepareStatement(st);
        if (p.execute()) throw new Exception();
	}
	//get Cid
	public static int getCid(String name) throws Exception{
		int cid = 0;
		if (!d.isConnected()) throw new Exception();
        String st = "select dbo.getcid(?)";
        PreparedStatement p = c.prepareStatement(st);
        p.setString(1, name);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            cid= r.getInt(1);
        }
        return cid;
	}
	//add product
	public static void createP(String name,String type,double price,ImageView image) throws Exception {
		if (!d.isConnected()) throw new Exception();
        String st = "INSERT INTO Product (Ptype,Pname,Pprice,Pimage) VALUES (?,?,?,?);";
        PreparedStatement p = c.prepareStatement(st);
        p.setString(1,type);
        p.setString(2, name.toUpperCase());
        p.setFloat(3,(float) price);
        p.setString(4, image.getImage().getUrl());
        if (p.execute()) throw new Exception();
	}
	//remove product
	public static void removeP(int pid) throws Exception {
		if (!d.isConnected()) throw new Exception();
        String st = "exec deleteP @productId="+pid;
        PreparedStatement p = c.prepareStatement(st);
        if (p.execute()) throw new Exception();
	}
	//get Pid
	public static int getPid(String name) throws Exception{
		int pid = 0;
		if (!d.isConnected()) throw new Exception();
        String st = "select dbo.getpid(?)";
        PreparedStatement p = c.prepareStatement(st);
        p.setString(1, name);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            pid= r.getInt(1);
        }
        return pid;
	}
	//create order
	public static void createO(int cid,int did) throws Exception{
		if (!d.isConnected()) throw new Exception();
        String st = "INSERT INTO Orders (Cid,Did,Ototalprice) VALUES (?,?,?);";
        PreparedStatement p = c.prepareStatement(st);
        p.setInt(1,cid);
        p.setInt(2,did);
        p.setFloat(3,0);
        if (p.execute()) throw new Exception();
	}
	//create order deliveryinfo
	public static int createD() throws Exception{
		int did = 0;
		if (!d.isConnected()) throw new Exception();
		String st = "INSERT INTO Deliveryinfo (telephonenb,location) output inserted.did VALUES (?,?);";
		PreparedStatement p = c.prepareStatement(st);
		p.setInt(1,0);
		p.setString(2, null);
		ResultSet r = p.executeQuery();
		while(r.next()) {
            did= r.getInt(1);
        }
		return did;
	}
	
	public static ObservableList<Tables> getTables() throws Exception {
		String q="Select * from TAB";
		ObservableList<Tables> ol= FXCollections.observableArrayList();
		Tables T;
		if (!d.isConnected()) throw new Exception();
		 PreparedStatement p = c.prepareStatement(q);
	        ResultSet r = p.executeQuery();
		while(r.next()) {
			int tid=r.getInt("TID");
			int cap=r.getInt("TCAP");
			int cid=r.getInt("CID");
			int isreserved=r.getInt("TISRESERVED");
			int tel=r.getInt("TTEL");
			String time=r.getString("TTIME");
			String name=r.getString("TCNAME");
			String re;
			if(isreserved==1)
				re="Yes";
			else re="No";
			T=new Tables(tid,cid,cap,re,time,tel,name);
			ol.add(T);
		}
		return ol;
	}
	public static Tables getTable(int ttid) throws Exception {
		String q="Select * from TAB where TID='"+ttid+"' ";
		
		Tables T = null;
		if (!d.isConnected()) throw new Exception();
		 PreparedStatement p = c.prepareStatement(q);
	        ResultSet r = p.executeQuery();
		while(r.next()) {
			int tid=r.getInt("TID");
			int cap=r.getInt("TCAP");
			int cid=r.getInt("CID");
			int isreserved=r.getInt("TISRESERVED");
			int tel=r.getInt("TTEL");
			String time=r.getString("TTIME");
			
			String name=r.getString("TCNAME");
			String re;
			if(isreserved==1)
				re="Yes";
			else re="No";
			T=new Tables(tid,cid,cap,re,time,tel,name);
		
		}
		return T;
	}
	public static  int getcustid(String name) throws Exception{
		int cid = 0;
		if (!d.isConnected()) throw new Exception();
	    String st = "select CID from CLIENT where CNAME='"+name+"'  ";
	    PreparedStatement p = c.prepareStatement(st);
        ResultSet r = p.executeQuery();
        while(r.next()) {
        	cid=r.getInt("CID");	
        }
        System.out.println("cid:"+cid);
	    return cid;
	}
	public static  int gettabtel(int tid) throws Exception{
		int num = 0;
		if (!d.isConnected()) throw new Exception();
	    String st = "select TTEL from TAB where TID='"+tid+"'  ";
	    PreparedStatement p = c.prepareStatement(st);
        ResultSet r = p.executeQuery();
        while(r.next()) {
        	num=r.getInt("TTEL");
        	
        }
	    return num;
	}
	public  static String  getcustnametel(int id) throws Exception{
		String name=null;
		
		if (!d.isConnected()) throw new Exception();
	    String st = "select CNAME from CLIENT where CID='"+id+"' ";
	    PreparedStatement p = c.prepareStatement(st);
        ResultSet r = p.executeQuery();
        while(r.next()) {
        	name=r.getString("CNAME");
        
        }
	    return name;
	}
	public static void updatetable(Tables T) throws Exception{
		if (!d.isConnected()) throw new Exception();
		int res;
		if(T.getIsreserved().equals("Yes"))
			res=1;
		else res=0;
	    String st = "update  TAB set CID='"+T.getCid()+"', TISRESERVED='"+res+"',TTIME='"+T.getTime()+"',TTEL='"+T.getTel()+"',TCNAME='"+T.getName()+"' where TID='"+T.getTid()+"'";
	    PreparedStatement p = c.prepareStatement(st);
        if (p.execute()) throw new Exception();
	 
	}
	
	public  static int getcusid(int tid) throws Exception{
		int cid = 0;
		if (!d.isConnected()) throw new Exception();
	    String st = "select CID from TAB where TID='"+tid+"'  ";
	    PreparedStatement p = c.prepareStatement(st);
        ResultSet r = p.executeQuery();
        while(r.next()) {
        	cid=r.getInt("CID");
        }
	    return cid;
	}
	//get did
	public static int getDid(int cid) throws Exception{
		int did = 0;
		if (!d.isConnected()) throw new Exception();
	    String st = "select dbo.getdid(?)";
	    PreparedStatement p = c.prepareStatement(st);
	    p.setInt(1,cid);
	    ResultSet r = p.executeQuery();
	    while(r.next()) {
	        did= r.getInt(1);
	    }
	    return did;
	}
	//get oid
	public static int getOid(int cid) throws Exception{
		int oid = 0;
		if (!d.isConnected()) throw new Exception();
        String st = "select dbo.getoid(?)";
        PreparedStatement p = c.prepareStatement(st);
        p.setInt(1,cid);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            oid= r.getInt(1);
        }
        return oid;
	}
	//add Product to orderitems
	public static void addPC(int oid,int pid,int q) throws Exception {
		if (!d.isConnected()) throw new Exception();
        String st = "INSERT INTO OrderItems (pid,oid,QUANTITY) VALUES (?,?,?);";
        PreparedStatement p = c.prepareStatement(st);
        p.setInt(1,pid);
        p.setInt(2 ,oid);
        p.setInt(3, q);
        if (p.execute()) throw new Exception();
	}
	//remove Product from orderitems
	public static void removePC(int oid,int pid) throws Exception {
		if (!d.isConnected()) throw new Exception();
	       String st = "exec deletePfromC @prid="+pid+",@orid="+oid;
	       PreparedStatement p = c.prepareStatement(st);
	      if (p.execute()) throw new Exception();
	}
	//get all sandwichs
	public static ArrayList<Sandwich> getS(){
		ArrayList<Sandwich> sl=new ArrayList<Sandwich>();
		try {
	         if (!d.isConnected()) throw new Exception();
	         String st = "SELECT * FROM Sandwich;";
	         PreparedStatement p = c.prepareStatement(st);
	         ResultSet r = p.executeQuery();
	         while(r.next()) {
	            sl.add(new Sandwich(r.getString(1).toLowerCase(),r.getFloat(2),new ImageView(r.getString(3))));
	         }
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
	     return sl;
	}
	//get all drinks
	public static ArrayList<Drink> getD(){
		ArrayList<Drink> dl=new ArrayList<Drink>();
		try {
	        if (!d.isConnected()) throw new Exception();
	        String st = "SELECT * FROM Drink;";
	        PreparedStatement p = c.prepareStatement(st);
	        ResultSet r = p.executeQuery();
	        while(r.next())  {
	            dl.add(new Drink(r.getString(1).toLowerCase(),r.getFloat(2),new ImageView(r.getString(3))));
	        }
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
	     return dl;
	}
		//get all order items
		public static ArrayList<Cart> getOI(int oid){
			ArrayList<Cart> cl=new ArrayList<Cart>();
			try {
	            if (!d.isConnected()) throw new Exception();
	            String st = "SELECT pid,quantity FROM OrderItems where oid=(?);";
	            PreparedStatement p = c.prepareStatement(st);
	            p.setInt(1, oid);
	            ResultSet r = p.executeQuery();
	            while(r.next()) {
	            	int pid=r.getInt("Pid");
	            	int q=r.getInt("quantity");
	            	String str = "SELECT pname,ptype,pprice FROM Product where pid=(?);";
		            PreparedStatement ps = c.prepareStatement(str);
		            ps.setInt(1, pid);
		            ResultSet rs = ps.executeQuery();
		            while(rs.next())  {
		            	cl.add(new Cart(rs.getString("pname").toLowerCase(),rs.getString("ptype"),rs.getFloat("Pprice"),q));
		            }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return cl;
		}
		//if manager
		public static boolean login(String un,String pass,String type) throws Exception {
			if (!d.isConnected()) throw new Exception();
			String st=null;
			if(type.equals("Manager"))
				st = "EXEC SelectManager @name ='"+un.toUpperCase()+"', @pass='"+pass+"'";
			else
				st = "EXEC SelectClient @name ='"+un.toUpperCase()+"', @pass='"+pass+"'";
	        PreparedStatement p = c.prepareStatement(st);
	        ResultSet r = p.executeQuery();
	        if(r.next()==true)
	        	return true;
	        return false;
		}
		//if username exist
		public static boolean checkname(String un) throws Exception {
			 if (!d.isConnected()) throw new Exception();
		        String st = "select * from Manager where Mname=(?);";
		        PreparedStatement p = c.prepareStatement(st);
		        p.setString(1,un.toUpperCase());
		        ResultSet r = p.executeQuery();
		        if(r.next()==true)//username in manager
		        	return true;
		        String str = "select * from Client where Cname=(?);";
		        PreparedStatement ps = c.prepareStatement(str);
		        ps.setString(1,un.toUpperCase());
		        ResultSet rs = ps.executeQuery();
		        if(rs.next()==true)//username in client
		        	return true;
		        return false;//no such username
		}
		//if username exist
		public static String[] checkEmail(String em) throws Exception {
			 if (!d.isConnected()) throw new Exception();
			 String s[]= {"0","N"};
		        String st = "select Mid from Manager where email=(?);";
		        PreparedStatement p = c.prepareStatement(st);
		        p.setString(1,em);
		        ResultSet r = p.executeQuery();
		        if(r.next()==true) {
		        	s[0]=""+r.getInt(1);s[1]="M";
		        	return s;
		        }
		        String str = "select Cid from Client where email=(?);";
		        PreparedStatement ps = c.prepareStatement(str);
		        ps.setString(1,em);
		        ResultSet rs = ps.executeQuery();
		        if(rs.next()==true){
		        	s[0]=""+rs.getInt(1);s[1]="C";
		        	return s;
		        }
		        return s;
		}		
		//set order location and telephone
		public static void setLT(int did,String loc,int tel) {
			try {
			       if (!d.isConnected()) throw new Exception();
			       String st="update deliveryinfo set location=(?),telephonenb=(?) where did=(?);";
			       PreparedStatement p = c.prepareStatement(st);
			       p.setString(1, loc);
			       p.setInt(2, tel);
			       p.setInt(3, did);
			       if (p.execute()) throw new Exception();
				}catch (Exception e) {
					e.printStackTrace();
			   }
		}
		//get totalprice
		public static double getOTP(int oid) throws Exception {
			double pr=0;
			 if (!d.isConnected()) throw new Exception();
	            String st = "select Ototalprice from orders where oid=(?)";
	            PreparedStatement p = c.prepareStatement(st);
	            p.setInt(1, oid);
	            ResultSet r = p.executeQuery();
	            while(r.next()) {
	                pr=r.getFloat(1);
	            }
	            return pr;
		}
		//remove all order items
		public static void clearOI(int oid) throws Exception {
			if (!d.isConnected()) throw new Exception();
	        String st = "delete from OrderItems where Oid=(?)";
	        PreparedStatement p = c.prepareStatement(st);
	        p.setInt(1, oid);
	        if (p.execute()) throw new Exception();
	        String str = "update Orders set Ototalprice=0 where oid="+oid;
	        PreparedStatement pr = c.prepareStatement(str);
	        if (pr.execute()) throw new Exception();
		}
		public static String[] getCC(int cid) throws Exception {
			String cc[]=new String[2];
			 if (!d.isConnected()) throw new Exception();
	            String st = "select cctype,ccnb from CREDITCARD where cid=(?)";
	            PreparedStatement p = c.prepareStatement(st);
	            p.setInt(1, cid);
	            ResultSet r = p.executeQuery();
	            while(r.next()) {
	            	cc[0]=r.getString(1);
	            	cc[1]=Integer.toString(r.getInt(2));
	            }
	            return cc;
		}

		public static void setCredit(int cid,String cct,int ccnb) {
			try {
	            if (!d.isConnected()) throw new Exception();
	            String st="update \"CreditCard\" set CCNB=(?) , CCType=(?) where cid=(?);";
	            PreparedStatement p = c.prepareStatement(st);
	            p.setInt(1, ccnb);
	            p.setString(2, cct);
	            p.setInt(3, cid);
	            if (p.execute()) throw new Exception();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		public static void createCredit(int cid,String cct,int ccnb) {
			try {
	            if (!d.isConnected()) throw new Exception();
	            String st = "insert \"CreditCard\" values(?,?,?);";
	            PreparedStatement p = c.prepareStatement(st);
	            p.setInt(1, ccnb);
	            p.setInt(2, cid);
	            p.setString(3, cct);
	            if (p.execute()) throw new Exception();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		public static boolean checkPname(String name) throws Exception {
			if (!d.isConnected()) throw new Exception();
			String st = "select * from Product where Pname=(?);";
			PreparedStatement p = c.prepareStatement(st);
			p.setString(1,name.toUpperCase());
			ResultSet r = p.executeQuery();
			if(r.next()==true)
				return true;
			return false;
		}
		
		public static int getCnbs() throws Exception{
			int nb = 0;
			if (!d.isConnected()) throw new Exception();
	        String st = "select * from Client;";
	        PreparedStatement p = c.prepareStatement(st);
	        ResultSet r = p.executeQuery();
	        while(r.next()) {
	            nb+=1;
	        }
	        return nb;
		}
		
		public static int getPnbs() throws Exception{
			int nb = 0;
			if (!d.isConnected()) throw new Exception();
	        String st = "select * from Product;";
	        PreparedStatement p = c.prepareStatement(st);
	        ResultSet r = p.executeQuery();
	        while(r.next()) {
	            nb+=1;
	        }
	        return nb;
		}
		//check if creditcard nb is used
		public static boolean checkCCnba(int nb,int cid) throws Exception {
			if (!d.isConnected()) throw new Exception();
			String st = "select CCNB from CreditCard where CCNB=(?) and cid!=(?);";
			PreparedStatement p = c.prepareStatement(st);
			p.setInt(1,nb);
			p.setInt(2,cid);
			ResultSet r = p.executeQuery();
			if(r.next()==true)
				return true;
			return false;
			}
		public static void insertO(int cid) throws Exception {
			if (!d.isConnected()) throw new Exception();
			String st = "INSERT INTO Totalorders VALUES (?,?);";
			PreparedStatement p = c.prepareStatement(st);
			p.setInt(1,cid);
			p.setInt(2,1);
			if (p.execute()) throw new Exception();
		}

		public static int getTO() throws Exception {
			int nb = 0;
			if (!d.isConnected()) throw new Exception();
			String st = "select sum(Totalorders) from totalorders;";
			PreparedStatement p = c.prepareStatement(st);
			ResultSet r = p.executeQuery();
			while(r.next()) {
			     nb=r.getInt(1);
			}
			return nb;
		}
		public static void deletecus(String name) throws Exception {
			if (!d.isConnected()) throw new Exception();
			String st = "exec deleteCus @name="+name;
			PreparedStatement p = c.prepareStatement(st);
			if (p.execute()) throw new Exception();
	    }
		public static ArrayList<Customer> getCus(){
			ArrayList<Customer> cl=new ArrayList<Customer>();
			try {
	            if (!d.isConnected()) throw new Exception();
	            String st = "SELECT Cname,Cpass,Email FROM Client;";
	            PreparedStatement p = c.prepareStatement(st);
	            ResultSet r = p.executeQuery();
	            while(r.next())  {
	                cl.add(new Customer(r.getString(1).toLowerCase(),r.getString(2),r.getString(3)));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return cl;
		}
		public static boolean searchC(int pid,int oid) throws Exception {
			if (!d.isConnected()) throw new Exception();
			String st = "select * from orderItems where pid=(?) and oid=(?);";
			PreparedStatement p = c.prepareStatement(st);
			p.setInt(1,pid);
			p.setInt(2,oid);
			ResultSet r = p.executeQuery();
			while(r.next()) {
			     return true;
			}
			return false;
		}
		//check if user have creditcard
		public static boolean checkCC(int cid) throws Exception {
			if (!d.isConnected()) throw new Exception();
			String st = "select * from CreditCard where cid=(?);";
			PreparedStatement p = c.prepareStatement(st);
			p.setInt(1,cid);
			ResultSet r = p.executeQuery();
			if(r.next()==true)
				return true;
			return false;
		}
		public static void updateC(int oid,int pid,int q) throws Exception {
			if(!d.isConnected()) throw new Exception();
			String st = "exec updateCart @prid="+pid+", @orid="+oid+", @quantity="+q;
			PreparedStatement p = c.prepareStatement(st);
			if (p.execute()) throw new Exception();
		}
		
		public static boolean isMaster(String name) throws Exception {
			if(!d.isConnected()) throw new Exception();
			String st = "select * from manager where isMaster=1 and Mname=(?)";
			PreparedStatement p = c.prepareStatement(st);
			p.setString(1,name);
			ResultSet r = p.executeQuery();
			if(r.next()==true)
				return true;
			return false;
		}

		public static String[] getinfo(int did) throws Exception {
			if(!d.isConnected()) throw new Exception();
			String st = "select location,telephoneNb from deliveryinfo where did=(?) and telephoneNb is not null";
			PreparedStatement p = c.prepareStatement(st);
			p.setInt(1,did);
			ResultSet r = p.executeQuery();
			if(r.next()==true) {
				return new String[]{r.getString(1),r.getString(2)};
			}
			return new String[] {null,null};
		}
		
		public static void changeP(String id,String type,String p) {
			try {
	            if (!d.isConnected()) throw new Exception();
	            String st;
	            if(type.equals("M"))
	            	st="exec updateMpass @id="+Integer.parseInt(id)+", @pass="+p;
	            else st="exec updateCpass @id="+Integer.parseInt(id)+", @pass="+p;
	            PreparedStatement p1 = c.prepareStatement(st);
	            if (p1.execute()) throw new Exception();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		//modify prices on $
		public static void updateprices(float oldp, float newp) {
			try {
	            if (!d.isConnected()) throw new Exception();
	            String st = "SELECT PPRICE,PID from PRODUCT;";
	            PreparedStatement p = c.prepareStatement(st);
	            ResultSet r = p.executeQuery();
	            while(r.next()) {
	            	int id=r.getInt("PID");
	            	float price=r.getFloat("PPRICE");
	            	price=price/oldp;
	            	float n=price*newp;
	            	 String st1 = "UPDATE PRODUCT set PPRICE='"+n+"' where PID='"+id+"' ;";
	 	            PreparedStatement p1 = c.prepareStatement(st1);
	 	           if (p1.execute()) throw new Exception();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
		}
		
}
