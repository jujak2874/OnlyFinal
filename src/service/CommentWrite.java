package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;
import dto.Comment;

public class CommentWrite implements CommandProcess{
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		String text = request.getParameter("commentText");
		String userid = request.getParameter("userid");
		int pid = Integer.parseInt(request.getParameter("commentPid"));
		
		CommentDao dao = CommentDao.getInstance();
		Comment comment = new Comment();
		
		comment.setUserId(userid);
		comment.setText(text);
		comment.setPid(pid);
		
		int cid = dao.insertComment(comment);
		if(cid>0) {
			System.out.println("insert Comment");
		}else {
			System.out.println("fail");
		}
		
		request.setAttribute("commentResult", cid);
		return "commentWriteResult.jsp";
	}
}
