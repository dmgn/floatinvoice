package com.floatinvoice.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;

import com.floatinvoice.business.dao.InvoiceFileUploadDao;
import com.floatinvoice.business.dao.InvoiceInfoReadDao;
import com.floatinvoice.business.dao.OrgReadDao;
import com.floatinvoice.common.OrgType;
import com.floatinvoice.messages.BaseMsg;
import com.floatinvoice.messages.InvoiceDtlsMsg;
import com.floatinvoice.messages.ListMsg;
import com.floatinvoice.messages.UploadMessage;


public class InvoiceServiceImpl implements InvoiceService {

	InvoiceInfoReadDao invoiceInfoReadDao;
	InvoiceFileUploadDao invoiceFileUploadDao;
	OrgReadDao orgReadDao;
	
	public InvoiceServiceImpl(){
		
	}
	
	public InvoiceServiceImpl( InvoiceInfoReadDao invoiceInfoReadDao, InvoiceFileUploadDao invoiceFileUploadDao,
			OrgReadDao orgReadDao){		
		this.invoiceInfoReadDao = invoiceInfoReadDao;
		this.invoiceFileUploadDao = invoiceFileUploadDao;
		this.orgReadDao = orgReadDao;
	}

	@Override
	public ListMsg<InvoiceDtlsMsg> fetchAllNewInvoices(String acronym) {
		
		Map<String, Object> org = orgReadDao.findOrgId(acronym);
		String type = (String) org.get("ORG_TYPE");
		Integer bankOrgId = (Integer) org.get("COMPANY_ID");
		
		if(OrgType.BANK.getText().equalsIgnoreCase(type)){
			return invoiceInfoReadDao.fetchInvoicesAvailableForBanks(bankOrgId);
		}else
			return invoiceInfoReadDao.fetchAllNewInvoices(acronym);
	}

	@Override
	public BaseMsg uploadInvoiceFile(UploadMessage msg) throws Exception {
		try {
			final Workbook workbook = WorkbookFactory.create(msg.getFile().getInputStream());
			workbook.getSheetAt(0);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			
			Map<String, List<Object>> invoiceDtMap = new HashMap<>();
			Map<String, List<Object>> invoiceDueDtMap = new HashMap<>();
			Map<String, List<Object>> amountMap = new HashMap<>();
			Map<String, List<Object>> invoiceNo = new HashMap<>();
			Map<String, List<Object>> customerMap = new HashMap<>();
			Map<String, List<Object>> description = new HashMap<>();

			
			while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while(cellIterator.hasNext()) {
		            Cell cell = cellIterator.next();
		            if( cell.getCellType() == Cell.CELL_TYPE_STRING && row.getRowNum() == 0){
			            if("INVOICE_DATE".equalsIgnoreCase(cell.getStringCellValue())){
			            	invoiceDtMap.put("INVOICE_DATE", new LinkedList<>());
			            }
						if("DUE_DATE".equalsIgnoreCase(cell.getStringCellValue())){
							invoiceDtMap.put("DUE_DATE", new LinkedList<>());
						}							            
						if("AMOUNT".equalsIgnoreCase(cell.getStringCellValue())){
							invoiceDtMap.put("AMOUNT", new LinkedList<>());
						}
						if("INVOICE_NO".equalsIgnoreCase(cell.getStringCellValue())){
							invoiceDtMap.put("INVOICE_NO", new LinkedList<>());						
						}
						if("CUSTOMER_NAME".equalsIgnoreCase(cell.getStringCellValue())){
							invoiceDtMap.put("CUSTOMER_NAME", new LinkedList<>());						
						}
						if("DESCRIPTION".equalsIgnoreCase(cell.getStringCellValue())){
							invoiceDtMap.put("DESCRIPTION", new LinkedList<>());
						}
		            }else{

		            switch (cell.getCellType()) 
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print( "Numeric : " + cell.getNumericCellValue() + "t");
                            if (DateUtil.isCellDateFormatted(cell)) {
                                if(cell.getColumnIndex() == 0){
                                	((LinkedList)invoiceDtMap.get("INVOICE_DATE")).add(cell.getDateCellValue());
                                }
                                if(cell.getColumnIndex() == 1){
                                	((LinkedList) invoiceDtMap.get("DUE_DATE")).add(cell.getDateCellValue());
                                }                                
                            } else {
                                System.out.println(cell.getNumericCellValue());
                                if(cell.getColumnIndex() == 2){
                                	((LinkedList)invoiceDtMap.get("AMOUNT")).add((Double)cell.getNumericCellValue());
                                }
                                if(cell.getColumnIndex() == 3){
                                	((LinkedList)invoiceDtMap.get("INVOICE_NO")).add(String.valueOf(cell.getNumericCellValue()));
                                }
                                
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                        	 if(cell.getColumnIndex() == 4){
                             	((LinkedList)invoiceDtMap.get("CUSTOMER_NAME")).add(cell.getStringCellValue());
                             }
                        	 if(cell.getColumnIndex() == 5){
                             	((LinkedList)invoiceDtMap.get("DESCRIPTION")).add(cell.getStringCellValue());
                             }
                        	break;
                    }
		            }
		        }
			}
			return invoiceFileUploadDao.fileUpload(msg, invoiceDtMap);
		} catch (EncryptedDocumentException | InvalidFormatException
				| IOException e) {
			throw e;
		}
	}

	@Override
	public BaseMsg creditInvoice(String refId) throws Exception {
		BaseMsg response = null;
		int result = invoiceInfoReadDao.creditInvoice(refId);
		if (result > 0){
			response = new BaseMsg();	
			response.addInfoMsg("File uploaded successfully", HttpStatus.OK.value());
		}
		return response;
	}

	@Override
	public ListMsg<InvoiceDtlsMsg> fetchFundedInvoices(String acronym) {	
		Map<String, Object> org = orgReadDao.findOrgId(acronym);
		Integer orgId = (Integer) org.get("COMPANY_ID");
		return invoiceInfoReadDao.fetchFundedInvoices(orgId);
	}

	@Override
	public ListMsg<InvoiceDtlsMsg> fetchPendingInvoices(String acronym) {
		Map<String, Object> org = orgReadDao.findOrgId(acronym);
		Integer orgId = (Integer) org.get("COMPANY_ID");
		return invoiceInfoReadDao.fetchPendingInvoices(orgId);
	}

	@Override
	public BaseMsg bidInvoice(List<String> refIds, String acronym) throws Exception {
		Map<String, Object> org = orgReadDao.findOrgId(acronym);
		Integer orgId = (Integer) org.get("COMPANY_ID");
		BaseMsg response =  invoiceInfoReadDao.bidInvoice(refIds, orgId);
		response.addInfoMsg("Success", HttpStatus.OK.value());
		return response;
	}
	
	
}
