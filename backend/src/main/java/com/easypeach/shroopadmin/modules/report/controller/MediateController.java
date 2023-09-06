package com.easypeach.shroopadmin.modules.report.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroopadmin.modules.global.request.SearchRequest;
import com.easypeach.shroopadmin.modules.report.dto.response.MediateResponse;
import com.easypeach.shroopadmin.modules.report.dto.response.PageMediateResponse;
import com.easypeach.shroopadmin.modules.report.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/mediates")
@RestController
@RequiredArgsConstructor
public class MediateController {

	private final ReportService reportService;

	@GetMapping
	public ResponseEntity<PageMediateResponse> mediateSearchFindAll(
		final SearchRequest searchRequest,
		final @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable
	) {
		PageMediateResponse pageMediateResponse = reportService.mediateSearchFindAll(searchRequest, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(pageMediateResponse);

	}

	@GetMapping("/{reportId}")
	public ResponseEntity<MediateResponse> mediateFindById(final @PathVariable Long reportId) {

		MediateResponse response = reportService.mediateFindById(reportId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
