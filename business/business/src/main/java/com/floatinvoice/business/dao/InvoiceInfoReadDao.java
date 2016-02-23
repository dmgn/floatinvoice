package com.floatinvoice.business.dao;

import java.util.List;

import com.floatinvoice.messages.BaseMsg;
import com.floatinvoice.messages.InvoiceDtlsMsg;
import com.floatinvoice.messages.ListMsg;

public interface InvoiceInfoReadDao {

	ListMsg<InvoiceDtlsMsg> fetchAllNewInvoices( String smeAcronym );
	
	ListMsg<InvoiceDtlsMsg> fetchInvoicesAvailableForBanks( int bankOrgId );

	Integer creditInvoice( String refId )throws Exception;
	
	ListMsg<InvoiceDtlsMsg> fetchFundedInvoices( int orgId );

	ListMsg<InvoiceDtlsMsg> fetchPendingInvoices( int orgId );
	
	BaseMsg bidInvoice ( List<String> refIds, int financierOrgId ) throws Exception;

}

