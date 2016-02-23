package com.floatinvoice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.floatinvoice.business.InvoiceService;
import com.floatinvoice.business.InvoiceServiceImpl;
import com.floatinvoice.business.ProfileService;
import com.floatinvoice.business.ProfileServiceImpl;
import com.floatinvoice.business.RegistrationService;
import com.floatinvoice.business.RegistrationServiceImpl;


@Configuration
public class BusinessServiceConfig {

	@Autowired
	ReadServicesConfig readServicesConfig;
	
	
	@Bean
	public 	InvoiceService invoiceService(){
		return new InvoiceServiceImpl(readServicesConfig.invoiceInfoReadDao(), readServicesConfig.invoiceFileUploadDao(),
				readServicesConfig.orgReadDao());
	}
	
	@Bean
	public 	ProfileService profileService(){
		return new ProfileServiceImpl(readServicesConfig.profileDao());
	}
	

	@Bean
	public 	RegistrationService registrationService(){
		return new RegistrationServiceImpl(readServicesConfig.registrationDao());
	}

	
}
