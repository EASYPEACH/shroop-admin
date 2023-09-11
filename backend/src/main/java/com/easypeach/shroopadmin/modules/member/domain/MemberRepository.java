package com.easypeach.shroopadmin.modules.member.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.easypeach.shroopadmin.modules.member.exception.MemberNotExistException;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginId(String loginId);

	default Member getByLoginId(final String loginId) {
		return findByLoginId(loginId)
			.orElseThrow(MemberNotExistException::new);
	}

	@Query("select m from Member m "
		+ "where (m.loginId like %:searchWord% or m.nickname like %:searchWord% or m.phoneNumber like %:searchWord%)")
	Page<Member> findAll(final String searchWord, final Pageable pageable);
}
