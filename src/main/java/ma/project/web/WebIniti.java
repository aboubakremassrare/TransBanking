package ma.project.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import ma.project.JeeSpringDataJpaThymleafBanqueApplication;

public class WebIniti extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(JeeSpringDataJpaThymleafBanqueApplication.class);
	}

}
