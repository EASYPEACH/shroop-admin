package com.easypeach.shroopadmin.modules.likes.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.easypeach.shroopadmin.modules.member.domain.Member;
import com.easypeach.shroopadmin.modules.product.domain.Product;

@Table(name = "likes")
@Entity
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public static Likes createLike(Member member, Product product) {
		Likes like = new Likes();
		like.member = member;
		like.product = product;
		return like;
	}
}
