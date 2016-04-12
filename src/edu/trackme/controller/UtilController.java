package edu.trackme.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.trackme.db.DBConnection;

public class UtilController {
	
	 	private DBConnection con;
	    
	    public UtilController(){
	        con = new DBConnection();
	    }
	    
	    public int checkIfCodeExists(String code){
	    	int doesExist = 1;
	    	PreparedStatement ps;
	        ResultSet rs;
	         String sQuery = "SELECT * FROM user WHERE code = ?";
	        
	         
	         try{
	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, code);
	             rs = ps.executeQuery();
	             
	           
	         
	             if(rs.next()){
	            	 doesExist = 1;
	             }
	             
	         }catch(SQLException e){
	             e.printStackTrace();
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    	
	    	return doesExist;
	    }
	    
	    
	    public int getUserIdBasedOnCode(String code){
	    	Integer id = -1;
	    	
	    	PreparedStatement ps;
	        ResultSet rs;
	         String sQuery = "SELECT id FROM user WHERE code = ?";
	        
	         
	         try{
	 
	             ps = con.getConnection().prepareStatement(sQuery);
	             ps.setString(1, code);
	             rs = ps.executeQuery();
	             
	             while(rs.next()){
	            	 id = rs.getInt(1);
	             }
	            
	         }catch(SQLException e){
	             e.printStackTrace();
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    	
	    	
	    	return id;
	    }
	    
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
	             rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7);
	             }
	            
	         }catch(SQLException e){
	             e.printStackTrace();
	         }finally{
	        	 con.closeCon();
	         }
	    	
	    	
	    	return userDetails;
	    }
	    
}
