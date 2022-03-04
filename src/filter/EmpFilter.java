package filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;

import com.employee.model.EmployeeVO;

public class EmpFilter implements Filter {
	
	private FilterConfig config;
	
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();
		EmployeeVO empVO1 = (EmployeeVO)session.getAttribute("empVO1");
		String error = "<script>alert('您的權限不足!!');window.location.href='JavaScript:window.history.back()'</script>";
		String uri = req.getRequestURI();
		try {
		switch(uri) {
		case "/CFA102G5/back_end/emp/listAllEmp.jsp":
			if(empVO1.getDep_no() != 5) {
				out.print(error);
				break;
			}else {
				req.setAttribute("empVO1", empVO1);
				chain.doFilter(req, res);
				break;
				}
		case "/CFA102G5/back_end/emp/addEmp.jsp":	
			if(empVO1.getDep_no() != 5) {
				out.print(error);
				break;
			}else {
				req.setAttribute("empVO1", empVO1);
				chain.doFilter(req, res);
				break;
				}
		case "/CFA102G5/back_end/department/allDep.jsp":	
			if(empVO1.getDep_no() != 5) {
				out.print(error);
				break;
			}else {
				req.setAttribute("empVO1", empVO1);
				chain.doFilter(req, res);
				break;
				}
		case "/CFA102G5/back_end/department/addDep.jsp":
			if(empVO1.getDep_no() != 5) {
				out.print(error);
				break;
			}else {
				req.setAttribute("empVO1", empVO1);
				chain.doFilter(req, res);
				break;
				}
//		case "/CFA102G5/back_end/member/selectMemberPage.jsp":
//			if(empVO1.getDep_no() != 3 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/roomType/listAllRoomType.jsp":
//			if(empVO1.getDep_no() != 1 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/roomType/addRoomType.jsp":
//			if(empVO1.getDep_no() != 1 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/room/listAllRoom.jsp":
//			if(empVO1.getDep_no() != 1 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/room/addRoom.jsp":
//			if(empVO1.getDep_no() != 1 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/foodClass/allClass.jsp":
//			if(empVO1.getDep_no() != 4 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/foodClass/addClass.jsp":
//			if(empVO1.getDep_no() != 4 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/foodStore/allStore.jsp":
//			if(empVO1.getDep_no() != 4 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/foodStore/addStore.jsp":
//			if(empVO1.getDep_no() != 4 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
//		case "/CFA102G5/back_end/serviceCases/listAllCase.jsp":
//			if(empVO1.getDep_no() != 3 && empVO1.getDep_no() != 5) {
//				out.print(error);
//				break;
//			}
//			else {
//				req.setAttribute("empVO1", empVO1);
//				chain.doFilter(req, res);
//				break;
//				}
		}
		
	}catch(NullPointerException e) {
		out.print("<script>alert('請重新登入');window.location.href='/CFA102G5/back_end/login/login.jsp'</script>");
	}
	

}
}
