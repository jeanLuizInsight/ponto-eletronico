package com.zanatta.pontoeletronico.services;

import java.text.MessageFormat;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.zanatta.pontoeletronico.entities.Lancamento;
import com.zanatta.pontoeletronico.repositories.LancamentoRepository;

@Service
public class LancamentoService {

	private static final Logger LOG = LoggerFactory.getLogger(FuncionarioService.class);

	@Autowired private LancamentoRepository lancamentoRepository;

	/**
	 * Registra um lançamento no banco de dados.
	 * OBS.: cachePut: atualiza qualquer alteração do elemento que já está no cache
	 * @param lancamento
	 * @return
	 */
	@CachePut("lancamentoPorId")
	public Lancamento persistir(Lancamento lancamento) {
		String msg = MessageFormat.format("Persistindo lançamento {0}", lancamento);
		LOG.info(msg);
		return this.lancamentoRepository.save(lancamento);
	}

	/**
	 * Retorna um lançamento filtrando pelo seu ID.
	 * @param id
	 * @return
	 */
	@Cacheable("lancamentoPorId")
	public Optional<Lancamento> buscarPorId(Long id) {
		String msg = MessageFormat.format("Buscando um lançamento pelo ID {0}", id);
		LOG.info(msg);
		return Optional.ofNullable(this.lancamentoRepository.findById(id).orElse(null));
	}

	/**
	 * Retorna um lançamento filtrando pelo ID do funcionário.
	 * @param id
	 * @return
	 */
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest page) {
		String msg = MessageFormat.format("Buscando um lançamento pelo ID do funcionário {0}", funcionarioId);
		LOG.info(msg);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
	}

	/**
	 * Remove um lançamento pelo seu ID
	 * @param id
	 */
	public void remover(Long id) {
		String msg = MessageFormat.format("Removendo um lançamento pelo ID {0}", id);
		LOG.info(msg);
		this.lancamentoRepository.deleteById(id);
	}
}
