package com.company.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.company.entity.EligibiltyReport;

@Repository
public interface EligibilityReportsRepo extends JpaRepository<EligibiltyReport, Integer> {

	@Query("Select distinct(planName) from EligibiltyReport")
	public List<String> findUniquePlanNames();

	@Query("Select distinct(planStatus) from EligibiltyReport")
	public List<String> findUniquePlanStatus();

}
