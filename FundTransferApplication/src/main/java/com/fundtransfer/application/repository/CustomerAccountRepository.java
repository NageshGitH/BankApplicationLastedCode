package com.fundtransfer.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.application.model.CustomerAccount;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

	Long findByAccountNo(long accountNo);
	CustomerAccount findByAccountNoAndCustomerCustomerId(long accountNo,long customerId);
}
