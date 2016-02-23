package com.floatinvoice.esecurity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.floatinvoice.business.InvoiceService;
import com.floatinvoice.common.UserContext;
import com.floatinvoice.messages.BaseMsg;
import com.floatinvoice.messages.InvoiceDtlsMsg;
import com.floatinvoice.messages.ListMsg;
import com.floatinvoice.messages.UploadMessage;

@Controller
@RequestMapping(value="/invoice")
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;
	
    @RequestMapping(value = { "/view"}, method = RequestMethod.GET)
    public  ResponseEntity<ListMsg<InvoiceDtlsMsg>> viewNewInvoices(@RequestParam(value="acro", required=true) String acro) {
        return new ResponseEntity<ListMsg<InvoiceDtlsMsg>>(invoiceService.fetchAllNewInvoices(acro), HttpStatus.OK);
    }
    
    @RequestMapping(value = { "/funded"}, method = RequestMethod.GET)
    public  ResponseEntity<ListMsg<InvoiceDtlsMsg>> viewFundedInvoices(@RequestParam(value="acro", required=true) String acro) {
        return new ResponseEntity<ListMsg<InvoiceDtlsMsg>>(invoiceService.fetchFundedInvoices(acro), HttpStatus.OK);
    }

    @RequestMapping(value = { "/pending"}, method = RequestMethod.GET)
    public  ResponseEntity<ListMsg<InvoiceDtlsMsg>> viewPendingInvoices(@RequestParam(value="acro", required=true) String acro) {
        return new ResponseEntity<ListMsg<InvoiceDtlsMsg>>(invoiceService.fetchPendingInvoices(acro), HttpStatus.OK);
    }
    
    @RequestMapping(value = { "/upload"}, method = RequestMethod.POST)
    public  ResponseEntity<BaseMsg> uploadFile(@RequestParam(value="acro", required=true) String acro,
    		@RequestParam(value="filename", required=false) String fileName,
    		@RequestParam(value="file", required=true) MultipartFile file) throws Exception {    	
    	UploadMessage uploadMsg = new UploadMessage(file);
    	uploadMsg.setFileName(file.getOriginalFilename());
    	uploadMsg.setSmeAcronym(acro);    	
        return new ResponseEntity<BaseMsg>(invoiceService.uploadInvoiceFile(uploadMsg), HttpStatus.OK);
    }
    
    
    @RequestMapping(value = { "/credit"}, method = RequestMethod.POST)
    public  ResponseEntity<BaseMsg> creditInvoices(@RequestBody BaseMsg request) throws Exception {
        return new ResponseEntity<BaseMsg>(invoiceService.creditInvoice(request.getRefId()), HttpStatus.OK);
    }
    
    @RequestMapping(value = { "/bid"}, method = RequestMethod.POST)
    public  ResponseEntity<BaseMsg> bidInvoices(@RequestBody BaseMsg request) throws Exception {
    	List<String> refIds = request.getRefIds();
    	return new ResponseEntity<BaseMsg>(invoiceService.bidInvoice(refIds, UserContext.getAcronym()), HttpStatus.OK);
    }
}
