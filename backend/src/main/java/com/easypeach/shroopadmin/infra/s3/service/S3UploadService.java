package com.easypeach.shroopadmin.infra.s3.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sun.jdi.InternalException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String saveFile(final MultipartFile multipartFile) {
		try {
			String originalFilename = multipartFile.getOriginalFilename();
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(multipartFile.getSize());
			metadata.setContentType(multipartFile.getContentType());
			// 저장
			log.info("image save region = {}",amazonS3Client.getRegion());
			amazonS3Client.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
			return amazonS3Client.getUrl(bucket, originalFilename).toString();
		}catch (IOException e){
			throw new InternalException("서버에 문제가 생겼습니다");
		}

	}

	public ResponseEntity<UrlResource> downloadImage(final String originalFilename) {

		UrlResource urlResource = new UrlResource(amazonS3Client.getUrl(bucket, originalFilename));

		String contentDisposition = "attachment; filename=\"" + originalFilename + "\"";

		// header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
			.body(urlResource);

	}

	public void deleteImage(final String originalFilename) {
		amazonS3Client.deleteObject(bucket, originalFilename);
	}
}