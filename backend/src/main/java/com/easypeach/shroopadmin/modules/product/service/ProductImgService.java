package com.easypeach.shroopadmin.modules.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroopadmin.infra.s3.service.S3UploadService;
import com.easypeach.shroopadmin.modules.product.domain.Product;
import com.easypeach.shroopadmin.modules.product.domain.ProductImg;
import com.easypeach.shroopadmin.modules.product.exception.ProductImgLengthException;
import com.easypeach.shroopadmin.modules.product.respository.ProductImgRepository;
import com.easypeach.shroopadmin.modules.product.respository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductImgService {
	private final ProductImgRepository productImgRepository;
	private final ProductRepository productRepository;
	private final S3UploadService s3UploadService;

	@Transactional
	public void saveProductImg(final List<MultipartFile> productImgList,
		final List<MultipartFile> defectImgList, final Long productId, final boolean isDefect) {
		Product product = productRepository.getById(productId);
		try {
			List<ProductImg> imgList = insertImgList(productImgList, defectImgList, product, isDefect);
			productImgRepository.saveAll(imgList);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public void updateProductImgList(final List<MultipartFile> productImgList,
		final List<MultipartFile> defectImgList, final Long productId, final boolean isDefect) {
		Product product = productRepository.getById(productId);

		try {
			List<ProductImg> imgList = insertImgList(productImgList, defectImgList, product, isDefect);
			product.getProductImgList().clear();
			for (ProductImg img : imgList) {
				img.setProduct(product);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ProductImg> insertImgList(final List<MultipartFile> productImgList,
		final List<MultipartFile> defectImgList,
		final Product product, final boolean isDefect) throws IOException {
		List<ProductImg> imgList = new ArrayList<>();
		imgList.addAll(createImgList(productImgList, product, false));
		if (isDefect) {
			imgList.addAll(createImgList(defectImgList, product, true));
		}
		return imgList;
	}

	public List<ProductImg> createImgList(final List<MultipartFile> requestImgList,
		final Product product, final boolean isDefect) throws
		IOException {
		List<ProductImg> imgList = new ArrayList<>();
		for (MultipartFile multipartFile : requestImgList) {
			String uploadUrl = s3UploadService.saveFile(multipartFile);
			imgList.add(ProductImg.createProductImg(product, uploadUrl, isDefect));
		}
		return imgList;
	}

	public void checkImgLength(final List<MultipartFile> productImgList) {
		if (productImgList.size() < 2) {
			throw new ProductImgLengthException("사진은 2장이상 등록해주세요");
		}

	}
}
