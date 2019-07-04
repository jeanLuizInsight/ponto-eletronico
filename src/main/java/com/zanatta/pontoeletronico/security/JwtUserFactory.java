package com.zanatta.pontoeletronico.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.enums.PerfilEnum;

/**
 * Factory para gerenciar usuário do sistema (Funcionário) no usuário reconhecido pelo Spring Security.
 * @author <a href="">Jean Luiz Zanatta</a>
 * @since 04/07/2019
 */
public class JwtUserFactory {

	private JwtUserFactory() {
		throw new IllegalStateException("Classe utilitária!!!");
	}

	/**
	 * Converte e gera um JwtUser com base nos dados de um funcionário.
	 * @param funcionario
	 * @return JwtUser
	 */
	public static JwtUser create(Funcionario funcionario) {
		return new JwtUser(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(),
				mapToGrantedAuthorities(funcionario.getPerfil()));
	}

	/**
	 * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		return authorities;
	}
}
