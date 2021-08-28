package com.fundtransfer.application.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fundtransfer.application.DTO.BeneficiaryDTO;
import com.fundtransfer.application.DTO.CredentialsDTO;
import com.fundtransfer.application.DTO.CustomerCreationDTO;
import com.fundtransfer.application.DTO.FundTransferDto;
import com.fundtransfer.application.exception.InvalidCredentialsException;
import com.fundtransfer.application.exception.ResourceNotFoundException;
import com.fundtransfer.application.exception.UserNameAlreadyExistsException;
import com.fundtransfer.application.model.Customer;
import com.fundtransfer.application.service.FundTransferService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFundTransferController 
{

	@Mock
	FundTransferService fundTransferService;
	
	@InjectMocks
	FundTransferController fundTransferController;
	
	
	static CustomerCreationDTO customerCreationDTO;
	static BeneficiaryDTO dto;
	static Customer customer;
	static CredentialsDTO credentials;
	static FundTransferDto fundTranferDto;
	
	@BeforeAll
	public void setUp()
	{
		credentials = new CredentialsDTO();
		credentials.setUserName("Sonali");
		credentials.setPassword("Sri123");
		
		customerCreationDTO = new CustomerCreationDTO();
		customerCreationDTO.setUserName("AI0329");
		customerCreationDTO.setDateOfBirth("2018-01-18");
		customerCreationDTO.setGender("female");
		customerCreationDTO.setMobileNo("7680094779");
		customerCreationDTO.setEmailId("sonali@gmail.com");
		customerCreationDTO.setPanCardNo("AULPK8888H");
		customerCreationDTO.setAadhaarCardNo("500607192799");
		customerCreationDTO.setAddress("Warangal");
		customerCreationDTO.setOpeningDeposit("9500");
		customerCreationDTO.setBankName("HDFC");
		customerCreationDTO.setBranchName("Kukatpally");
		customerCreationDTO.setIfsCode("HDFC00314");
		customerCreationDTO.setAccountType("Checking");
		
		
		// set values for BeneficiaryDTO
		dto = new BeneficiaryDTO();
		dto.setBeneficiaryAccountNo("4567");
		dto.setBeneficiaryName("Nagesh");
		dto.setIfsCode("HDF00314");
		dto.setTransferLimit("8000");
		
		// set values for fund transfer dto
		fundTranferDto = new FundTransferDto();
		fundTranferDto.setFromAccountNo("123456");
		fundTranferDto.setToAccountNo("4567");
		fundTranferDto.setRemarks("Expenses");
		fundTranferDto.setTransferAmount("1000");
		
		
	}
	
	
	@Test
	@DisplayName("Customer Registration :: Postive Scenario")
	@Order(1)
	public void testCustomerRegistrationPostive() {
		// Event
		when(fundTransferService.saveCustomerRegisterationDetails(customerCreationDTO)).thenReturn(new ResponseEntity<>("Sucess", HttpStatus.OK));
		//Event
		ResponseEntity<?> result = fundTransferController.customerRegisteration(customerCreationDTO);
	     //out come
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	
	@Test
	@DisplayName("Customer Registration:: Negative Scenario")
	@Order(2)
	public void testCustomerRegistrationNegative() {
		// context
		when(fundTransferService.saveCustomerRegisterationDetails(customerCreationDTO)).thenThrow(UserNameAlreadyExistsException.class);
		// Event and outcome
		assertThrows(UserNameAlreadyExistsException.class,()-> fundTransferController.customerRegisteration(customerCreationDTO));
	}
	
	@Test
	@DisplayName("Customer Credentials: Positive Scenario")
	@Order(3)
	public void testAuthenticateUser()
	{
		//context
		when(fundTransferService.checkCustomerCredential(credentials)).thenReturn(new ResponseEntity<>("Login success", HttpStatus.OK));
		
		//Event
		ResponseEntity<?> result = fundTransferController.checkCustomerCredentials(credentials);
		
		//out come
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	@DisplayName("Customer Credentials: Negative Scenario")
	@Order(4)
	public void testAuthenticateUser1()
	{
		//context
		when(fundTransferService.checkCustomerCredential(credentials)).thenThrow(InvalidCredentialsException.class);
		
		//Event and out come
		assertThrows(InvalidCredentialsException.class,()->fundTransferController.checkCustomerCredentials(credentials));
	}
	
	@Test
	@DisplayName("Save Beneficiary   :: Postive Scenario")
	@Order(5)
	public void testSaveBenificaryPositive()
	{
		//context
		Mockito.when(fundTransferService.saveCustomerBeneficiary(dto, "AI0326")).thenReturn(new ResponseEntity<>("Successfully added Beneficiary", HttpStatus.OK));
		//Event and  out come
		assertEquals(HttpStatus.OK, fundTransferController.saveCustomerBeneficiary(dto, "AI0326").getStatusCode());
	}
	
	@Test
	@DisplayName("Save Beneficiary   :: Negative Scenario")
	@Order(6)
	public void testSaveBenificaryNegative() throws ResourceNotFoundException {
		Mockito.when(fundTransferService.saveCustomerBeneficiary(dto, "AI0326")).thenThrow(ResourceNotFoundException.class);
		//Event and  out come
		assertThrows(ResourceNotFoundException.class, ()->fundTransferController.saveCustomerBeneficiary(dto, "AI0326"));
	}

	@Test
	@DisplayName("Fund Transfer ")
	@Order(7)
	public void testFundTransfer()
	{
		Mockito.when(fundTransferService.accountFundTransfer(fundTranferDto, "AI0326")).thenReturn(new ResponseEntity<>("Transaction Done Successfully", HttpStatus.OK));
		//Event and  out come
		assertEquals(HttpStatus.OK, fundTransferController.fundTransfer(fundTranferDto, "AI0326").getStatusCode());
	}
	
	
}

