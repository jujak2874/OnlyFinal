package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.Member;

public class SignUp implements CommandProcess {
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("SignUp 실행...");
		Member member = new Member();
		System.out.println("userID: " + request.getParameter("member_id"));
		System.out.println("nickname: " + request.getParameter("nickname"));
		System.out.println("password: " + request.getParameter("password"));
		System.out.println("email: " + request.getParameter("email"));
		member.setUserid(request.getParameter("member_id"));
		member.setUsername(request.getParameter("nickname"));
		member.setPassword(request.getParameter("password"));
		member.setEmail(request.getParameter("email"));
		MemberDao dao = MemberDao.getInstance();
		int result = dao.insert(member);
		if(result>0){
			System.out.println("SignUp 성공");
			
		} else{
			System.out.println("SignUp 실패");
		}
		request.setAttribute("result", result);
		return "signUpResult.jsp";
	}

}
