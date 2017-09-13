package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.naming.*;
import javax.sql.*;

import dto.ChatMessage;

public class ChatDao {
	private static ChatDao instance;

	private ChatDao() {
	}

	public static ChatDao getInstance() {
		if (instance == null) {
			instance = new ChatDao();
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

	// 채팅방 있는지 확인
	// 있으면 그 채팅방에 메시지 저장, 없으면 채팅방을 만든 후 저장
	// ChatService(채팅메시지, 받는사람, 보내는사람)
	public void ChatService(String chatMessage, String getT, String sendT) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		System.out.println("ChatService() called.." + chatMessage + ", " + getT + ", " + sendT);
		String chatroom = ChkCR(getT, sendT); // 채팅방 아이디를 받아옴
		if (chatroom == null) {
			System.out.println("없음 ==> 채팅방 생성");
			chatroom = CreateChat(getT, sendT); // 채팅방 생성
		} else {
			System.out.println("있음 ==> " +chatroom);
		}
		String sql = "insert into chat_message values(?,?,sysdate,mid_seq.nextval,?,0)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sendT);
			pstmt.setString(2, chatMessage);
			pstmt.setString(3, chatroom);
			pstmt.executeUpdate();
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
	}

	private String CreateChat(String getT, String sendT) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into chat " + " values(?)";
		String chatroom = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (getT.compareTo(sendT) > 0) {
				pstmt.setString(1, getT + ":" + sendT);
				chatroom = getT + ":" + sendT;
			} else {
				pstmt.setString(1, sendT + ":" + getT);
				chatroom = sendT + ":" + getT;
			}
			pstmt.executeUpdate();
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
		return chatroom;
	}
	
	private int CreateChat(String chatroom) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into chat " + " values(?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chatroom);
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

	private String ChkCR(String getT, String sendT) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select cid from chat where cid = ?";
		String chatRoom = "";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (getT.compareTo(sendT) > 0) {
				System.out.println("ChkCR:" + getT + ":" + sendT);
				pstmt.setString(1, getT + ":" + sendT);
			} else {
				System.out.println("ChkCR:" + sendT + ":" + getT);
				pstmt.setString(1, sendT + ":" + getT);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				chatRoom = rs.getString(1);
				System.out.println(chatRoom);
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
		return chatRoom;
	}

	private int ChkCR(String chatroom) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select cid from chat where cid = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chatroom);
			result = pstmt.executeUpdate();
			
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
	public List<ChatMessage> chatload(String chatroom) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("chatLoad called.. " + chatroom);

		int isRoomExist = ChkCR(chatroom);
		if(isRoomExist<=0){
			if(CreateChat(chatroom)>0){
				System.out.println("채팅방 새로 개설");
			} else{
				System.out.println("채팅방 개설 실패");
			}
		}
		
		String sql = "select * from chat_message where cid=? order by cast(mid as int) asc";
		ResultSet rs = null;
		List<ChatMessage> chathistory = new ArrayList<ChatMessage>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chatroom);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("mid:" + rs.getInt("mid") + ": " + rs.getString("message"));
				ChatMessage cm = new ChatMessage();
				cm.setCid(rs.getString("cid"));
				cm.setMessage(rs.getString("message"));
				cm.setUserid(rs.getString("userid"));
				cm.setCreated(rs.getDate("created"));
				cm.setStatus(rs.getInt("status"));
				cm.setMid(rs.getInt("mid"));
				chathistory.add(cm);
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
		return chathistory;
	}

	public List<ChatMessage> chatRLoad(String user) {
		System.out.println("chatRLoad()실행.. " + user);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ChatMessage> messageList = new ArrayList<>();
		String sql = "select * from chat_message where mid in (select max(mid) from CHAT_MESSAGE where cid in (SELECT cid FROM chat where cid like ('%' || ? || '%')) group by cid) order by created";
		sql = "select * from chat_message, (select max(mid) mid, cid from chat_message where cid in (SELECT cid FROM chat where cid like ('%' || ? || '%') group by cid) group by cid) temp where temp.cid = chat_message.cid and temp.mid = chat_message.mid";
		/*
		 * sql =
		 * "select * from chat_message where mid in (select mid from chat)";
		 */
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString("cid"));
				do {
					System.out.println(rs.getString("message"));
					ChatMessage cm = new ChatMessage();
					cm.setMessage(rs.getString("message"));
					cm.setUserid(rs.getString("userid"));
					cm.setCreated(rs.getDate("created"));
					cm.setStatus(rs.getInt("status"));
					cm.setMid(rs.getInt("mid"));
					cm.setCid(rs.getString("cid"));
					messageList.add(cm);
				} while (rs.next());
			} else {
				System.out.println("메시지 기록 없음");
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
		return messageList;
	}
	
	public int readMessage(int mid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "update chat_message set status=1 where mid=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);;
			result = pstmt.executeUpdate();
			
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
	
	
	public int checkUnreadMessage(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = "select count(mid) from chat_message where cid like ('%' || ? || '%') and userid != ? and status=0";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberId);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
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