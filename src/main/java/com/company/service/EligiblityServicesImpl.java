package com.company.service;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.company.entity.EligibiltyReport;
import com.company.repo.EligibilityReportsRepo;
import com.company.request.SearchRequest;
import com.company.response.SearchResponse;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class EligiblityServicesImpl implements EligibiltyServices {

	@Autowired
	EligibilityReportsRepo eligibiltyReportRepo;

	@Override
	public List<String> getUniquePlanNames() {

//		List<EligibiltyReport> records = eligibiltyReportRepo.findAll();
//		if we use for loop here then if there is 10 lakh data then loop will run that many times, so not recommended

		return eligibiltyReportRepo.findUniquePlanNames(); // custom query
	}

	@Override
	public List<String> getUniquePlanStatuses() {
		return eligibiltyReportRepo.findUniquePlanStatus();
	}

	@Override
	public List<SearchResponse> search(SearchRequest srchRequest) {

		List<SearchResponse> responseList = new ArrayList<>();

		EligibiltyReport eliReport = new EligibiltyReport();

		String planName = srchRequest.getPlanName();
		String planStatus = srchRequest.getPlanStatus();
		LocalDate planStartDate = srchRequest.getPlanStartDate();
		LocalDate planEndDate = srchRequest.getPlanEndDate();

		if (planName != null && !planName.equals("")) {
			eliReport.setPlanName(planName);
		}

		if (planStatus != null && !planStatus.equals("")) {
			eliReport.setPlanStatus(planStatus);
		}

		if (!(planStartDate != null)) {
			eliReport.setPlanStartDate(planStartDate);
		}

		if (!(planEndDate != null)) {
			eliReport.setPlanEndDate(planEndDate);
		}

		Example<EligibiltyReport> query = Example.of(eliReport);

		// which will make query?? find out later
		List<EligibiltyReport> reportList = eligibiltyReportRepo.findAll(query);

		for (EligibiltyReport col : reportList) {
			SearchResponse srchRes = new SearchResponse();
			BeanUtils.copyProperties(col, srchRes); // same properties will be copied. PS: name of both var should be
													// same in both class.

			responseList.add(srchRes);
		}

		return responseList;

	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {

		List<EligibiltyReport> entities = eligibiltyReportRepo.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);

		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Email");
		headerRow.createCell(2).setCellValue("Mobile");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");

		int i = 1;

		for (EligibiltyReport entity : entities) {

			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(entity.getMobNo());
			dataRow.createCell(3).setCellValue(String.valueOf(entity.getGender()));
			dataRow.createCell(4).setCellValue(entity.getSsn());

			i++;
		}

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

//		https://www.codejava.net/frameworks/spring-boot/pdf-export-example
	@Override
	public void generatePDF(HttpServletResponse response) throws Exception {

		List<EligibiltyReport> entities = eligibiltyReportRepo.findAll();

		Document document = new Document(PageSize.A4);

		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("Search Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(5); // 5 is no. Of columns
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f, 3.5f });
		table.setSpacingBefore(10);

//		document.add(table);   ////

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Name", font)); // cell Text
		table.addCell(cell);

		cell.setPhrase(new Phrase("E-mail", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Mob No", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);

		for (EligibiltyReport entity : entities) {
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobNo())); // expecting string as parameter
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getSsn()));
		}

		document.add(table);

		document.close();

	}

}
