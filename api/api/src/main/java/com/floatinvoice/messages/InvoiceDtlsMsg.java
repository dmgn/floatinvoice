package com.floatinvoice.messages;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="InvoiceDtls")
@XmlAccessorType(value=XmlAccessType.PROPERTY)
public class InvoiceDtlsMsg extends BaseMsg{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name="amt")
	private double amount;
	
	@XmlElement(name="startDt")
	private Date startDt;
	
	@XmlElement(name="endDt")
	private Date endDt;
	
	@XmlElement(name="smeCtpy")
	private String smeCtpy;
	
	@XmlElement(name="sme", required=false)
	private String sme;
	
	@XmlElement(name="desc")
	private String desc;
	
	@XmlElement(name="status")
	private String status;

	@XmlElement(name="invoiceNo")
	private String invoiceNo;
	
	@XmlElement(name="financier")
	private String financier;
	
	@XmlElement(name="interestRate")
	private double interestRate;
	
	@XmlElement(name="loanPeriod")
	private int loanPeriod;
	
	
	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(int loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public String getSmeCtpy() {
		return smeCtpy;
	}

	public void setSmeCtpy(String smeCtpy) {
		this.smeCtpy = smeCtpy;
	}

	public String getSme() {
		return sme;
	}

	public void setSme(String sme) {
		this.sme = sme;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getFinancier() {
		return financier;
	}

	public void setFinancier(String financier) {
		this.financier = financier;
	}
	
	
}
