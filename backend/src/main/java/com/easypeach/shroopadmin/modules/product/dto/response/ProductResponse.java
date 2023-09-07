package com.easypeach.shroopadmin.modules.product.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.easypeach.shroopadmin.modules.product.domain.Category;
import com.easypeach.shroopadmin.modules.product.domain.Product;
import com.easypeach.shroopadmin.modules.product.domain.ProductGrade;
import com.easypeach.shroopadmin.modules.transaction.domain.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

	private Long id;
	private MemberResonse seller;
	private TransactionStatus transactionStatus;
	private String title;
	private Category category;
	private ProductGrade productGrade;
	private List<ProductImgResponse> productImgList;
	private String brand;
	private Long price;
	private Boolean isCheckedDeliveryFee;
	private String content;
	private Boolean isDefect;
	private String saleReason;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate purchaseDate;

	private LocalDateTime createDate;

	public ProductResponse(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.category = product.getCategory();
		this.productGrade = product.getProductGrade();
		this.brand = product.getBrand();
		this.price = product.getPrice();
		this.isCheckedDeliveryFee = product.getIsCheckedDeliveryFee();
		this.content = product.getContent();
		this.purchaseDate = product.getPurchaseDate();
		this.isDefect = product.getIsDefect();
		this.saleReason = product.getSaleReason();
		this.createDate = product.getCreateDate();
	}

	public void setProductImgList(java.util.List<ProductImgResponse> productImgList) {
		this.productImgList = productImgList;
	}

	public void setSeller(MemberResonse seller) {
		this.seller = seller;
	}

	public void setTransaction(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}


}
