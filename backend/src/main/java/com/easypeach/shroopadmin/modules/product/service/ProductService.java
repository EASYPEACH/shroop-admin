package com.easypeach.shroopadmin.modules.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroopadmin.modules.likes.domain.LikesRepository;
import com.easypeach.shroopadmin.modules.member.domain.Member;
import com.easypeach.shroopadmin.modules.member.domain.MemberRepository;
import com.easypeach.shroopadmin.modules.product.domain.Category;
import com.easypeach.shroopadmin.modules.product.domain.Product;
import com.easypeach.shroopadmin.modules.product.dto.request.ProductRequest;
import com.easypeach.shroopadmin.modules.product.dto.response.MemberResonse;
import com.easypeach.shroopadmin.modules.product.dto.response.ProductImgResponse;
import com.easypeach.shroopadmin.modules.product.dto.response.ProductOneImgResponse;
import com.easypeach.shroopadmin.modules.product.dto.response.ProductResponse;
import com.easypeach.shroopadmin.modules.product.exception.ProductException;
import com.easypeach.shroopadmin.modules.product.respository.CategoryRepository;
import com.easypeach.shroopadmin.modules.product.respository.ProductImgRepository;
import com.easypeach.shroopadmin.modules.product.respository.ProductRepository;
import com.easypeach.shroopadmin.modules.transaction.domain.Transaction;
import com.easypeach.shroopadmin.modules.transaction.domain.TransactionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final ProductImgRepository productImgRepository;
	private final TransactionRepository transactionRepository;
	private final LikesRepository likeRepository;

	public ProductResponse getProductInfo(final Long productId) {
		Product product = productRepository.getById(productId);
		product.getSeller().getId();
		product.getCategory().getId();
		ProductResponse productResponse = setProductResponse(product);
		return productResponse;
	}

	public List<Product> findByCategoryId(final Long categoryId) {
		List<Product> productList = productRepository.findByCategoryId(categoryId);

		return productList;
	}

	@Transactional
	public Long saveProduct(final Long memberId, final ProductRequest productRequest) {
		Member seller = memberRepository.getById(memberId);
		Category category = categoryRepository.getById(productRequest.getCategoryId());
		Product product = productRepository.save(Product.createProduct(seller, productRequest, category));
		return product.getId();
	}

	@Transactional
	public Long updateProduct(final Long memberId, final Long productId, final ProductRequest productRequest) {
		Product product = productRepository.getById(productId);
		Member loginMember = memberRepository.getById(memberId);
		Member productOwnerMember = memberRepository.getById(product.getSeller().getId());

		if (loginMember != productOwnerMember) {
			throw ProductException.notAuthorizationToUpdate();
		}

		Category category = categoryRepository.getById(productRequest.getCategoryId());
		product.updateProduct(productRequest, category);

		return product.getId();
	}

	@Transactional
	public void deleteProduct(final Long productId) {
		Product product = productRepository.getById(productId);
		likeRepository.deleteAllByProduct(product);
		if (product.getTransaction() != null) {
			throw ProductException.notStatusDelete(product.getTransaction().getStatus());
		}

		productRepository.delete(product);
	}

	public ProductResponse setProductResponse(final Product product) {
		ProductResponse productResponse = new ProductResponse(product);
		MemberResonse seller = new MemberResonse(product.getSeller());
		product.getCategory().getId();
		List<ProductImgResponse> productImgList = productImgRepository.findAllByProduct(product)
			.stream()
			.map(ProductImgResponse::new)
			.collect(Collectors.toList());

		productResponse.setProductImgList(productImgList);
		productResponse.setSeller(seller);
		Transaction transaction = transactionRepository.findByProduct(product);
		if (transaction != null) {
			productResponse.setTransaction(transaction.getStatus());
		}

		return productResponse;
	}

	public Page<ProductOneImgResponse>  searchProduct(final String searchWord, final String transactionStatus, final Pageable pageable) {

		Page<ProductOneImgResponse> productPage;

		productPage = productRepository.searchProduct(searchWord, transactionStatus,pageable);

		int pageCount = productPage.getTotalPages();

		List<ProductOneImgResponse> list = productPage.stream().collect(Collectors.toList());

		return productPage;
	}

}
