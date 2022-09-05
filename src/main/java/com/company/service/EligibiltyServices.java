package com.company.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.company.request.SearchRequest;
import com.company.response.SearchResponse;

public interface EligibiltyServices {

	public List<String> getUniquePlanNames();

	public List<String> getUniquePlanStatuses();

	public List<SearchResponse> search(SearchRequest request);

	public void generateExcel(HttpServletResponse response) throws Exception;

	public void generatePDF(HttpServletResponse response) throws Exception;

}
