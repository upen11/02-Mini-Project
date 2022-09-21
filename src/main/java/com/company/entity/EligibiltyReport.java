package com.company.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "ELIGIBILITY_REPORT")
public class EligibiltyReport {

	@Id
	private Integer eliId;
	private String name;
	private Long mobNo;
	private String email;
	private Character gender;
	private Long ssn;
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;
	@Column(insertable = false)
	@UpdateTimestamp
	private LocalDate updatedDate;
	private String createdBy;
	private String updatedBy;

}
