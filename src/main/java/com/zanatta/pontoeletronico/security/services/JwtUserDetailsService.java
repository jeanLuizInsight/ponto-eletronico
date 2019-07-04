package com.zanatta.pontoeletronico.security.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.security.JwtUserFactory;
import com.zanatta.pontoeletronico.services.FuncionarioService;

/**
 * Service para manipular a interface UserDetails do Spring.
 * @author <a href="mailto:jean.zanatta@unoesc.edu.br">Jean Luiz Zanatta</a>
 * @since 04/07/2019
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired private FuncionarioService funcionarioService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(username);
		if (funcionario.isPresent()) {
			return JwtUserFactory.create(funcionario.get());
		}
		throw new UsernameNotFoundException("E-mail não encontrado.");
	}

}
