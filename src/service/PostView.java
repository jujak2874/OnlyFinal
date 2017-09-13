package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PostDao;
import dto.Post;

public class PostView implements CommandProcess {
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		Post post = new Post();
		PostDao dao = PostDao.getInstance();
		List<Post> result = dao.postView(post);
		String mid = request.getParameter("member_id");
		String pid = request.getParameter("pid");
		String url = request.getParameter("url");
		String text = request.getParameter("text");
		
		System.out.println("mid : " + mid + ", pid : " + pid + ", url : " + url);
		if (result.size() > 0) {
			request.setAttribute("pid", pid);
			request.setAttribute("member_id", mid);
			request.setAttribute("url", url);
			request.setAttribute("text", text);
		} else {
			System.out.println("실패");
		}
		request.setAttribute("postResult", result);
		
		return "timeline.jsp";
	}
}
