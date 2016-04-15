package edu.trackme.controller;


import edu.trackme.db.DBConnection;
import edu.trackme.model.User;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
import java.util.ArrayList;

import edu.trackme.util.UserCodeGeneration;



public class MasterController {
	    
	    public DBConnection con;
	    
	    public MasterController(){
	        con = new DBConnection();
	    }
	    
	    
		
	
		    
	    

	    
	    
	    //UPDATE 
	    
	    
	    //accept tracker
	    public void acceptTracker(String trackeeID, String trackerID){
	    	String sQuery = "UPDATE tracker SET status = ? WHERE trackerId = ? AND trackeeId = ?";
	    	PreparedStatement ps;
	    	
	        try{
	       	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, "Active");
	             ps.setInt(2, Integer.parseInt(trackerID));
	             ps.setInt(3, Integer.parseInt(trackeeID));
	             ps.executeUpdate();
	           
	         }catch(SQLException e){
	             e.printStackTrace();
	        
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    }
	    
	    
	    //Update Status
	    public void updateStatusArrived(String sUserID, String status){
	    	String sQuery = "UPDATE user SET status = ? WHERE id = ?";
	    	PreparedStatement ps;
	    	
	        try{
	       	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, status);
	             ps.setInt(2, Integer.parseInt(sUserID));
	             ps.executeUpdate();
	           
	         }catch(SQLException e){
	             e.printStackTrace();
	        
	         }finally{
	        	 con.closeCon();
	         }
	        
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    //Update Track Mode
	    public String updateUserTrackMode(String sUserID, String trackMode){
	    	String sQuery = "UPDATE user SET track_mode = ? WHERE id = ?";
	    	PreparedStatement ps;
	    	String mode = "";
	    	
	        try{
	       	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, trackMode);
	             ps.setInt(2, Integer.parseInt(sUserID));
	             ps.executeUpdate();
	           
	         }catch(SQLException e){
	             e.printStackTrace();
	        
	         }finally{
	        	 con.closeCon();
	         }
	        
	        
	        sQuery = "SELECT track_mode FROM user WHERE id = ?";
	        ResultSet rs;
	        
	        try{
		       	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setInt(1, Integer.parseInt(sUserID));
	             rs = ps.executeQuery();
	             
	             
	             while(rs.next()){
	            	 mode = rs.getString(1);
	             }
	             
	           
	         }catch(SQLException e){
	             e.printStackTrace();
	        
	         }finally{
	        	 con.closeCon();
	         }
	        
	    	
	    	return mode;
	    }
	    
	    
	    //UPDATE track mode and interval (if applicable)
	    
	    //UPDATE User location
	    public void updateUserLocation(String sUserID, String sLatitude, String sLongtitude){
	    	Double dLatitude, dLongtitude;
	    	Integer iUserID;
	    
	    	//parse coordinates
	    	dLatitude = Double.parseDouble(sLatitude);
	    	dLongtitude = Double.parseDouble(sLongtitude);
	    	iUserID = Integer.parseInt(sUserID);
	    	
	    	 PreparedStatement ps;
	         String sQuery = "UPDATE user SET latitude = ?, longtitude = ? WHERE id = ?";
	        

	         
	         try{
	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setDouble(1, dLatitude);
	             ps.setDouble(2, dLongtitude);
	             ps.setDouble(3, iUserID);
	            ps.executeUpdate();
	           
	         }catch(SQLException e){
	             e.printStackTrace();
	             System.out.println("Userd ID: " + sUserID + " failed to update location     latitude and longtitude (" + sLatitude + "," + sLongtitude);
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    	
	    	
	    }
	    
	    
	    
	    //RETRIEVE
	    
	    //GET ALL USERS (convert to gson)
	    
	    
	    //Get User Location
	    public String getUserLocation(String sUserID){
	    	String location = "", sQuery;
	    	ResultSet rs;
	    	PreparedStatement ps;
	    	Integer iTrackeeID;
	    	
	    	iTrackeeID = Integer.parseInt(sUserID);
	    	
	    	
	    	sQuery = "SELECT latitude, longtitude FROM user WHERE id = ?";
	    	   try{
		             ps = con.getConnection().prepareStatement(sQuery);
		             ps.setInt(1, iTrackeeID);
		             rs = ps.executeQuery();
		             
		             while(rs.next()){
		            	location = String.valueOf(rs.getDouble(1)) + " " + String.valueOf(rs.getDouble(2));
		             }
		            
		             
		         }catch(SQLException e){
		             e.printStackTrace();
		         }finally{
		        	 con.closeCon();
		         }
		    	
	    	
	    	
	    	
	    	return location;
	    }
	    
	    
	    //Retrieve Track Request List
	    //user is trackee
	    public ArrayList<User> getTrackRequestList(String sUserID){
	    	ArrayList<User> nameList = new ArrayList<User>();
	    	String result = "", sQuery;
	    	ResultSet rs;
	    	PreparedStatement ps;
	    	Integer iTrackeeID;
	    	
	    	iTrackeeID = Integer.parseInt(sUserID);
	    	
	    	
	    	sQuery = "SELECT id, name FROM user WHERE id IN("
	    			 + "SELECT trackerId FROM trackers WHERE trackeeId = ? AND trackstatus = 'pending')";
	    	   try{
		             ps = con.getConnection().prepareStatement(sQuery);
		             ps.setInt(1, iTrackeeID);
		             rs = ps.executeQuery();
		             
		             while(rs.next()){
		            	 nameList.add(new User(rs.getInt(1), rs.getString(2)));
		             }
		            
		             
		         }catch(SQLException e){
		             e.printStackTrace();
		         }finally{
		        	 con.closeCon();
		         }
		    	
	    	
	    	
	    	return nameList;
	    }
	    
	    //Retrieve Trackee List
	    public ArrayList<User> getTrackeeList(String sUserID){
	    	ArrayList<User> nameList = new ArrayList<User>();
	    	String result = "", sQuery;
	    	ResultSet rs;
	    	PreparedStatement ps;
	    	Integer iTrackeeID;
	    	
	    	iTrackeeID = Integer.parseInt(sUserID);
	    	
	    	
	    	sQuery = "SELECT id, name, email FROM user WHERE id IN("
	    			 + "SELECT trackeeId FROM trackers WHERE trackerId = ? AND trackstatus = 'pending')";
	    	   try{
		             ps = con.getConnection().prepareStatement(sQuery);
		             ps.setInt(1, iTrackeeID);
		             rs = ps.executeQuery();
		             
		             while(rs.next()){
		            	 nameList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
		             }
		            
		             
		         }catch(SQLException e){
		             e.printStackTrace();
		         }finally{
		        	 con.closeCon();
		         }
		    	
	    	
	    	
	    	return nameList;
	    }
	    
	    
	    
	    
	    
	    //RETRIEVE Trackee track mode
	    public String getTrackeeTrackMode(String sTrackeeID){
	    	String trackMode = "", sQuery;
	    	ResultSet rs;
	    	PreparedStatement ps;
	    	Integer iTrackeeID;
	    	
	    	iTrackeeID = Integer.parseInt(sTrackeeID);
	    	
	    	
	    		sQuery = "SELECT track_mode FROM user WHERE id = ?";
	    	   try{
		             ps = con.getConnection().prepareStatement(sQuery);
		             ps.setInt(1, iTrackeeID);
		             rs = ps.executeQuery();
		             
		             while(rs.next()){
		            	 trackMode = rs.getString(1);
		             }
		            
		         }catch(SQLException e){
		             e.printStackTrace();
		         }finally{
		        	 con.closeCon();
		         }
		    	
	    	
	    	
	    	

	    	return trackMode;
	    }
	    
	    
	    //RETRIEVE Trackee track interval
	    public String getTrackeeTrackInterval(String sTrackeeID){
	    	String trackInterval = "", sQuery;
	    	ResultSet rs;
	    	PreparedStatement ps;
	    	Integer iTrackeeID;
	    	
	    	iTrackeeID = Integer.parseInt(sTrackeeID);
	    	
	    	
	    		sQuery = "SELECT track_interval FROM user WHERE id = ?";
	    	   try{
		             ps = con.getConnection().prepareStatement(sQuery);
		             ps.setInt(1, iTrackeeID);
		             rs = ps.executeQuery();
		             
		             while(rs.next()){
		            	 trackInterval = String.valueOf(rs.getInt(1));
		             }
		            
		         }catch(SQLException e){
		             e.printStackTrace();
		         }finally{
		        	 con.closeCon();
		         }
		    	
	    	
	    	
	    	

	    	return trackInterval;
	    }
	    

	    //RETRIEVE Trackee location 
	    public String getTrackeeLocation(String sTrackerID, String sTrackeeID){
	    		Boolean isValid = false;
	    		Integer iTrackerID, iTrackeeID;
	    		PreparedStatement ps;
	    		String sQuery, location = "";
	    		ResultSet rs;
	    		
	    		iTrackerID = Integer.parseInt(sTrackerID);
	    		iTrackeeID = Integer.parseInt(sTrackeeID);
	    		
	    	   //check if tracker is still able to track trackee
	    		if(checkTrackTrackeeTrackingValidility(iTrackerID, iTrackeeID)){
	    			 
	    	         sQuery = "SELECT latitude, longtitude FROM user WHERE id = ?";
	    	        
	    	         try{
	    	 
	    	             ps = con.getConnection().prepareStatement(sQuery);
	    	             ps.setInt(1, iTrackeeID);
	    	             rs = ps.executeQuery();
	    	             
	    	             while(rs.next()){
	    	            	 location = rs.getString(1) + " " + rs.getString(2);
	    	             }
	    	             

	    	         }catch(SQLException e){
	    	             e.printStackTrace();
	    	             System.out.println("Trackee ID: " + sTrackeeID + " failed to retrieve location.");
	    	         }finally{
	    	        	 con.closeCon();
	    	         }
	    		}else{
	    			location = "ERROR Your trackee is no longer allowing you to track him/her.";
	    		}
	    		
	   
	    	return location;
	    }
	    
	    
	    //Retrieve id of the owner of the code
	    public int getUserIdBasedOnCode(String code){
	    	Integer id = -1;
	    	
	    	PreparedStatement ps;
	        ResultSet rs;
	         String sQuery = "SELECT id FROM user WHERE code = ?";
	        
	         
	         try{
	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, code);
	             rs = ps.executeQuery();
	             
	             if(rs.first()){
	            	 id = rs.getInt(1);
	             }
	            
	         }catch(SQLException e){
	             e.printStackTrace();
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    	
	    	
	    	return id;
	    }
	    
	    //Retrieve all the details of the logged in user
	    public String getUserDetails(String email, String password){
	    	
	    	PreparedStatement ps;
	        ResultSet rs;
	        String sQuery = "SELECT * FROM user WHERE email = ? AND password = ?";
	        String userDetails = "";
	         
	         try{
	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, email);
	             ps.setString(2, password);
	             rs = ps.executeQuery();
	             
	             while(rs.next()){
	            	 userDetails = String.valueOf(rs.getInt(1)) + " " + rs.getString(2) + " " + rs.getString(3) + " " +
	             rs.getString(4) +  " " + rs.getString(6) + " " + String.valueOf(rs.getDouble(7)) + " " + String.valueOf(rs.getDouble(8)) + " "
	             + rs.getString(9) + " " + String.valueOf(rs.getInt(10)) + " " + rs.getString(11);
	             }
	            
	         }catch(SQLException e){
	             e.printStackTrace();
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    	
	    	return userDetails;
	    }
	    
	    
	    
	    
	    
	    //CREATE
	    
	    //Track User
	    public int trackUser(String code, String trackerid){
	    	Integer doesExist = -1;
	    	Integer trackeeid = -1;
	    	
	    	
	    	trackeeid = getUserIdBasedOnCode(code);
	    	doesExist = checkIfCodeExists(code);
	    	
	    	if(doesExist != -1 && trackeeid != -1 ){
	    	PreparedStatement ps;
	    	String sQuery = "INSERT INTO trackers (trackid, trackerid, trackeeid, trackstatus) VALUES (null, ?, ?, 'pending')";
	    	
	    	   
	        try{
	            ps = con.getConnection().prepareStatement(sQuery);
	            ps.setInt(1, Integer.parseInt(trackerid));
	            ps.setInt(2, trackeeid);
	            ps.executeUpdate();
	        }catch(SQLException e){
	            e.printStackTrace();
	        }finally{
	       	 con.closeCon();
	        }
	    	
	    	}
	    	
	    	System.out.println("doesExist: " + doesExist);
	    	
	    	return doesExist;
	    }
	    
	    
	    
	    //Register User
	    public int registerUser(String name, String mobileNumber, String email, String password){
	    	 String userCode = new UserCodeGeneration().generateCode();
	    	 int registrationSuccess = 1;
	    	 PreparedStatement ps;
	         String sQuery = "INSERT INTO user (id,name, mobileNumber,email,password, code, latitude, longtitude, track_mode, track_interval, status)" +
	        		 		"VALUES (null, ?, ?,?, ?, ?, ?, ?, ?, ?, ?);";
	        
	         
	         try{
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, name);
	             ps.setString(2, mobileNumber);
	             ps.setString(3, email);
	             ps.setString(4, password);
	             ps.setInt(5, Integer.parseInt(userCode));
	             ps.setDouble(6, 0.0);
	             ps.setDouble(7, 0.0);
	             ps.setString(8, "Pulse");
	             ps.setInt(9, 300);
	             ps.setString(10, "Not Travelling");
	             ps.executeUpdate();
	         }catch(SQLException e){
	             e.printStackTrace();
	             registrationSuccess = -1;
	         }finally{
	        	 con.closeCon();
	         }
	    	
	         return registrationSuccess;
	    }
	    
	    
	    

	    
	    //DELETE
	    
	    

	    
	    //HELPER
	    
	    //Authenticate User for Login
	    public String authenticateUserForLogin(String email, String password){
	    	 Boolean isValid = false;
	    	 Integer flag = -1;
	    	 PreparedStatement ps;
	         ResultSet rs;
	         String sQuery = "SELECT email FROM user WHERE email = ? AND password = ?";
	         String sQuery2 = "SELECT * FROM user WHERE email = ? AND password = ?";
	         String userDetails = null;
	         String name = "", status = "";
	         
	         try{
	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, email);
	             ps.setString(2, password);
	             rs = ps.executeQuery();
	         
	        
	            	isValid = rs.next();
	            	System.out.println("ISVLAID!!: " + isValid);
	             
	         }catch(SQLException e){
	             e.printStackTrace();
	         }finally{
	        	 con.closeCon();
	         }
	         
	         
	         if(isValid){
	        	 try{
	        		 ps = con.getConnection().prepareStatement(sQuery2);
	        		 ps.setString(1, email);
	        		 ps.setString(2, password);
	        		 rs = ps.executeQuery();
	        		 
	        		 while(rs.next()){
	        		 
	        		 name = rs.getString(2);
	        		 name = name.replace(" ", "1");
	        		 status = rs.getString(11);
	        		 status = status.replace(" ", "1");
	        		
	        			 
	        		 userDetails = String.valueOf(rs.getInt(1)) + " " + name + " " + rs.getString(3) + " " +
	        	             rs.getString(4) + " " + rs.getString(6) + " " + String.valueOf(rs.getDouble(7)) + " " + String.valueOf(rs.getDouble(8)) + " "
	        	             + rs.getString(9) + " " + String.valueOf(rs.getInt(10)) + " " + status;
	        	             
	        		 }
	        		 
	        	 }catch(SQLException e){
		             e.printStackTrace();
		         }finally{
		        	 con.closeCon();
		         }
	         }
	    	
	   
	    	return String.valueOf(isValid) + " " + userDetails;
	    }
	    
	    
	    //Check if code entered by user exists
	    public int checkIfCodeExists(String code){
	    	int doesExist = -1;
	    	PreparedStatement ps;
	        ResultSet rs;
	         String sQuery = "SELECT * FROM user WHERE code = ?";
	        
	         
	         try{
	        	
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, code);
	             rs = ps.executeQuery();
	         
	           
	             
	             if(rs.first()){
	            	 doesExist = 1;
	 
	             }
	             
	         }catch(SQLException e){
	             e.printStackTrace();
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    	
	    	return doesExist;
	    }
	    
	    
	    //Check Tracker tracking access towards a trackee
	    public boolean checkTrackTrackeeTrackingValidility(Integer iTrackerID, Integer iTrackeeID){
	    	Boolean isValid = false;
	    	PreparedStatement ps;
	    	ResultSet rs;
	    	String sQuery, sResult = "";
	    	
	         try{
		         sQuery = "SELECT trackstatus FROM trackers WHERE trackerId = ? AND trackeeId = ?";
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setInt(1, iTrackerID);
	             ps.setInt(2, iTrackeeID);
	             rs = ps.executeQuery();
	         
	             while(rs.next()){
	            	sResult = rs.getString(1); 
	             }
	         }catch(SQLException e){
	             e.printStackTrace();
	         }finally{
	        	 con.closeCon();
	         }
	    	
	         if(sResult == "active")
	        	 isValid = true;
	         
	    	
	    	return isValid;
	    }

	    
	    
	      
	}

