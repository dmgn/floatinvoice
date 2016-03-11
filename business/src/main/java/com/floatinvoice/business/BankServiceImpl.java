package com.floatinvoice.business;

import java.util.Map;

import com.floatinvoice.business.dao.BankInfoDao;
import com.floatinvoice.business.dao.OrgReadDao;
import com.floatinvoice.messages.BankDtlsMsg;
import com.floatinvoice.messages.BaseMsg;
import com.floatinvoice.messages.ListMsg;
import com.floatinvoice.messages.LoanDtlsMsg;
import com.floatinvoice.messages.LoanInstallmentsDtlsMsg;

public class BankServiceImpl implements BankService {

	BankInfoDao bankInfoDao;	
	OrgReadDao orgReadDao;
	
	public BankServiceImpl() {
	}
	
	public BankServiceImpl(BankInfoDao bankInfoDao) {
		this.bankInfoDao = bankInfoDao;
	}
	
	@Override
	public ListMsg<BankDtlsMsg> fetchBankDetails(String acronym) {
		Map<String, Object> map = orgReadDao.findOrgId(acronym);
		int orgId = (Integer) map.get("company_id");
		return new ListMsg<>(bankInfoDao.fetchBankDetails(orgId));
	}

	@Override
	public BaseMsg saveBankDetails(BankDtlsMsg bankDetails) {
		return bankInfoDao.saveBankInfo(bankDetails);
	}

	@Override
	public BaseMsg approveLoan(LoanDtlsMsg loanDtlsMsg) {
		return bankInfoDao.approveLoan(loanDtlsMsg);
	}

	@Override
	public BaseMsg payLoanInstallment(LoanInstallmentsDtlsMsg installment) {
		return bankInfoDao.payLoanInstallment(installment);
	}

}
