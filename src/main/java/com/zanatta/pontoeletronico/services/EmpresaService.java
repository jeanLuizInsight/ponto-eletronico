package com.zanatta.pontoeletronico.services;

import java.text.MessageFormat;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zanatta.pontoeletronico.entities.Empresa;
import com.zanatta.pontoeletronico.repositories.EmpresaRepository;

@Service
public class EmpresaService {

	private static final Logger LOG = LoggerFactory.getLogger(EmpresaService.class);

	@Autowired EmpresaRepository empresaRepository;

	/**
	 * Retorna uma empresa filtrando pelo CNPJ.
	 * @param cnpj
	 * @return
	 */
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		String msg = MessageFormat.format("Buscando uma empresa para o CNPJ {0}", cnpj);
		LOG.info(msg);
		return Optional.ofNullable(this.empresaRepository.findByCnpj(cnpj));
	}

	/**
	 * Registra uma empresa no banco de dados.
	 * @param empresa
	 * @return
	 */
	public Empresa persistir(Empresa empresa) {
		String msg = MessageFormat.format("Persistindo empresa {0}", empresa);
		LOG.info(msg);
		return this.empresaRepository.save(empresa);
	}
}
