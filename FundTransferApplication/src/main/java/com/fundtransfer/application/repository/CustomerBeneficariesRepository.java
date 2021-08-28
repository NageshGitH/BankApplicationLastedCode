package com.fundtransfer.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.application.model.CustomerBeneficaries;

public interface CustomerBeneficariesRepository extends JpaRepository<CustomerBeneficaries, Long>
{
	CustomerBeneficaries findByBeneficiaryAccountNoAndCustomerCustomerId(long accountNo,long customerId);
	
	Optional<CustomerBeneficaries> findByBeneficiaryAccountNoAndCustomerUserName(Long accountNo,String userName);
}
