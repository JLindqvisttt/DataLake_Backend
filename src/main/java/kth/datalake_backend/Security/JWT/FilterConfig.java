package kth.datalake_backend.Security.JWT;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean jwtFilter() {
    FilterRegistrationBean filter= new FilterRegistrationBean();
    System.out.println("test");
    filter.setFilter(new AuthTokenFilter());

//        provide endpoints which needs to be restricted.
//        All Endpoints would be restricted if unspecified
    filter.addUrlPatterns("/api/auth/signIn");
    filter.addUrlPatterns("/api/auth/signUp");

    return filter;
  }
}
