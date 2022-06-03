package yerp.common.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import egovframework.rte.fdl.cmmn.trace.handler.TraceHandler;
import egovframework.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import egovframework.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import yerp.common.interceptor.LoginCheckInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private Environment environment;

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new websquare.http.DefaultRequestDispatcher(), "*.wq");
	}

	@Bean
	public LeaveaTrace leaveaTrace() {
		LeaveaTrace trace = new LeaveaTrace();
		TraceHandlerService[] manager = { new DefaultTraceHandleManager() };
		TraceHandler[] handler = { new DefaultTraceHandler() };
		manager[0].setReqExpMatcher(new AntPathMatcher());
		manager[0].setHandlers(handler);

		trace.setTraceHandlerServices(manager);
		return trace;
	}

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		converter.setSupportedMediaTypes(supportedMediaTypes);
		return converter;
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		converter.setSupportedMediaTypes(supportedMediaTypes);
		return converter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LoginCheckInterceptor(environment.getProperty("server.domain"))).addPathPatterns("/**").excludePathPatterns(
//				// "/websquare/**",
//				"/assets/**", "/page/ssoLogin", "/page/ssoLoginPopup", "/account/ssoLogin", "/account/tokenValid");
		
		// interceptor 제외 url 추가 (페이지 직접 호출 및 NON_SSO_LOGIN)
		registry.addInterceptor(new LoginCheckInterceptor(environment.getProperty("server.domain"))).addPathPatterns("/**").excludePathPatterns(
				// "/websquare/**",
				// "/GATE/**",
				"/popup/**","/assets/**", "/page/ssoLogin", "/page/ssoLoginPopup", "/account/ssoLogin", "/account/tokenValid", "/websquare/**"
				//, "/udc/**", "/inc/**"
				, "/account/sessionInfo", "/menu/**", "/account/logout"
				//, "/page/nonSsoLogin"
				, "/page/login", "/page/logout"
				//, "/account/nonSsoLogin"
				);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new CommonHandlerMethodArgumentResolver());
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sessionManagerCustomizer() {
	        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	        return server -> server.addContextCustomizers(context -> {
	        	context.setSessionTimeout(120 * 60);
	        	context.setCookieProcessor(new LegacyCookieProcessor());
	        });
	}
	
}