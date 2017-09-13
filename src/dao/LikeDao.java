package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.naming.*;
import javax.sql.*;

import dto.Alert;
import dto.ChatMessage;
import dto.Likes;

public class LikeDao {
	private static LikeDao instance;

	private LikeDao() {
	}

	public static LikeDao getInstance() {
		if (instance == null) {
			instance = new LikeDao();
		}
		return instance;
	}

	private Connection getConnection() {
		Connection con = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
			con = ds.getConnection();
			System.out.println("DB연결성공");
		} catch (Exception e) {
			System.out.println("DB연결실패");
			System.out.println(e.getMessage());
		}
		return con;
	}

	public int insert(Likes likes) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into likes values(?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, likes.getUserid());
			pstmt.setString(2, likes.getStatus());
			pstmt.setInt(3, likes.getPid());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return result;
	}

	public String checkLikeStatus(String memberId, int pid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "n";
		String sql = "select status from likes where userid = ? and pid=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, pid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getString("status");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
		}
		return result;
	}

	public int toggleLike(String memberId, int pid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		int likeStatus = existLike(memberId, pid);
		String sql = null;
		System.out.println("listStatus: " + likeStatus);
		if (likeStatus == -1) {
			Likes likes = new Likes();
			likes.setPid(pid);
			likes.setUserid(memberId);
			likes.setStatus("y");
			return insert(likes);
		} else {
			if (likeStatus == 1) {
				sql = "update likes set STATUS='n' where userid = ? and pid=?";
			} else if (likeStatus == 0) {
				sql = "update likes set STATUS='y' where userid = ? and pid=?";
			}
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberId);
				pstmt.setInt(2, pid);
				result = pstmt.executeUpdate();
				if (result > 0)
					System.out.println("like update성공");
				else
					System.out.println("like update실패");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
					if (rs != null)
						rs.close();
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	public int existLike(String memberId, int pid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		System.out.println("read post " + memberId + pid);
		String sql = "select status from likes where userid = ? and pid = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setInt(2, pid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("status").equals("y")) {
					result = 1;
				} else {
					result = 0;
				}
			} else {
				result = -1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
		}
		return result;
	}

	public int getLike(int pid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select count(userid) likcount from likes where pid = ? and status='y'";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt("likcount");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
		}
		return result;
	}

}