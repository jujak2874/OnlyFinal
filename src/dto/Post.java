package dto;

import java.sql.Date;

public class Post {
	
	private int pid; //"PID" NUMBER(38,0) NOT NULL ENABLE, 
	private Date created;//"CREATED" DATE NOT NULL ENABLE, 
	private String del; //"DEL" CHAR(1 BYTE) NOT NULL ENABLE, 
	private String qna; //"QNA" VARCHAR2(1000 BYTE), 
	private String text; //"TEXT" VARCHAR2(1000 BYTE), 
	private String url; //"URL" VARCHAR2(1000 BYTE), 
	private String userid; //"USERID" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	private Date modified; //"MODIFIED" CHAR(18 BYTE), 
	private int type; //"TYPE" CHAR(18 BYTE), 
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getQna() {
		return qna;
	}
	public void setQna(String qna) {
		this.qna = qna;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	/*private String post_id;
	private Date write_date;
	private Date modifi_date;
	private int delete_chk;
	private String member_id;
	private String qna;
	private String text;
	private String url;
	private int type;
*/
	/*public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

	public Date getModifi_date() {
		return modifi_date;
	}

	public void setModifi_date(Date modifi_date) {
		this.modifi_date = modifi_date;
	}

	public int getDelete_chk() {
		return delete_chk;
	}

	public void setDelete_chk(int delete_chk) {
		this.delete_chk = delete_chk;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getQna() {
		return qna;
	}

	public void setQna(String qna) {
		this.qna = qna;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}*/

}
