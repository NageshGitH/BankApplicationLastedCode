package com.fundtransfer.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fundtransfer.application.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
Customer findByUserName(String userName);
	
	Customer findByCustomerId(long customerId);
	
	Customer findByUserNameAndPassword(String userName,String password);
	
	@Query("select c.panCardNo, c.aadhaarCardNo ,  c.emailId from Customer c where  c.panCardNo=:panNo or c.aadhaarCardNo=:aadhaarCardNo"
			+ " or c.emailId =:emailId")
	Optional<String> validateCustomer(String panNo,String aadhaarCardNo,String emailId);

}
