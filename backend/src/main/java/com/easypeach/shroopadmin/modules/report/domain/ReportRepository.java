package com.easypeach.shroopadmin.modules.report.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypeach.shroopadmin.modules.report.exception.ReportNotExistException;

public interface ReportRepository extends JpaRepository<Report, Long> {

	@Query("select r from Report r "
		+ "left join fetch r.product p "
		+ "left join fetch r.member m "
		+ "left join fetch p.category "
		+ "where r.isMediate = false")
	List<Report> findAllFetchJoin(Pageable pageable);

	@Query("select r from Report r "
		+ "left join fetch r.product p "
		+ "left join fetch r.member m "
		+ "left join fetch p.category c "
		+ "where r.isMediate = false "
		+ "and (r.title like %:searchWord% or p.title like %:searchWord% or m.loginId like %:searchWord% or c.name like %:searchWord%)")
	List<Report> searchFindAllFetchJoin(String searchWord, Pageable pageable);

	@Query("select count(r) from Report r "
		+ "where r.isMediate = false "
		+ "and (r.title like %:searchWord% or r.product.title like %:searchWord% or r.member.loginId like %:searchWord% or r.product.category.name like %:searchWord%)")
	int searchFindAllFetchJoinPageCount(String searchWord);

	@Query("select r from Report r "
		+ "left join fetch r.product p "
		+ "left join fetch r.member m "
		+ "left join fetch p.category "
		+ "where r.isMediate = false "
		+ "and r.id = :reportId"
	)
	Optional<Report> findByIdFetchJoin(Long reportId);

	default Report getById(final Long reportId) {
		return findById(reportId).orElseThrow(() -> new ReportNotExistException("신고가 존재하지 않습니다."));
	}

	default Report getByIdFetchJoin(final Long reportId) {
		return findByIdFetchJoin(reportId).orElseThrow(() -> new ReportNotExistException("신고가 존재하지 않습니다."));
	}
}
