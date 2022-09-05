package com.company.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.request.SearchRequest;
import com.company.response.SearchResponse;
import com.company.service.EligibiltyServices;

@RestController
public class ReportRestController {

	@Autowired
	private EligibiltyServices eligService;

	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlanNames() {
		List<String> planNames = eligService.getUniquePlanNames();

		return new ResponseEntity<>(planNames, HttpStatus.OK);
	}

	@GetMapping("/statuses")
	public ResponseEntity<List<String>> getPlanStatuses() {
		List<String> statuses = eligService.getUniquePlanStatuses();

		return new ResponseEntity<>(statuses, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request) {
		List<SearchResponse> response = eligService.search(request);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/downloadExcel")
	public void getExcelReport(HttpServletResponse response) throws Exception {

		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = "attachmnet; filename=data.xls";

		response.setHeader(headerKey, headerValue);

		eligService.generateExcel(response);
	}

	@GetMapping("/downloadPDF")
	public void getPDFReport(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=EligibiltyReports_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		eligService.generatePDF(response);
	}
}
