package com.easypeach.shroopadmin.modules.likes.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.easypeach.shroopadmin.modules.member.domain.Member;
import com.easypeach.shroopadmin.modules.product.domain.Product;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	Long countLikesByProduct(Product product);

	List<Likes> findAllByMember(Member member);

	boolean existsLikesByMemberAndProduct(Member member, Product product);

	Optional<Likes> findByMemberAndProduct(Member member, Product product);

	void deleteAllByProduct(Product product);


}
