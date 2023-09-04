package com.easypeach.shroopadmin.modules.report.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.easypeach.shroopadmin.modules.report.domain.Report;
import com.easypeach.shroopadmin.modules.report.domain.ReportRepository;
import com.easypeach.shroopadmin.modules.report.dto.response.ReportResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportService {

	private final ReportRepository reportRepository;

	public List<ReportResponse> findAll(Pageable pageable) {

		List<Report> list = reportRepository.findAllFetchJoin(pageable);

		return list.stream().map(ReportResponse::new).collect(Collectors.toList());
	}

}

