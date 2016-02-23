package com.floatinvoice.test.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.floatinvoice.common.UserContext;
import com.floatinvoice.messages.BaseMsg;
import com.floatinvoice.messages.ListMsg;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:Beans.xml")
@WebAppConfiguration
public class InvoiceControllerTest {

	
	@Autowired
	private WebApplicationContext wac;
	private ObjectMapper objMapper;
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		this.mockMvc = webAppContextSetup(this.wac).build();
		objMapper = wac.getBean(ObjectMapper.class);
	}
	
	@Test
	public void testViewInvoices() throws Exception{
		RequestBuilder req = get("/invoice/view?acro=COTIND").contentType(MediaType.APPLICATION_JSON);
		MvcResult res = mockMvc.perform(req).andDo(print()).andReturn();	
	}
	
	@Test
	public void testFinancierViewInvoices() throws Exception{
		RequestBuilder req = get("/invoice/view?acro=BOAMERY").contentType(MediaType.APPLICATION_JSON);
		MvcResult res = mockMvc.perform(req).andDo(print()).andReturn();	
	}
	
	
	@Test
	public void testUploadFiles() throws Exception{
		Resource resource = new ClassPathResource("test.xlsx");
		MockMultipartFile file = new MockMultipartFile("file", resource.getInputStream());
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		RequestBuilder reqBuilder = MockMvcRequestBuilders.fileUpload("/invoice/upload?acro=COTIND&filename=test.xlsx")
				.file(file);
		
		mockMvc.perform(reqBuilder).andDo(print());		
	}
	
	@Test
	public void testCreditInvoices() throws Exception{
		BaseMsg msg = new BaseMsg();
		msg.setRefId("XYZ1");
		RequestBuilder req = post("/invoice/credit").header("remote-user", "abc.xyz@gmail.com")
				.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(msg));
		MvcResult res = mockMvc.perform(req).andDo(print()).andReturn();	
	}
	
	@Test
	public void testBidInvoices() throws Exception{
		BaseMsg msg = new BaseMsg();
		msg.setRefIds(Arrays.asList("A94CCB5C51B34D24887C4795975A562B"));
		UserContext.addContextData(null, "CITIBANK", null, 0);
		System.out.println(" ******* " + objMapper.writeValueAsString(msg));		
		RequestBuilder req = post("/invoice/bid?acro=CITIBANK").header("remote-user", "gnaik@floatinvoice.com")
				.contentType(MediaType.APPLICATION_JSON).content(objMapper.writeValueAsString(msg));
		MvcResult res = mockMvc.perform(req).andDo(print()).andReturn();	
	}
}
