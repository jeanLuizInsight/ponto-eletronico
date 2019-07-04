package com.zanatta.pontoeletronico.security.dto;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * DTO para usuário realizar login.
 * @author <a href="">Jean Luiz Zanatta</a>
 * @since 04/07/2019
 */
public class JwtAuthenticationDto implements Serializable {

	private static final long serialVersionUID = 8201610221187278892L;

	private String email;
	private String senha;

	@NotEmpty(message = "E-mail é obrigatório!")
	@Email(message = "E-mail inválido!")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Senha é obrigatória!")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [email=" + email + ", senha=" + senha + "]";
	}

}
