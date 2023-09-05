package com.easypeach.shroopadmin.modules.report.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroopadmin.modules.report.domain.Report;
import com.easypeach.shroopadmin.modules.report.domain.ReportRepository;
import com.easypeach.shroopadmin.modules.report.domain.ReportStatus;
import com.easypeach.shroopadmin.modules.report.dto.request.SearchReportRequest;
import com.easypeach.shroopadmin.modules.report.dto.response.PageReportResponse;
import com.easypeach.shroopadmin.modules.report.dto.response.ReportResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReportService {

	private final ReportRepository reportRepository;

	public PageReportResponse searchFindAll(final SearchReportRequest searchReportRequest, final Pageable pageable) {

		String searchWord = searchReportRequest.getSearchWord().isEmpty() ? "" : searchReportRequest.getSearchWord();

		List<Report> reportList = reportRepository.searchFindAllFetchJoin(searchWord, pageable);

		List<ReportResponse> list = reportList.stream().map(ReportResponse::new).collect(Collectors.toList());
		int totalDataCount = reportRepository.searchFindAllFetchJoinPageCount(searchWord);

		PageReportResponse pageReportResponse = new PageReportResponse(totalDataCount, list);

		return pageReportResponse;
	}

	public ReportResponse findById(final Long reportId) {
		Report report = reportRepository.getByIdFetchJoin(reportId);
		return new ReportResponse(report);
	}

	@Transactional
	public void deleteById(final Long reportId) {
		reportRepository.deleteById(reportId);
	}

	@Transactional
	public void updateStatus(final Long reportId, final ReportStatus reportStatus) {
		Report report = reportRepository.getById(reportId);
		report.updateStatus(reportStatus);

	}

}

