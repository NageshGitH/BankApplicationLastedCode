package com.fundtransfer.application.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.application.DTO.BeneficiaryDTO;
import com.fundtransfer.application.DTO.CredentialsDTO;
import com.fundtransfer.application.DTO.CustomerCreationDTO;
import com.fundtransfer.application.DTO.FundTransferDto;
import com.fundtransfer.application.exception.UserNameAlreadyExistsException;
import com.fundtransfer.application.service.FundTransferService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value ="/fundTransfer")
@EnableTransactionManagement
@Slf4j
public class FundTransferController
{
	private static final Logger logger = LoggerFactory.getLogger(FundTransferController.class);
	
	@Autowired
	FundTransferService fundTransferService;
	
    @PostMapping("/")
	public ResponseEntity<String> customerRegisteration(@Valid @RequestBody CustomerCreationDTO request)  throws UserNameAlreadyExistsException
	{
    	logger.info("inside customerAccountOpening");
		return fundTransferService.saveCustomerRegisterationDetails(request);
	}
    
    @PostMapping("/{userName}")
	public ResponseEntity<String> saveCustomerBeneficiary(@Valid @RequestBody BeneficiaryDTO request,@PathVariable("userName") String userName)  
	{
    	logger.info("inside saveBeneficiaryDetails");
		return fundTransferService.saveCustomerBeneficiary(request,userName);
	}
    
    @PostMapping("/checkCredentials")
	public ResponseEntity<String> checkCustomerCredentials(@Valid @RequestBody CredentialsDTO custCredentials)  
	{
    	logger.info("inside checkCredentials");
		return fundTransferService.checkCustomerCredential(custCredentials);
	}
    @PostMapping("/fundTransfer/{userName}")
   	public ResponseEntity<String> fundTransfer(@Valid @RequestBody FundTransferDto fundTransferDto,@PathVariable("userName") String userName)  
   	{
       	logger.info("inside checkCredentials");
   		return fundTransferService.accountFundTransfer(fundTransferDto,userName);
   	}

}
