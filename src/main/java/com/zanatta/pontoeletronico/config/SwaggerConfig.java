package com.zanatta.pontoeletronico.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.zanatta.pontoeletronico.security.utils.JwtTokenUtil;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Classe de configuração do Swagger
 * @author <a href="">Jean Luiz Zanatta</a>
 * @since 04/07/2019
 */
@Configuration
// @Profile("desenvolvimento")
@EnableSwagger2
public class SwaggerConfig {

	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private UserDetailsService userDetailsService;

	/**
	 * Bean de configuração de toda documentação da API.
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.zanatta.pontoeletronico"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);
	}

	/**
	 * Infos da API ao acessar UI.
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Ponto Inteligente API")
				.description("Documentação da API de acesso aos endpoints do Ponto Inteligente.")
				.version("1.0")
				.build();
	}

	/**
	 * Precisamos de um token para acesso, para isso fazemos uma autenticação manual
	 * @return
	 */
	@Bean
	public SecurityConfiguration security() {
		String token;
		try {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername("admin@zanatta.com");
			token = this.jwtTokenUtil.obterToken(userDetails);
		} catch (Exception e) {
			token = "";
		}
		return new SecurityConfiguration(null, null, null, null, "Bearer " + token, ApiKeyVehicle.HEADER,
				"Authorization", ",");
	}
}
