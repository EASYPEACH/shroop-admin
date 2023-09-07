package com.easypeach.shroopadmin.infra.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.easypeach.shroopadmin.modules.auth.presentation.AuthenticationPrincipalArgumentResolver;
import com.easypeach.shroopadmin.modules.auth.service.JwtInterceptor;
import com.easypeach.shroopadmin.modules.auth.service.JwtProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final JwtProvider jwtProvider;
	private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authenticationPrincipalArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new JwtInterceptor(jwtProvider))
			.addPathPatterns("/api/**")
			.excludePathPatterns("/api/auth/login","/api/auth/checkLogin");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("*")
			.allowCredentials(true)
			.maxAge(3000)
			.allowedOriginPatterns("*");
	}
}
