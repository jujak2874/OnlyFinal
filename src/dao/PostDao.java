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

import dto.Hashtag;
import dto.Member;
import dto.Post;

public class PostDao {
	private static PostDao instance;

	private PostDao() {
	}

	public static PostDao getInstance() {
		if (instance == null) {
			instance = new PostDao();
		}
		return instance;
	}

	private Connection getConnection() {
		Connection con = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
			con = ds.getConnection();
			System.out.println("DB�뿰寃곗꽦怨�");
		} catch (Exception e) {
			System.out.println("DB�뿰寃곗떎�뙣");
			System.out.println(e.getMessage());
		}
		return con;
	}

	public long insertPost(Post post) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		// postID�깮�꽦
		long myId = 0;
		String sqlIdentifier = "select pid_seq.NEXTVAL from dual";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sqlIdentifier);
			synchronized (this) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					myId = rs.getLong(1);
					System.out.println("pid_seq: " + myId);
				}
			}
		} catch (Exception e) {

		}

		String sql = "insert into POST values(?,sysdate,0,?,?,?,?,null,?)";
		try {
			/* con = getConnection(); */
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, myId);
			// question
			pstmt.setString(2, null);
			// text
			pstmt.setString(3, post.getText());
			// URL
			pstmt.setString(4, post.getUrl());
			// mid
			pstmt.setString(5, post.getUserid());
			pstmt.setInt(6, post.getType());
			result = pstmt.executeUpdate();
			System.out.println("�옉�꽦�꽦怨� from DAO");
		} catch (Exception e) {
			myId = 0;
			System.out.println("�옉�꽦�떎�뙣 from DAO");
			System.out.println(e.getMessage());
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
		return myId;
	}

	public List<Post> postView(Post post) {
		List<Post> list = new ArrayList<Post>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from post, follow where userid1 = ? and userid = userid2 and status = y order by pid desc";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, post.getUserid());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				post.setPid(rs.getInt("pid"));
				post.setUserid(rs.getString("member_id"));
				post.setCreated(rs.getDate("created"));
				post.setUrl(rs.getString("url"));
				post.setText(rs.getString("text"));
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

	public int insertHashtag(Hashtag ht) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into hash_tag values (seq_hid.nextval , ?, ?)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ht.getTag_id());
			pstmt.setLong(2, ht.getPost_id());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}

	public String memberExist(String memberTag) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String userId = null;
		String sql = "select userid from member where exists(select userid from member where userid=?) and userid=?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberTag);
			pstmt.setString(2, memberTag);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userId = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return userId;
	}

	public int insertMembertag(String memberTag, long pid) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into member_tag values (?,?)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberTag);
			pstmt.setLong(2, pid);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return result;
	}

	// �빐�떦 �쑀��媛� �뙏濡쒗븯�뒗 �쑀���쓽 post + �궡 post
	public List<Post> getTimelinePostList(String userid) {
		System.out.println("getTimelinePostList called.." + userid);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Post> postList = new ArrayList<Post>();
		String sql = "select * from post where userid in"
		 + "(select userid from member where userid in (select userid2 from follow where userid1 = ?) or userid = ?) order by pid desc";
		//String sql = "select * from post";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userid);
			rs = pstmt.executeQuery();
			System.out.println("리턴값이 있는지 확인");
			if (rs.next()) {
				do {
					System.out.println("pid: " + rs.getInt("pid"));
						Post post = new Post();
						post.setPid(rs.getInt("pid"));
						post.setText(rs.getString("text"));
						post.setUrl(rs.getString("url"));
						post.setUserid(rs.getString("userid"));
						post.setType(rs.getInt("type"));
						post.setCreated(rs.getDate("created"));
						// post.setModifi_date(rs.getDate("modifi_date"));
						// post.setDelete_chk(rs.getInt("delete_chk"));
						postList.add(post);
				} while (rs.next());
			}
		} catch (Exception e) {
			System.out.println("error:");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("error2: " + e.getMessage());
			}
		}
		if (postList != null)
			System.out.println(postList.size() + "개 포스트 리턴");
		return postList;
	}
	
	
	public List<Post> getBlogPostList(String userid) {
		System.out.println("getBlogPostList called.." + userid);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Post> postList = new ArrayList<Post>();
		String sql = "select * from post where userid = ? order by pid desc";
		//String sql = "select * from post";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			System.out.println("리턴값이 있는지 확인");
			if (rs.next()) {
				do {
						Post post = new Post();
						post.setPid(rs.getInt("pid"));
						post.setText(rs.getString("text"));
						post.setUrl(rs.getString("url"));
						post.setUserid(rs.getString("userid"));
						post.setType(rs.getInt("type"));
						post.setCreated(rs.getDate("created"));
						// post.setModifi_date(rs.getDate("modifi_date"));
						// post.setDelete_chk(rs.getInt("delete_chk"));
						postList.add(post);
				} while (rs.next());
			}
		} catch (Exception e) {
			System.out.println("error:");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("error2: " + e.getMessage());
			}
		}
		return postList;
	}
	
	public List<Hashtag> getHashtagList(int pid){
		List<Hashtag> hList=new ArrayList<Hashtag>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from hash_tag where pid = ? order by hname";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Hashtag ht=new Hashtag();
				ht.setPost_id(rs.getInt("pid"));
				ht.setTag_id(rs.getString("hname"));
				hList.add(ht);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("해시태그 리스트 불러오기 오류: " + e.getMessage());
			}
		}
		
		return hList;
	}
	
	public List<Member> getMembertagList(int pid){
		List<Member> mList=new ArrayList<Member>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from member_tag where pid = ? order by userid";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Member mb=new Member();
				mb.setUserid(rs.getString("userid"));
				mList.add(mb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				System.out.println("멤버태그 리스트 불러오기 오류: " + e.getMessage());
			}
		}
		return mList;
	}

}
