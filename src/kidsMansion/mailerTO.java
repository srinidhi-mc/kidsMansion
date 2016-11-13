package kidsMansion;

import java.sql.Date;

public class mailerTO {
	
	private String CREATE_DATE;
	private String MODIFIED_DATE;
	private int PID;
	private int ID ;
	private String BODY ;
	private String MAILER_LIST;
	private String SUBJECT;
	private String ATTACHMENT_PATH;
	private String ATTACHMENT_FILE;
	private char MAIL_SENT;
	
	public char getMAIL_SENT() {
		return MAIL_SENT;
	}
	public void setMAIL_SENT(char mAIL_SENT) {
		MAIL_SENT = mAIL_SENT;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public String getMODIFIED_DATE() {
		return MODIFIED_DATE;
	}
	public void setMODIFIED_DATE(String mODIFIED_DATE) {
		MODIFIED_DATE = mODIFIED_DATE;
	}
	public int getPID() {
		return PID;
	}
	public void setPID(int pID) {
		PID = pID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getBODY() {
		return BODY;
	}
	public void setBODY(String bODY) {
		BODY = bODY;
	}
	public String getMAILER_LIST() {
		return MAILER_LIST;
	}
	public void setMAILER_LIST(String mAILER_LIST) {
		MAILER_LIST = mAILER_LIST;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getATTACHMENT_PATH() {
		return ATTACHMENT_PATH;
	}
	public void setATTACHMENT_PATH(String aTTACHMENT_PATH) {
		ATTACHMENT_PATH = aTTACHMENT_PATH;
	}
	public String getATTACHMENT_FILE() {
		return ATTACHMENT_FILE;
	}
	public void setATTACHMENT_FILE(String aTTACHMENT_FILE) {
		ATTACHMENT_FILE = aTTACHMENT_FILE;
	}
	
	

}
