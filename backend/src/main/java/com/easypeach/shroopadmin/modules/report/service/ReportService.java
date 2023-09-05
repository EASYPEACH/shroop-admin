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
import com.easypeach.shroopadmin.modules.report.dto.response.MediateResponse;
import com.easypeach.shroopadmin.modules.report.dto.response.PageMediateResponse;
import com.easypeach.shroopadmin.modules.report.dto.response.PageReportResponse;
import com.easypeach.shroopadmin.modules.report.dto.response.ReportImgResponse;
import com.easypeach.shroopadmin.modules.report.dto.response.ReportResponse;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReportService {

	private final ReportRepository reportRepository;

	private final ReportImgService reportImgService;

	public PageReportResponse searchFindAll(final SearchReportRequest searchReportRequest, final Pageable pageable) {

		String searchWord = searchReportRequest.getSearchWord().isEmpty() ? "" : searchReportRequest.getSearchWord();

		List<Report> reportList = reportRepository.reportSearchFindAllFetchJoin(searchWord, pageable);

		List<ReportResponse> list = reportList.stream().map(ReportResponse::new).collect(Collectors.toList());
		int totalDataCount = reportRepository.reportSearchFindAllFetchJoinPageCount(searchWord);

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
		Report report = reportRepository.getByIdFetchJoin(reportId);
		report.updateStatus(reportStatus);

	}

	public PageMediateResponse mediateSearchFindAll(final SearchReportRequest searchReportRequest,
		final Pageable pageable) {
		String searchWord = searchReportRequest.getSearchWord().isEmpty() ? "" : searchReportRequest.getSearchWord();

		List<Report> reportList = reportRepository.mediateSearchFindAllFetchJoin(searchWord, pageable);

		List<MediateResponse> list = reportList.stream().map(MediateResponse::new).collect(Collectors.toList());
		int totalDataCount = reportRepository.mediateSearchFindAllFetchJoinPageCount(searchWord);

		PageMediateResponse pageMediateResponse = new PageMediateResponse(totalDataCount, list);

		return pageMediateResponse;
	}

	public MediateResponse mediateFindById(final Long reportId) {

		Report report = reportRepository.getByIdFetchJoin(reportId);
		List<ReportImgResponse> imgList = reportImgService.findByReportId(reportId);

		MediateResponse mediateResponse = new MediateResponse(report, imgList);

		return mediateResponse;

	}

}

