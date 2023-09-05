package com.easypeach.shroopadmin.modules.report.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroopadmin.modules.global.response.BasicResponse;
import com.easypeach.shroopadmin.modules.report.dto.request.ReportStatusRequest;
import com.easypeach.shroopadmin.modules.report.dto.request.SearchReportRequest;
import com.easypeach.shroopadmin.modules.report.dto.response.PageReportResponse;
import com.easypeach.shroopadmin.modules.report.dto.response.ReportResponse;
import com.easypeach.shroopadmin.modules.report.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/reports")
@RestController
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@GetMapping
	public ResponseEntity<PageReportResponse> searchFindAll(
		final SearchReportRequest searchReportRequest,
		final @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
		PageReportResponse response = reportService.searchFindAll(searchReportRequest, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{reportId}")
	public ResponseEntity<ReportResponse> findById(final @PathVariable Long reportId) {
		ReportResponse response = reportService.findById(reportId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{reportId}")
	public ResponseEntity<BasicResponse> deleteById(final @PathVariable Long reportId) {
		reportService.deleteById(reportId);
		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("신고가 삭제되었습니다."));
	}

	@PatchMapping("/{reportId}")
	public ResponseEntity<BasicResponse> updateStatus(final @PathVariable Long reportId,
		final @RequestBody ReportStatusRequest reportStatusRequest) {
		reportService.updateStatus(reportId, reportStatusRequest.getReportStatus());
		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("신고 상태 변경이 완료되었습니다."));
	}

}
