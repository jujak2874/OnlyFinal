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
		System.out.println("Search 실행.. " + searchTerm);
		MemberDao dao = MemberDao.getInstance();
		List<Member> result = dao.searchMember(searchTerm);
		if(result.size()<=0){
			System.out.println("검색 결과 없음");	
		} else{
			System.out.println(result.size()+"명이 검색되었습니다");
			request.setAttribute("searchResult", result);
		}
		return "searchResult.jsp";
	}

}
