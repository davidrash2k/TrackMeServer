package edu.trackme.controller;


import edu.trackme.db.DBConnection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	
import edu.trackme.util.UserCodeGeneration;



public class MasterController {
	    
	    private DBConnection con;
	    
	    public MasterController(){
	        con = new DBConnection();
	    }
	    
	    
		
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
	             System.out.println("Userd ID: " + sUserID + " failed to update location @ latitude and longtitude (" + sLatitude + "," + sLongtitude);
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    	
	    	
	    }
	    
	    //RETRIEVE Trackee location 
	    public String getTrackeeLocation(String sTrackerID, String sTrackeeID){
	    		Boolean isValid = false;
	    		Integer iTrackerID, iTrackeeID;
	    		PreparedStatement ps;
	    		String sQuery, location;
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
	    			
	    		}
	    		
	    		
	    		
	    		
	    	
	    	return "";
	    }
	    
	 
	    
	    
	    //RETRIEVE Trackee track mode and track interval (if applicable)
	    
	    
	    //UPDATE track mode and interval (if applicable)
	    
	    
	    //UPDATE 
	    
	    
	    
	    //RETRIEVE
	    
	    
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
	             rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + String.valueOf(rs.getDouble(7)) + " " + String.valueOf(rs.getDouble(8)) + " "
	             + rs.getString(9) + " " + String.valueOf(rs.getInt(10));
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
	         String sQuery = "INSERT INTO user (id,name, mobileNumber,email,password, code, location)" +
	        		 		"VALUES (null, ?, ?,?, ?, ?, null);";
	        
	         
	         try{
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, name);
	             ps.setString(2, mobileNumber);
	             ps.setString(3, email);
	             ps.setString(4, password);
	             ps.setString(5, userCode);
	             ps.executeUpdate();
	         }catch(SQLException e){
	             e.printStackTrace();
	             registrationSuccess = -1;
	         }finally{
	        	 con.closeCon();
	         }
	    	
	         return registrationSuccess;
	    }
	    
	    
	    
	    //UPDATE
	    
	    
	    
	    //DELETE
	    
	    
	    //HELPER
	    
	    //Authenticate User for Login
	    public String authenticateUserForLogin(String email, String password){
	    	 Boolean isValid = false;
	    	 Integer flag = -1;
	    	 PreparedStatement ps;
	         ResultSet rs;
	         String sQuery = "SELECT email FROM user WHERE email = ? AND password = ?";
	         String sQuery2 = "SELECT id, name, mobileNumber, email, code, location FROM user WHERE email = ? AND password = ?";
	         String userDetails = null;
	         
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
	        		 
	        		 rs.next();
	        		 
	        		 userDetails = rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6); 
	        		 
	        		 
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
	    	
	    	return isValid;
	    }

	    
	    
	      
	}

