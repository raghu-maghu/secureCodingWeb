package test.accounttransfer;

import java.sql.Date;

public class AccountTransferCom {
	private int transId;
	private Date transDate;
	public int getTransId() {
		return transId;
	}
	public void setTransId(int transId) {
		this.transId = transId;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}
	public String getCreditAccount() {
		return creditAccount;
	}
	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}
	public String getDebitAccount() {
		return debitAccount;
	}
	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}
	public String getTransRermarks() {
		return transRermarks;
	}
	public void setTransRermarks(String transRermarks) {
		this.transRermarks = transRermarks;
	}
	private String userId;
	private double transAmount;
	private String creditAccount;
	private String debitAccount;
	private String transRermarks;

}
