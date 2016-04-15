package edu.trackme.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.trackme.controller.MasterController;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String servletName = request.getParameter("servlet");
		System.out.println("SERVLET: " + servletName);
		MasterController controller = new MasterController();
		
		switch(servletName){
			case "login": 	String email = request.getParameter("email");
							String password = request.getParameter("password");
							System.out.println("RETURN OUTPUT: " + controller.authenticateUserForLogin(email, password));
							response.getWriter().write(controller.authenticateUserForLogin(email, password));
							break;
					
			case "register":int successfullyRegistered;
							successfullyRegistered = controller.registerUser(request.getParameter("name"), request.getParameter("mobileNumber"), request.getParameter("email"), request.getParameter("password"));
							response.getWriter().write(Integer.toString(successfullyRegistered));
							System.out.println("INTEGER: " + Integer.toString(successfullyRegistered));
							break;
			
			case "trackUser":int successfullyTrackedUser;
							 successfullyTrackedUser = controller.trackUser(request.getParameter("code"), request.getParameter("trackerID"));
							 response.getWriter().write(Integer.toString(successfullyTrackedUser));
							 break;
					
			case "getUserDetails" : response.getWriter().write(controller.getUserDetails(request.getParameter("email"),request.getParameter("password"))); 
							break;
			
			case "updateTrackMode": response.getWriter().write(controller.updateUserTrackMode(request.getParameter("id"), request.getParameter("track_mode")));
							break;
			
			case "updateUserArrived": controller.updateStatusArrived(request.getParameter("id"), request.getParameter("status"));
			
			case "getTrackRequestList":	Gson g = new Gson();
										String trackRequestList = g.toJson(controller.getTrackRequestList(request.getParameter("id")));
										System.out.println(trackRequestList);
										response.getWriter().write(trackRequestList); 
										break;
			
			case "getTrackeeList": 		Gson g2 = new Gson();
										String trackeeList = g2.toJson(controller.getTrackeeList(request.getParameter("id")));
										System.out.println(trackeeList);
										response.getWriter().write(trackeeList); break;
										
			case "updateUserLocation" : controller.updateUserLocation(request.getParameter("id"), request.getParameter("latitude"), request.getParameter("longitude")); break;
			
			case "getUserLocation"	: response.getWriter().write(controller.getUserLocation(request.getParameter("id"))); break;
			
			case "acceptTracker": controller.acceptTracker(request.getParameter("trackeeID"), request.getParameter("trackerID")); break;
				
			//case "declineTracker":
											
					
			default:break;
		}
		
	}

}
