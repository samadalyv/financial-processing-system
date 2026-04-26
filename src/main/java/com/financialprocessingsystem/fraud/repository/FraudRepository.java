package com.financialprocessingsystem.fraud.repository;

import com.financialprocessingsystem.fraud.model.Fraud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudRepository extends JpaRepository<Fraud, Long> {
}
