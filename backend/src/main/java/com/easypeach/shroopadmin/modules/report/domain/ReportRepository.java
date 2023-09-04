package com.easypeach.shroopadmin.modules.report.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long> {

	@Query("select r from Report r "
		+ "left join fetch r.product p "
		+ "left join fetch r.member m "
		+ "left join fetch p.category "
		+ "where r.isMediate = false")
	List<Report> findAllFetchJoin(Pageable pageable);
}
