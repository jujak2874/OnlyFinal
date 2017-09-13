package service;

import dao.FollowDao;

public class Follow {
	public boolean CheckFollow(String userid1, String userid2){
		System.out.println("CheckFollow ½ÇÇà.");

		FollowDao dao = FollowDao.getInstance();
		int status = dao.getFollowStatus(userid1, userid2);
		System.out.println(userid1 + ", " + userid2 + ": " + status);
		boolean result = false;
		if(status == 1){
			result = true;
		} 
		return result;
	}

}
