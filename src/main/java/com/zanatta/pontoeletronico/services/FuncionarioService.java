package com.zanatta.pontoeletronico.services;

import java.text.MessageFormat;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {

	private static final Logger LOG = LoggerFactory.getLogger(FuncionarioService.class);

	@Autowired private FuncionarioRepository funcionarioRepository;

	/**
	 * Registra um funcionario no banco de dados.
	 * @param empresa
	 * @return
	 */
	public Funcionario persistir(Funcionario funcionario) {
		String msg = MessageFormat.format("Persistindo funcionario {0}", funcionario);
		LOG.info(msg);
		return this.funcionarioRepository.save(funcionario);
	}

	/**
	 * Retorna um funcionario filtrando pelo CPF.
	 * @param cnpj
	 * @return
	 */
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		String msg = MessageFormat.format("Buscando um funcionario para o CPF {0}", cpf);
		LOG.info(msg);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	/**
	 * Retorna um funcionario filtrando pelo E-mail.
	 * @param cnpj
	 * @return
	 */
	public Optional<Funcionario> buscarPorEmail(String email) {
		String msg = MessageFormat.format("Buscando um funcionario para o E-MAIL {0}", email);
		LOG.info(msg);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}

	/**
	 * Retorna um funcionario filtrando pelo seu ID.
	 * @param cnpj
	 * @return
	 */
	public Optional<Funcionario> buscarPorId(Long id) {
		String msg = MessageFormat.format("Buscando um funcionario pelo ID {0}", id);
		LOG.info(msg);
		return Optional.ofNullable(this.funcionarioRepository.findById(id).orElse(null));
	}
}
