package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.Member;

public class FollowDao {
	private static FollowDao instance;

	private FollowDao() {

	}

	public static FollowDao getInstance() {
		if (instance == null) {
			instance = new FollowDao();
		}
		return instance;
	}

	private Connection getConnection() {
		Connection con = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
			con = ds.getConnection();
			System.out.println("DB���Ἲ��");
		} catch (Exception e) {
			System.out.println("DB�������");
			System.out.println(e.getMessage());
		}
		return con;
	}

	public int getFollowStatus(String userid1, String userid2) {
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String status = "n";

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select del_status from FOLLOW where userid1 = ? and userid2 = ?");
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, userid1);
			pstmt.setString(2, userid2);
			rs = pstmt.executeQuery();

			// Follow �̷��� �����ϴ��� �˻�
			if (rs.next()) {
				System.out.println(userid1 + ", " + userid2 + "�ȷ��� �̷� ����");
				status = rs.getString("del_status");
				if (status.equals("n")) {
					result = 1;
				} else {
					result = 0;
					// System.out.println("�������Դϴ�");
				}
			} else {
				System.out.println("�ȷ��� �̷� ����");
				// ���̵� �ٸ��� = -1
			}
		} catch (Exception e) {
			System.out.println("�ȷ��� ���� ��ȸ ����");
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	public int follow(String userid1, String userid2) {
		int result = -1;
		int followingStatus = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		System.out.println("follow.. ����");
		int status = getFollowStatus(userid1, userid2);
		if (status < 0) {
			System.out.println("add new following");
			sql = "insert into FOLLOW values (?,?,'n')";
			followingStatus = 1;
		} else if (status == 0) {
			System.out.println("change to follow");
			sql = "update FOLLOW set del_status = 'n' where userid1 = ? and userid2 = ?";
			followingStatus = 1;
		} else if (status == 1) { // �ȷ��� �̷��� ������ ���
			System.out.println("change to unfollow");
			sql = "update FOLLOW set del_status = 'y' where userid1 = ? and userid2 = ?";
		}
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid1);
			pstmt.setString(2, userid2);
			result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("�ȷ��� ����..");
			}
		} catch (Exception e) {
			System.out.println("�ȷ��� ����..");
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
		return followingStatus;
	}

	public List<Member> getFollowees(String id) {
		List<Member> list = new ArrayList<Member>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		System.out.println("getFollowees.. ����: " + id);

		sql = "select * from member where userid in (select userid1 from follow where userid2 = ? and del_status='n')";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					System.out.println(rs.getString("username"));
					Member member = new Member();
					member.setUserid(rs.getString("userid"));
					member.setUsername(rs.getString("username"));
					member.setProfile_image(rs.getString("profile_image"));
					member.setEmail(rs.getString("email"));
					list.add(member);
				} while (rs.next());
			} else {
				System.out.println("followee ����");
			}
		} catch (Exception e) {
			System.out.println("Followee �������� ����..");
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
		return list;
	}

	public List<Member> getFollowers(String id) {
		List<Member> list = new ArrayList<Member>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		System.out.println("getFollowers.. ����: " + id);

		sql = "select * from Member where userid in (select userid2 from follow where userid1 = ? and del_status='n')";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					System.out.println(rs.getString("username"));
					Member member = new Member();
					member.setUserid(rs.getString("userid"));
					member.setUsername(rs.getString("username"));
					member.setProfile_image(rs.getString("profile_image"));
					member.setEmail(rs.getString("email"));
					list.add(member);
				} while (rs.next());
			} else {
				System.out.println("follower ����");
			}
		} catch (Exception e) {
			System.out.println("Follower �������� ����..");
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
		return list;
	}
}