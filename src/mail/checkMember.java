package mail;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberClassVO;
import com.member.model.MemberService;

@WebServlet("/checkMember")
public class checkMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public checkMember() {
        super();
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		
		HttpSession session = req.getSession();
		Integer checkMem = (Integer) session.getAttribute("checkMem");
		String authCode = (String)session.getAttribute("authCode");
		String inputAuthCode = req.getParameter("authcode").trim();
		System.out.println(checkMem);
		if(authCode.equals(inputAuthCode)) {
			MemberService memSvc = new MemberService();
			memSvc.updateMemberstate(false,checkMem);
			MemberClassVO memVO = memSvc.getOne(checkMem);
			session.setAttribute("memVO", memVO);		
			String url = "/front_end/signin/checkMember.jsp";
			req.setAttribute("authSuccess", "authSuccess");
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);	
		}
		
	}

}
