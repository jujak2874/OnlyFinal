package dto;

import java.sql.Date;

public class Alert {
	private int aid; // "AID"; 
	private String status; //"STATUS" CHAR(1 BYTE) NOT NULL ENABLE, 
	private int type; //"TYPE" NUMBER(10,0) NOT NULL ENABLE, 
	private String userid1; //"USERID1" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	private String userid2; //"USERID2" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	private String url; //"URL" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	private Date created; //"CREATED" DATE NOT NULL ENABLE,
	
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUserid1() {
		return userid1;
	}
	public void setUserid1(String userid1) {
		this.userid1 = userid1;
	}
	public String getUserid2() {
		return userid2;
	}
	public void setUserid2(String userid2) {
		this.userid2 = userid2;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
}
