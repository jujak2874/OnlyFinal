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

import dto.Member;

public class MemberDao {
	private static MemberDao instance;

	private MemberDao() {

	}

	public static MemberDao getInstance() {
		if (instance == null) {
			instance = new MemberDao();
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

	public int insert(Member member) {
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		// 탈퇴일, 전화번호 , 생일은 가입시 수집하지 않음. = null
		// 기본이미지 = 0(img url 넣을 것)
		// status 0 = 탈퇴, 1 = 사용중, 2 = 일시정지(비활성화)
		String sql = "insert into MEMBER values (?,?,?,?,1,sysdate,sysdate,'/img_all/default_profile.png',null,null)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getUsername());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getEmail());
			result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("가입성공..");
			}
		} catch (Exception e) {
			System.out.println("가입실패..");
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

	public int login(String id, String pwd) {
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String dbPass = "";

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select password from MEMBER where userid = ?");
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			// 아이디와 패스워드가 일치하는지 검사
			if (rs.next()) {
				dbPass = rs.getString("password");
				if (dbPass.equals(pwd)) {
					result = 1;
					System.out.println("로그인 성공");
					// 로그인 성공 = 1
				} else {
					result = 0;
					System.out.println("로그인 실패 비밀번호 틀림");
					// 패스워드가 다를때 = 0
				}
			} else {
				result = -1;
				System.out.println("로그인 실패 아이디 없음");
				// 아이디가 다를때 = -1
			}
			return result;
		} catch (Exception e) {
			System.out.println("로그인 실패");
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

	public Member getMember(String id) {
		Member member = new Member();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from MEMBER where userid = ?";
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				member.setUserid(rs.getString("userid"));
				member.setUsername(rs.getString("username"));
				member.setProfile_image(rs.getString("profile_image"));
				member.setTel(rs.getString("tel"));
				member.setEmail(rs.getString("email"));
				member.setBirth(rs.getDate("birth"));
				member.setCreated(rs.getDate("created"));
				member.setModifed(rs.getDate("modified"));
				member.setPassword(rs.getString("password"));
			} else {
				System.out.println("멤버 불러오기 실패");
				// 아이디가 다를때 = -1
			}
		} catch (Exception e) {
			System.out.println("멤버 불러오기 실패");
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
		return member;
	}

	public List<Member> searchMember(String searchTerm) {
		List<Member> list = new ArrayList<Member>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("searchTerm> " + searchTerm);
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from MEMBER where userid like ('%' || ? || '%') or username like ('%' || ? || '%')");
			con = getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, searchTerm);
			pstmt.setString(2, searchTerm);
			
			rs = pstmt.executeQuery();
			while (true) {
				if (rs.next()) {
					Member member = new Member();
					member.setUserid(rs.getString("userid"));
					member.setUsername(rs.getString("username"));
					member.setTel(rs.getString("tel"));
					member.setEmail(rs.getString("email"));
					member.setBirth(rs.getDate("birth"));
					member.setProfile_image(rs.getString("profile_image"));
					list.add(member);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("검색 실패했음");
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
		return list;
	}
	
	public int checkId(String member_id) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			System.out.println("DB연결");
			String sql = "select * from member where userid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			System.out.println("DB검색");
			if (rs.next()) {
				result = 1;
			}
		} catch (SQLException e) {
		} finally {
			try {
				con.close();
				pstmt.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}
}