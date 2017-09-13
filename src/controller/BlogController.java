package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"*.blog"})
public class BlogController extends HttpServlet {

	 private static final long serialVersionUID = 1L;   
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public BlogController() {
	        super();
	        // TODO Auto-generated constructor stub
	    }

	    /**
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	     */
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	                      throws ServletException, IOException {
	    	String[] param = request.getServletPath().split("/");
	    	String userid = param[param.length-1].substring(0, param[param.length-1].indexOf('.'));
	    	request.setAttribute("blogowner", userid);
	    	System.out.println("getPathInfo" + userid);
	    	request.getRequestDispatcher("/blog.jsp").forward(request, response);
	    }

	    /**
	     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	     */
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	                throws ServletException, IOException {
	    	request.setCharacterEncoding("utf-8");
    		doGet(request, response);
	    }
}
