package com.floatinvoice.messages;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="LoanInstallmentsDtls")
@XmlAccessorType(value=XmlAccessType.PROPERTY)
public class LoanInstallmentsDtlsMsg extends BaseMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="loanInstallmentId")
	private int loanInstallmentId;
	
	@XmlElement(name="loanRefId")
	private String loanRefId;
	
	@XmlElement(name="paymentDueDt")
	private Date paymentDueDt;
	
	@XmlElement(name="paidDt")
	private Date paidDt;
	
	@XmlElement(name="fees")
	private Double fees;
	
	@XmlElement(name="amt")
	private Double amt;
	
	@XmlElement(name="smeAcro")
	private String smeAcro;
	
	@XmlElement(name="financier")
	private String financier;
	

	public int getLoanInstallmentId() {
		return loanInstallmentId;
	}

	public void setLoanInstallmentId(int loanInstallmentId) {
		this.loanInstallmentId = loanInstallmentId;
	}

	public String getLoanRefId() {
		return loanRefId;
	}

	public void setLoanRefId(String loanRefId) {
		this.loanRefId = loanRefId;
	}

	public Date getPaymentDueDt() {
		return paymentDueDt;
	}

	public void setPaymentDueDt(Date paymentDueDt) {
		this.paymentDueDt = paymentDueDt;
	}

	public Date getPaidDt() {
		return paidDt;
	}

	public void setPaidDt(Date paidDt) {
		this.paidDt = paidDt;
	}

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public String getSmeAcro() {
		return smeAcro;
	}

	public void setSmeAcro(String smeAcro) {
		this.smeAcro = smeAcro;
	}

	public String getFinancier() {
		return financier;
	}

	public void setFinancier(String financier) {
		this.financier = financier;
	}


	
}
