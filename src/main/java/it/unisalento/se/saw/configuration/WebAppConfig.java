package it.unisalento.se.saw.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Component
@ComponentScan(basePackages={"it.unisalento.se.saw"})
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter{
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST");
	}
	
	 public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	        configurer.favorPathExtension(false);
	        configurer.ignoreAcceptHeader(true);
	        configurer.defaultContentType(MediaType.APPLICATION_JSON);
	    }
	 
	 @Bean
	 public DeviceResolverHandlerInterceptor 
	         deviceResolverHandlerInterceptor() {
	     return new DeviceResolverHandlerInterceptor();
	 }

	 @Bean
	 public DeviceHandlerMethodArgumentResolver 
	         deviceHandlerMethodArgumentResolver() {
	     return new DeviceHandlerMethodArgumentResolver();
	 }

	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
	     registry.addInterceptor(deviceResolverHandlerInterceptor());
	 }

	 @Override
	 public void addArgumentResolvers(
	         List<HandlerMethodArgumentResolver> argumentResolvers) {
	     argumentResolvers.add(deviceHandlerMethodArgumentResolver());
	 }


}