package com.zanatta.pontoeletronico.security.dto;

import java.io.Serializable;

/**
 * DTO de representação do token.
 * @author <a href="">Jean Luiz Zanatta</a>
 * @since 04/07/2019
 */
public class TokenDto implements Serializable {

	private static final long serialVersionUID = -7862794155894721307L;

	private String token;

	public TokenDto() {}

	public TokenDto(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
