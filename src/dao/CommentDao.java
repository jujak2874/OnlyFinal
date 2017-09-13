package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.Comment;
import dto.Member;
import dto.Post;

public class CommentDao {
	private static CommentDao instance;
	
	private CommentDao() {
		
	}
	public static CommentDao getInstance() {
		if (instance == null) {
			instance = new CommentDao();
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
	
	public int insertComment(Comment comment) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		String sql = "insert into COMMENTS values(?,?,?,sysdate,cid_seq.NEXTVAL,0,null)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			// text
			pstmt.setString(1, comment.getText());
			System.out.println(comment.getText());
			// userId
			pstmt.setString(2, comment.getUserId());
			System.out.println(comment.getUserId());
			// postId
			pstmt.setInt(3, comment.getPid());
			System.out.println(comment.getPid());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(comment.getText()+" "+comment.getUserId()+" "+comment.getPid()+" "+"fail");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return result;
	}
	
	public List<Comment> commentView(int pid) {
		List<Comment> list = new ArrayList<Comment>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		/*String sql = "select * from comments where pid = ? and del='0' order by cid desc";*/
		String sql = "select * from comments c,member m where c.userid = m.userid and c.pid=? and del='0' order by cid desc";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setPid(rs.getInt("pid"));
				comment.setUserId(rs.getString("userid"));
				comment.setText(rs.getString("text"));
				comment.setCreated(rs.getDate("created"));
				list.add(comment);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				con.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
			}
		}
		return list;
	}
}
