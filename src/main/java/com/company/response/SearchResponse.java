package com.company.response;

import lombok.Data;

@Data
public class SearchResponse {

	private String name;
	private Long mobNo;
	private String email;
	private Character gender;
	private Long ssn;

}
