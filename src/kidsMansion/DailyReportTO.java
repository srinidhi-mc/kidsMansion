package kidsMansion;

import java.sql.Date;

public class DailyReportTO {

	private	int	ID;
	private	String	CONTENT;
	private	int	STATUS;
	private	String	SEND_DATE;
	private	String	MAIL_ID;
	private	Date	CREATED_DATE;
	private	Date	UPDATED_DATE;
	private	String	SUBJECT;
	private	String	TIME;
	private	int	TO_SEND;
	private int DELETED;
	
	public int getDELETED() {
		return DELETED;
	}
	public void setDELETED(int dELETED) {
		DELETED = dELETED;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	public String getSEND_DATE() {
		return SEND_DATE;
	}
	public void setSEND_DATE(String sEND_DATE) {
		SEND_DATE = sEND_DATE;
	}
	public String getMAIL_ID() {
		return MAIL_ID;
	}
	public void setMAIL_ID(String mAIL_ID) {
		MAIL_ID = mAIL_ID;
	}
	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public Date getUPDATED_DATE() {
		return UPDATED_DATE;
	}
	public void setUPDATED_DATE(Date uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getTIME() {
		return TIME;
	}
	public void setTIME(String tIME) {
		TIME = tIME;
	}
	public int getTO_SEND() {
		return TO_SEND;
	}
	public void setTO_SEND(int tO_SEND) {
		TO_SEND = tO_SEND;
	}

	
	

}
