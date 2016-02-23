package com.floatinvoice.business.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.floatinvoice.common.InvoiceStatus;
import com.floatinvoice.common.UUIDGenerator;
import com.floatinvoice.common.UserContext;
import com.floatinvoice.messages.BaseMsg;
import com.floatinvoice.messages.InvoiceDtlsMsg;
import com.floatinvoice.messages.ListMsg;

@Repository
public class JdbcInvoiceInfoReadDao implements InvoiceInfoReadDao {

	private NamedParameterJdbcTemplate jdbcTemplate;
	private OrgReadDao orgReadDao;
	
	public JdbcInvoiceInfoReadDao(){
		
	}
	
	public JdbcInvoiceInfoReadDao( DataSource dataSource, OrgReadDao orgReadDao ){		
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.orgReadDao = orgReadDao;
	}

	
	
	
	
	@Override
	public ListMsg<InvoiceDtlsMsg> fetchAllNewInvoices(String smeAcronym) {
		final String sql = " SELECT * FROM INVOICE_INFO II, ORGANIZATION_INFO ORG WHERE "
				+ "II.COMPANY_ID = ORG.COMPANY_ID AND ORG.ACRONYM = :smeAcronym "
				+ " AND II.INVOICE_ID NOT IN (SELECT INL.INVOICE_ID FROM INVOICE_NOTIFICATION_LIST INL )";
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("smeAcronym", smeAcronym);
		List<InvoiceDtlsMsg> list = jdbcTemplate.query(sql, map, new InvoiceInfoRowMapper());
		ListMsg<InvoiceDtlsMsg> result = new ListMsg<>(list);
		if (list.size() > 0 )
			result.setCount(list.size());
		return result;
	}

	
	private class InvoiceInfoRowMapper implements RowMapper<InvoiceDtlsMsg>{

		@Override
		public InvoiceDtlsMsg mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			InvoiceDtlsMsg result = new InvoiceDtlsMsg();
			result.setAmount(rs.getDouble("amount"));
			result.setDesc(rs.getString("description"));
			result.setSmeCtpy(rs.getString("buyer_name"));
			result.setStartDt(rs.getDate("invoice_start_dt"));
			result.setEndDt(rs.getDate("invoice_end_dt"));
			result.setStatus(InvoiceStatus.ACTIVE.getStatus());
			result.setSme(rs.getString("acronym"));
			result.setInvoiceNo(rs.getString("invoice_no"));
			result.setRefId( rs.getString("ref_id"));
			return result;
		}
		
	}

	@Override
	public ListMsg<InvoiceDtlsMsg> fetchInvoicesAvailableForBanks(int bankOrgId) {
		
		final String sql = " SELECT SMEORG.ACRONYM AS SMEACRO, BUYERORG.ACRONYM AS BUYERACRO, II.AMOUNT amount, II.INVOICE_NO invoice_no, II.INVOICE_START_DT, "
				+ "II.INVOICE_END_DT, II.DESCRIPTION , II.REF_ID ref_Id "
				+ "FROM INVOICE_INFO II "
				+ "JOIN INVOICE_NOTIFICATION_LIST INL "
				+ "ON II.INVOICE_ID = INL.INVOICE_ID "
				+ "JOIN ORGANIZATION_INFO SMEORG "
				+ "ON II.COMPANY_ID = SMEORG.COMPANY_ID "
				+ "JOIN ORGANIZATION_INFO BUYERORG "
				+ "ON II.BUYER_ID = BUYERORG.COMPANY_ID "
				+ "WHERE "				
				+ "INL.FINANCIER_ID = :bankOrgId AND "
				+ "II.INVOICE_ID NOT IN (SELECT IFC.INVOICE_ID FROM INVOICE_FINANCIER_CANDIDATES IFC WHERE IFC.FINANCIER_ID = :bankOrgId) "
				+ "ORDER BY SMEORG.ACRONYM, BUYERORG.ACRONYM";
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("bankOrgId", bankOrgId);
		List<InvoiceDtlsMsg> list = jdbcTemplate.query(sql, map, new FinancierInvoiceViewRowMapper());
		ListMsg<InvoiceDtlsMsg> result = new ListMsg<>(list);
		if (list.size() > 0 )
			result.setCount(list.size());
		return result;
	}

	
	
	private class FinancierInvoiceViewRowMapper implements RowMapper<InvoiceDtlsMsg>{

		@Override
		public InvoiceDtlsMsg mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			InvoiceDtlsMsg result = new InvoiceDtlsMsg();
			result.setAmount(rs.getDouble("amount"));
			result.setDesc(rs.getString("description"));
			result.setSmeCtpy(rs.getString("BUYERACRO"));
			result.setStartDt(rs.getDate("invoice_start_dt"));
			result.setEndDt(rs.getDate("invoice_end_dt"));
			result.setStatus(InvoiceStatus.ACTIVE.getStatus());
			result.setSme(rs.getString("SMEACRO"));
			result.setInvoiceNo(rs.getString("invoice_no"));
			result.setRefId(rs.getString("ref_Id"));			
			return result;
		}
		
	} 

	@Override
	public Integer creditInvoice(String refId) throws Exception {
		int result = 0;
		final String sql = "SELECT * FROM INVOICE_INFO II WHERE II.REF_ID = :refId";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("refId", refId);
		Map<String, Object> resultMap = jdbcTemplate.queryForObject(sql, paramMap, new ColumnMapRowMapper());
		if (resultMap.size() == 1)
			result = resultMap.size();
		
		int invoiceId = (int) resultMap.get("invoice_id");
		Date invoiceDt = (Date) resultMap.get("invoice_start_dt");
		Date dueDt =  (Date) resultMap.get("invoice_end_dt");
		Calendar cal = Calendar.getInstance();
		cal.setTime(invoiceDt);
		cal.add(Calendar.DATE, 3);
		Date expirationDt = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateFormatted = sdf.format(expirationDt);
		
		
		List<Integer> financierOrgIds = orgReadDao.findAllFinancierOrgIds();
		
		final List<InvoiceNotificationBean> lst = new LinkedList<>();
		for(int financierOrgId : financierOrgIds){
			InvoiceNotificationBean bean = new InvoiceNotificationBean();
			bean.setExpirationDt(sdf.parse(dateFormatted));
			bean.setFinancierId(financierOrgId);
			bean.setInvoiceDt(invoiceDt);
			bean.setInvoiceId(invoiceId);
			lst.add(bean);
		}
		String batchSql = " INSERT INTO INVOICE_NOTIFICATION_LIST (INVOICE_ID, FINANCIER_ID, INSERT_DT, EXPIRATION_DT) "
				+ "VALUES (?,?,?,?)";
		jdbcTemplate.getJdbcOperations().batchUpdate(batchSql, new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InvoiceNotificationBean inb = (InvoiceNotificationBean) lst.get(i);
				ps.setInt(1, inb.getInvoiceId());
				ps.setInt(2, inb.getFinancierId());			
				ps.setTimestamp(3, new Timestamp(inb.getInvoiceDt().getTime()));	
				ps.setTimestamp(4, new Timestamp(inb.getExpirationDt().getTime()));				

			}			
			@Override
			public int getBatchSize() {
				return lst.size();
			}
		});
		return result;
	}
	
	
	private class InvoiceNotificationBean{
		private Integer invoiceId;
		private Date invoiceDt;
		private Date expirationDt;
		private int financierId;
		
		
		public Integer getInvoiceId() {
			return invoiceId;
		}
		public void setInvoiceId(Integer invoiceId) {
			this.invoiceId = invoiceId;
		}
		public Date getInvoiceDt() {
			return invoiceDt;
		}
		public void setInvoiceDt(Date invoiceDt) {
			this.invoiceDt = invoiceDt;
		}
		public Date getExpirationDt() {
			return expirationDt;
		}
		public void setExpirationDt(Date expirationDt2) {
			this.expirationDt = expirationDt2;
		}
		public int getFinancierId() {
			return financierId;
		}
		public void setFinancierId(int financierId) {
			this.financierId = financierId;
		}
	}

	@Override
	public ListMsg<InvoiceDtlsMsg> fetchFundedInvoices(int orgId) {
		final String sql = "SELECT IFC.REF_ID, IFC.INTEREST_RATE, IFC.LOAN_PERIOD, II.BUYER_NAME, II.AMOUNT, II.INVOICE_NO, II.INVOICE_START_DT, II.INVOICE_END_DT, OI.ACRONYM FROM INVOICE_INFO II "
				+ "	JOIN INVOICE_FINANCIER_CANDIDATES IFC "
				+ " ON II.INVOICE_ID = IFC.INVOICE_ID "
				+ " JOIN ORGANIZATION_INFO OI "
				+ " ON OI.COMPANY_ID = IFC.FINANCIER_ID "
				+ " JOIN ORGANIZATION_INFO SME "
				+ " ON SME.COMPANY_ID = II.COMPANY_ID "
				+ " WHERE II.COMPANY_ID = :orgId ORDER BY II.INVOICE_ID" ;
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("orgId", orgId);
		List<InvoiceDtlsMsg> list = jdbcTemplate.query(sql, map, new FundedInvoiceViewRowMapper());
		ListMsg<InvoiceDtlsMsg> result = new ListMsg<>(list);
		if (list.size() > 0 )
			result.setCount(list.size());
		return result;
	}

	
	private class FundedInvoiceViewRowMapper implements RowMapper<InvoiceDtlsMsg>{
		@Override
		public InvoiceDtlsMsg mapRow(ResultSet rs, int idx)
				throws SQLException {
			InvoiceDtlsMsg result = new InvoiceDtlsMsg();
			result.setRefId(rs.getString("REF_ID"));
			result.setInterestRate(rs.getDouble("INTEREST_RATE"));
			result.setLoanPeriod(rs.getInt("LOAN_PERIOD"));
			result.setSmeCtpy(rs.getString("BUYER_NAME"));
			result.setAmount(rs.getDouble("AMOUNT"));
			result.setInvoiceNo(rs.getString("INVOICE_NO"));
			result.setStartDt(rs.getDate("INVOICE_START_DT"));
			result.setEndDt(rs.getDate("INVOICE_END_DT"));
			result.setFinancier(rs.getString("ACRONYM"));
			return result;
		}
	}
	
	
	@Override
	public ListMsg<InvoiceDtlsMsg> fetchPendingInvoices(int orgId) {
		final String sql = "SELECT II.REF_ID, II.BUYER_NAME, II.AMOUNT, II.INVOICE_NO, II.INVOICE_START_DT, II.INVOICE_END_DT, OI.ACRONYM FROM INVOICE_INFO II "
				+ "	JOIN INVOICE_NOTIFICATION_LIST INL "
				+ " ON II.INVOICE_ID = INL.INVOICE_ID "
				+ " JOIN ORGANIZATION_INFO OI "
				+ " ON OI.COMPANY_ID = INL.FINANCIER_ID "
				+ " JOIN ORGANIZATION_INFO SME "
				+ " ON SME.COMPANY_ID = II.COMPANY_ID "				
				+ " WHERE II.COMPANY_ID = :orgId ORDER BY II.INVOICE_ID" ;
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("orgId", orgId);
		List<InvoiceDtlsMsg> list = jdbcTemplate.query(sql, map, new PendingInvoiceViewRowMapper());
		ListMsg<InvoiceDtlsMsg> result = new ListMsg<>(list);
		if (list.size() > 0 )
			result.setCount(list.size());
		return result;
	}
	
	private class PendingInvoiceViewRowMapper implements RowMapper<InvoiceDtlsMsg>{
		@Override
		public InvoiceDtlsMsg mapRow(ResultSet rs, int idx)
				throws SQLException {
			InvoiceDtlsMsg result = new InvoiceDtlsMsg();
			result.setRefId(rs.getString("REF_ID"));
			result.setSmeCtpy(rs.getString("BUYER_NAME"));
			result.setAmount(rs.getDouble("AMOUNT"));
			result.setInvoiceNo(rs.getString("INVOICE_NO"));
			result.setStartDt(rs.getDate("INVOICE_START_DT"));
			result.setEndDt(rs.getDate("INVOICE_END_DT"));
			result.setFinancier(rs.getString("ACRONYM"));
			return result;
		}
	}

	@Override
	public BaseMsg bidInvoice(List<String> refIds, int financierOrgId) throws Exception {
		BaseMsg resultBaseMsg = null;
		String refId = null;
		for(String tmpRefId : refIds){
			refId = tmpRefId;
		}
		final String sql = "SELECT II.INVOICE_ID, INL.FINANCIER_ID FROM INVOICE_INFO II JOIN "
				+ " INVOICE_NOTIFICATION_LIST INL ON "
				+ " II.INVOICE_ID = INL.INVOICE_ID WHERE II.REF_ID = :refId "
				+ " AND INL.FINANCIER_ID = :financierOrgId";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("refId",refId);
		params.addValue("financierOrgId",financierOrgId);
		Map<String, Object> result = jdbcTemplate.queryForObject(sql, params, new ColumnMapRowMapper());
		int invoiceId = (Integer) result.get("INVOICE_ID");
		int orgId = (Integer) result.get("FINANCIER_ID");
		final String insertSql = "INSERT INTO INVOICE_FINANCIER_CANDIDATES (INVOICE_ID, FINANCIER_ID, INTEREST_RATE, LOAN_PERIOD, REF_ID, REQUEST_ID, UPDATE_BY, SOURCE_APP)"
				+ " VALUES (:invoiceId,:financierOrgId,:interestRate,:loanPeriod,:refId, :reqId, :updateBy, :source)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("invoiceId", invoiceId);
		paramMap.put("financierOrgId", orgId);
		paramMap.put("interestRate", new Double(16.00));
		paramMap.put("loanPeriod", 3);
		paramMap.put("refId", UUIDGenerator.newRefId());
		paramMap.put("reqId", UserContext.getRequestId());
		paramMap.put("updateBy", UserContext.getUserName());		
		paramMap.put("source", 0);		
		int row = jdbcTemplate.update(insertSql, paramMap);
		
		if(row == 1){
			resultBaseMsg = new BaseMsg();
		}
		
		return resultBaseMsg;
	}
	
}
