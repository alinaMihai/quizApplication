package ro.incrys.internship.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ro.incrys.internship.entities.Candidate;

/**
 * Servlet Filter implementation class AccessFilter
 */
@WebFilter("/protected/*")
public class AccessFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AccessFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// pass the request along the filter chain

		HttpSession s = req.getSession();
		Candidate wu = (Candidate) s.getAttribute("user");

		String url = req.getRequestURL().toString();

		if (wu == null) {
			res.sendRedirect("../../login.jsp");
		} else if (wu.getRole() == 0) {

			if (url.contains("/admin/")) {
				System.out.println(url);
				System.out.println("admin");
//				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.sendRedirect("../../errorPage.jsp");
                return;
			}
		}

		else {

			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
