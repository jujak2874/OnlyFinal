package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.Member;

public class Search implements CommandProcess {
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		
		String searchTerm = request.getParameter("searchTerm");
		System.out.println("Search ����.. " + searchTerm);
		MemberDao dao = MemberDao.getInstance();
		List<Member> result = dao.searchMember(searchTerm);
		if(result.size()<=0){
			System.out.println("�˻� ��� ����");	
		} else{
			System.out.println(result.size()+"���� �˻��Ǿ����ϴ�");
			request.setAttribute("searchResult", result);
		}
		return "searchResult.jsp";
	}

}
