package com.example.jpa.config;

import com.example.jpa.common.guard.CurrentUserArgumentResolver;
import com.example.jpa.common.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new CommonInterceptor())
				.addPathPatterns("/api/*");
	}

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
      argumentResolvers.add(new CurrentUserArgumentResolver());
  }
}
