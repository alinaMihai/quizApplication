package ro.incrys.internship.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.CandidateServletHelper;

/**
 * Servlet implementation class CandidateServlet
 * has the purpose to display quiz information of candidates
 */
@WebServlet("/CandidateServlet")
public class CandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CandidateServletHelper instance = new CandidateServletHelper();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CandidateServlet() {
		super();
	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = 0;
		String url = request.getQueryString();
		String methodName=url;
		
		if (url.indexOf("&") > 0) {
			 methodName = url.substring(0, url.indexOf("&"));

			 id = Integer.parseInt(url.substring(url.indexOf("=") + 1));
			
		}
		for (Method method : CandidateServletHelper.class.getDeclaredMethods()) {
			String name = method.getName();
			if (name.equals(methodName)) {
				try {
				
					method.setAccessible(true);
					method.invoke(instance, request, response, id);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					Logger.getLogger(CandidateServlet.class).debug(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
	}

}
