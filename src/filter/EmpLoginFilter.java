package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EmpLoginFilter implements Filter {
	private FilterConfig config;
    public EmpLoginFilter() {
    }

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		//讓登出後不能再被快取進來
		res.setHeader("Cache-Control", "no-store");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires",0);
		
		Object empVO1 = session.getAttribute("empVO1");
		if (empVO1 == null) {
			session.setAttribute("location2", req.getRequestURI());
			res.sendRedirect(req.getContextPath()+"/back_end/login/login.jsp");
			return;}
		else {
		chain.doFilter(request, response);
		}
		}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
