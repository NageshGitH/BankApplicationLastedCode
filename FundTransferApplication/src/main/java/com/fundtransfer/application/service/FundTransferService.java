package com.fundtransfer.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fundtransfer.application.DTO.BeneficiaryDTO;
import com.fundtransfer.application.DTO.CredentialsDTO;
import com.fundtransfer.application.DTO.CustomerCreationDTO;
import com.fundtransfer.application.DTO.FundTransferDto;
import com.fundtransfer.application.exception.BankOperationsException;
import com.fundtransfer.application.exception.BeneficiaryAccountNoAlreadyExistsException;
import com.fundtransfer.application.exception.InSufficientFundException;
import com.fundtransfer.application.exception.InvalidCredentialsException;
import com.fundtransfer.application.exception.ResourceNotFoundException;
import com.fundtransfer.application.exception.TransactionFailedException;
import com.fundtransfer.application.exception.TransferLimitException;
import com.fundtransfer.application.exception.UserNameAlreadyExistsException;
import com.fundtransfer.application.model.AccountTransactions;
import com.fundtransfer.application.model.Customer;
import com.fundtransfer.application.model.CustomerAccount;
import com.fundtransfer.application.model.CustomerBeneficaries;
import com.fundtransfer.application.repository.AccountTransactionsRepository;
import com.fundtransfer.application.repository.CustomerAccountRepository;
import com.fundtransfer.application.repository.CustomerBeneficariesRepository;
import com.fundtransfer.application.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FundTransferService 
{

	private static final Logger logger = LoggerFactory.getLogger(FundTransferService.class);
	@Autowired
	CustomerRepository custRepo;
	@Autowired
	CustomerAccountRepository accountRepo;
	@Autowired
	CustomerBeneficariesRepository beneficiaryRepo;
	@Autowired
	AccountTransactionsRepository transRepo;
	
	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> saveCustomerRegisterationDetails(CustomerCreationDTO request)
	 {
		logger.info("inside saveCustomerDetails  method");
		
		logger.info("Checking for if User Name exists.");
		if (Optional.ofNullable(custRepo.findByUserName(request.getUserName())).isPresent())
			throw new UserNameAlreadyExistsException("User Name already exists.Please try with another user name");
		
		logger.info("Validation customer pancard no,aadhaar No and email id");
		Optional<String> result = custRepo.validateCustomer(request.getPanCardNo(),request.getAadhaarCardNo(),
				request.getEmailId());
		
		if(result.isPresent())
		{
			StringBuffer strMsg = new StringBuffer();
			Arrays.asList(result.get().split(",")).stream().forEach(prop->
			{
				    System.out.println("prop::"+prop);
				    if(prop.equalsIgnoreCase(request.getPanCardNo()))				
						strMsg.append("Pan Card No ::  "+prop+",");
					else if(prop.equalsIgnoreCase(request.getAadhaarCardNo()))				
						strMsg.append("Aadhaar Card No ::  "+prop+" , ");
					else if(prop.equalsIgnoreCase(request.getEmailId()))				
						strMsg.append("Email Id ::  "+prop+" ");
									
			});
			if(strMsg.length() > 0)
				throw new BankOperationsException(strMsg.toString()+" registered with another customer.Please try with another!!!!");
		}
		
		Customer cust = new Customer();
		cust.setCustomerName(request.getCustomerName());
		cust.setUserName(request.getUserName());
		cust.setAddress(request.getAddress());
		cust.setAadhaarCardNo(request.getAadhaarCardNo());
		cust.setPanCardNo(request.getPanCardNo());
		cust.setMobileNo(Long.valueOf(request.getMobileNo()));
		cust.setCreationDate(LocalDate.now());
		cust.setDateOfBirth(java.time.LocalDate.parse(request.getDateOfBirth()));
		cust.setEmailId(request.getEmailId());
		cust.setGender(request.getGender());
		cust.setPassword(passwordGenerator());
		custRepo.save(cust);
		
		CustomerAccount custAccount = new CustomerAccount();
		custAccount.setAccountNo(numbGen());
		custAccount.setAccountType(request.getAccountType());
		custAccount.setAvailableBalance(Double.valueOf(request.getOpeningDeposit()));
		custAccount.setBankName(request.getBankName());
		custAccount.setBranchName(request.getBranchName());
		custAccount.setCustomer(cust);
		custAccount.setIfsCode(request.getIfsCode());
		accountRepo.save(custAccount);
		
		StringBuffer strMsg = new StringBuffer();
		strMsg.append("Account Opened for Customer :: ").append(cust.getCustomerName());
		strMsg.append("");
		strMsg.append("  Use credentials for Login: ").append("User Name:: " + cust.getUserName())
				.append(" Password:: " + cust.getPassword());
		
		return new ResponseEntity<>(strMsg.toString(), HttpStatus.OK);
	}
	
	public ResponseEntity<String> saveCustomerBeneficiary(BeneficiaryDTO dto, String userName) 
	{
		logger.info("inside saveBeneficiary  method");
		
		logger.info("Checking for if customer exists.");
		
		Customer customer = Optional.ofNullable(custRepo.findByUserName(userName))
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer Name", userName));
		
		Optional<CustomerBeneficaries> beneficary = beneficiaryRepo.findByBeneficiaryAccountNoAndCustomerUserName(Long.valueOf(dto.getBeneficiaryAccountNo()), userName);
		
		if(beneficary.isPresent())
		{
		  throw new BeneficiaryAccountNoAlreadyExistsException("Beneficiary Account Number already associated with Customer");
		}
		
		CustomerBeneficaries beneficiaries = new CustomerBeneficaries();
		beneficiaries.setBeneficiaryAccountNo(Long.valueOf(dto.getBeneficiaryAccountNo()));
		beneficiaries.setBeneficiaryName(dto.getBeneficiaryName());
		beneficiaries.setCustomer(customer);
		beneficiaries.setIfsCode(dto.getIfsCode());
		beneficiaries.setTransferLimit(Double.valueOf(dto.getTransferLimit()));
		beneficiaryRepo.save(beneficiaries);
		
		return new ResponseEntity<>("Successfully added Beneficiary", HttpStatus.OK);
	}

	public ResponseEntity<String> checkCustomerCredential(CredentialsDTO credentials) {
		logger.info("inside checkLoginCredential method");
		logger.info("Checking for if customer credentials");
		
		if (Optional.ofNullable(
				custRepo.findByUserNameAndPassword(credentials.getUserName(), credentials.getPassword()))
				.isEmpty()) {
			throw new InvalidCredentialsException("Authetication Failed! Please provide valid User Name or Password ");
		}
		return new ResponseEntity<>("Authetication Success", HttpStatus.OK);
	}
	
	
	@Transactional(rollbackFor = TransactionFailedException.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<String> accountFundTransfer(FundTransferDto fundTransDto, String userName)
	{
		// Check is the customer exists, if does not exists return error message
				// else continue 
		Customer customer = Optional.ofNullable(custRepo.findByUserName(userName))
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer Name", userName));
		// Check is the account number exists, if does not exists return error message
		// else continue .
		CustomerAccount fromAccDetails = Optional
				.ofNullable(accountRepo.findByAccountNoAndCustomerCustomerId(
						Long.valueOf(fundTransDto.getFromAccountNo()), customer.getCustomerId()))
				.orElseThrow(() -> new ResourceNotFoundException("Account Details", "Account Number",
						fundTransDto.getFromAccountNo()));
		CustomerBeneficaries toAccDetails = Optional.ofNullable(beneficiaryRepo.findByBeneficiaryAccountNoAndCustomerCustomerId(
			 							Long.valueOf(fundTransDto.getToAccountNo()), customer.getCustomerId())).orElseThrow(() -> new
			 										ResourceNotFoundException("Customers Beneficiary Account Details  ", "Account Number", fundTransDto.getToAccountNo()));
		isFundTranferAllowed(Double.valueOf(fundTransDto.getTransferAmount()), fromAccDetails.getAvailableBalance(),fromAccDetails);
		checkBeneficaryTranferLimit(Double.valueOf(fundTransDto.getTransferAmount()),toAccDetails.getTransferLimit(),toAccDetails);
		
		AccountTransactions accTrans = new AccountTransactions();
		Double avaialbleBalance = fromAccDetails.getAvailableBalance()-Double.valueOf(fundTransDto.getTransferAmount());
		fromAccDetails.setAvailableBalance(avaialbleBalance);
		accTrans.setAccount(fromAccDetails);
		accTrans.setFromAccount(Long.valueOf(fundTransDto.getFromAccountNo()));
		accTrans.setToAccount(Long.valueOf(fundTransDto.getToAccountNo()));
		accTrans.setTransactionTime(LocalDateTime.now());
		accTrans.setTransactionType("DEBIT");
		accTrans.setTransferAmount(Double.valueOf(fundTransDto.getTransferAmount()));
		transRepo.save(accTrans);
		return new ResponseEntity<>("Transaction Done Successfully ", HttpStatus.OK);
	}
	
	
	 private long numbGen() 
	  {
		  while (true) {
		        long numb = (long)(Math.random() * 100000000 * 100000000); // had to use this as int's are to small for a 13 digit number.
		        if (String.valueOf(numb).length() == 12)
		            return numb;
		    }
	  
	  }
	 
		private String passwordGenerator()
		{
			String password = UUID.randomUUID().toString().split("-")[1];
			return password;
		}
	
	 public  boolean isFundTranferAllowed(double transferAmt,double avaialableAmount,CustomerAccount fromAccDetails) throws InSufficientFundException
		{
			if (transferAmt > avaialableAmount) {
				throw new InSufficientFundException(
						"InSufficent balance for the account number::" + fromAccDetails.getAccountNo());
			}else
			{
				  return true;
			}
		}
	 
	 public boolean checkBeneficaryTranferLimit(double transferAmt,double avaialableAmount,CustomerBeneficaries toAccDetails) 
		{
			if (transferAmt > avaialableAmount) 
			{
				throw new TransferLimitException("Transfer Limit Excessed for this benificary account number::"
						+ toAccDetails.getBeneficiaryAccountNo()+" Maximum tranfer Limit: "+toAccDetails.getTransferLimit());
			}else
			{
				return true;
			}
		}
	 
	 
}
