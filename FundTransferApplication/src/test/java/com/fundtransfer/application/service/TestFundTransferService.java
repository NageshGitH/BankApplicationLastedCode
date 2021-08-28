package com.fundtransfer.application.service;

import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
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
import com.fundtransfer.application.model.CustomerAccount;
import com.fundtransfer.application.model.CustomerBeneficaries;
import com.fundtransfer.application.repository.AccountTransactionsRepository;
import com.fundtransfer.application.repository.CustomerAccountRepository;
import com.fundtransfer.application.repository.CustomerBeneficariesRepository;
import com.fundtransfer.application.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFundTransferService {


	@InjectMocks
	FundTransferService fundTransferService;

	@Mock
	CustomerRepository custRepo;

	@Mock
	CustomerAccountRepository accRepo;

	@Mock
	CustomerBeneficariesRepository beneficiaryRepo;

	@Mock
	AccountTransactionsRepository transRepo;



	static Customer customer;
	static CustomerAccount account;
	static CustomerCreationDTO customerCreationDTO;
	static FundTransferDto fundTranferDto;
	static BeneficiaryDTO dto;
	static CustomerBeneficaries benificary;
	static CredentialsDTO credentials;
	

	@BeforeAll
	public static void setUp() {
		
		credentials = new CredentialsDTO();
		credentials.setUserName("Sonali");
		credentials.setPassword("Sri123");
		
		// mock customer details as in database
		customer = new Customer();
		customer.setCustomerId(1l);
		customer.setUserName("AI0326");

		// mock account details as in database
		account = new CustomerAccount();
		account.setAccountId(1l);
		account.setAvailableBalance(5500);
		account.setAccountNo(123456l);

		// mock benificary details as in database
		benificary = new CustomerBeneficaries();
		//benificary.setBeneficiaryAccountNo(4567);
		benificary.setCustomer(customer);
		benificary.setTransferLimit(6000);

		// set values for accountopening dto
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
	@DisplayName("CustomerRegistration :: Postive Scenario")
	@Order(1)
	public void testCustomerRegistrationPostive() {
		// Event
		
		  Mockito.when(custRepo.save(Mockito.any(Customer.class))).thenAnswer(i -> 
		  {
			  Customer cust = i.getArgument(0); 
			  cust.setCustomerId(1l);
			  return cust; 
		  });
			
	      Mockito.when(accRepo.save(Mockito.any(CustomerAccount.class))).thenAnswer(i-> 
	      {
	    	  CustomerAccount acct = i.getArgument(0); 
	    	  acct.setAccountId(0l);
			  return  acct; 
		  });
	     ResponseEntity<String> result = fundTransferService.saveCustomerRegisterationDetails(customerCreationDTO);
	
	     assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	
	@Test
	@DisplayName("CustomerRegistration :: Negative Scenario")
	@Order(2)
	public void testCustomerRegistrationNegative() {
		// context
		Mockito.when(custRepo.findByUserName("AI0329")).thenThrow(UserNameAlreadyExistsException.class);
		// Event and outcome
		assertThrows(UserNameAlreadyExistsException.class,()-> fundTransferService.saveCustomerRegisterationDetails(customerCreationDTO));
	}

	@Test
	@DisplayName("Check Customer Login Credentials :: Postive Scenario")
	@Order(3)
	public void testAuthenticateCustomerPostive() 
	{
		Mockito.when(custRepo.findByUserNameAndPassword("Sonali", "Sri123")).thenReturn(customer);
		// Event
		ResponseEntity<String> result = fundTransferService.checkCustomerCredential(credentials);
		// out come
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	
	@Test
	@DisplayName("Check Customer Login Credentials:: Negative Scenrio")
	@Order(4)
	public void testAuthenticateNegative()  {
		// context
		Mockito.when(custRepo.findByUserNameAndPassword("Sonali", "Sri123")).thenReturn(null);
		// Event and outcome
		assertThrows(InvalidCredentialsException.class, ()->fundTransferService.checkCustomerCredential(credentials));
	}

	@Test
	@DisplayName("Save Customer Beneficiary   :: Postive Scenario")
	@Order(5)
	public void testSaveBenificaryPositive()
	{
		//context
		Mockito.when(custRepo.findByUserName(customer.getUserName())).thenReturn(customer);
		//Event and  out come
		assertEquals(HttpStatus.OK, fundTransferService.saveCustomerBeneficiary(dto, "AI0326").getStatusCode());
	}
	@Test
	@DisplayName("Save Customer Beneficiary   :: Negative Scenario")
	@Order(6)
	public void testSaveBenificaryNegative() throws ResourceNotFoundException {
		Mockito.when(custRepo.findByUserName("AI0326")).thenReturn(null);
		//Event and  out come
		assertThrows(ResourceNotFoundException.class, ()->fundTransferService.saveCustomerBeneficiary(dto, "AI0326"));
	}

	@Test
	@DisplayName("Test Customer FundTransfer :: Postive Scenario")
	@Order(7)
	public void testFundTransfer() 
	{
		// context
		Mockito.when(custRepo.findByUserName("AI0326")).thenReturn(customer);		
		Mockito.when(accRepo.findByAccountNoAndCustomerCustomerId(Long.valueOf(123456), 1l)).thenReturn(account);
		Mockito.when(beneficiaryRepo.findByBeneficiaryAccountNoAndCustomerCustomerId(
				Long.valueOf(4567), 1l)).thenReturn(benificary);
		
        assertEquals(fundTransferService.checkBeneficaryTranferLimit(Double.valueOf(fundTranferDto.getTransferAmount()), benificary.getTransferLimit(),benificary),true);
		 
        assertEquals(fundTransferService.isFundTranferAllowed(Double.valueOf(fundTranferDto.getTransferAmount()), account.getAvailableBalance(),account),true);
		 
		//Event and  out come
		assertEquals(HttpStatus.OK, fundTransferService.accountFundTransfer(fundTranferDto, "AI0326").getStatusCode());
	}
	
	@Test
	@DisplayName("Test Customer FundTransfer :: Negative Scenario")
	@Order(8)
	public void testFundTransferNegative() 
	{
		// context
		 assumeFalse(fundTransferService.checkBeneficaryTranferLimit(Double.valueOf(fundTranferDto.getTransferAmount()), benificary.getTransferLimit(),benificary));
		 
		 assumeFalse(fundTransferService.isFundTranferAllowed(Double.valueOf(fundTranferDto.getTransferAmount()), account.getAvailableBalance(),account));
		 
		assertThrows(ResourceNotFoundException.class, ()->fundTransferService.accountFundTransfer(fundTranferDto, "AI0326"));
		
	}
}
