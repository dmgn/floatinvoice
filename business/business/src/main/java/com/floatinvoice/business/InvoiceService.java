package com.floatinvoice.business;

import java.util.List;

import com.floatinvoice.messages.BaseMsg;
import com.floatinvoice.messages.InvoiceDtlsMsg;
import com.floatinvoice.messages.ListMsg;
import com.floatinvoice.messages.UploadMessage;

public interface InvoiceService {

	
	ListMsg<InvoiceDtlsMsg> fetchAllNewInvoices( String acronym );
	
	ListMsg<InvoiceDtlsMsg> fetchPendingInvoices( String acronym );

	ListMsg<InvoiceDtlsMsg> fetchFundedInvoices( String acronym );

	BaseMsg uploadInvoiceFile ( UploadMessage msg) throws Exception;
		
	BaseMsg creditInvoice ( String refId ) throws Exception;
	
	BaseMsg bidInvoice ( List<String> refIds, String acronym ) throws Exception;

}
