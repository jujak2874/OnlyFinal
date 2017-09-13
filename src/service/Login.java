package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import dao.MemberDao;
import dto.Member;

public class Login implements CommandProcess {
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Login 실행.");
		String id = request.getParameter("member_id");
		String pwd = request.getParameter("password");

		MemberDao dao = MemberDao.getInstance();
		int result = dao.login(id, pwd);
		Member member = dao.getMember(id);
		System.out.println(id + ", " + pwd);
		if(result>0){
			System.out.println("Login 성공  by " + id);
			request.setAttribute("id", id);
			request.setAttribute("name", member.getUsername());
			request.setAttribute("profile_img", member.getProfile_image());
		} else if(result==0){
			System.out.println("비밀번호가 맞지 않습니다");
		}else{
			System.out.println("해당 ID가 없음");
		}
		request.setAttribute("result", result);
		return "login.jsp";
	}

}
