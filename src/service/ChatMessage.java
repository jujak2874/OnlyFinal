package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FollowDao;
import dto.Member;

public class ChatMessage implements CommandProcess {
   @Override
   public String requestPro(HttpServletRequest request, HttpServletResponse response) {
      System.out.println("Friend ½ÇÇà.");
      String id = request.getParameter("member_id");

      FollowDao dao = FollowDao.getInstance();
      List<Member> followees = dao.getFollowees(id);
      List<Member> followers = dao.getFollowers(id);
      
      System.out.println("Followee #:" + followees.size());
      System.out.println("Follower #:" + followers.size());
      if(followers.size()>0)
         request.setAttribute("followers", followers);
      if(followees.size()>0)
         request.setAttribute("followees", followees);
      
      
      return "friendList.jsp";
   }

}