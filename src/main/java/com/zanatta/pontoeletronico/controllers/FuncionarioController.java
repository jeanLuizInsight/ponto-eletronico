package com.zanatta.pontoeletronico.controllers;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zanatta.pontoeletronico.dtos.FuncionarioDto;
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.response.Response;
import com.zanatta.pontoeletronico.services.FuncionarioService;
import com.zanatta.pontoeletronico.utils.PasswordUtils;

@RestController
@RequestMapping("api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	private static final Logger LOG = LoggerFactory.getLogger(FuncionarioController.class);

	@Autowired private FuncionarioService funcionarioService;

	@PutMapping(value = "{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDto dto,
			BindingResult bindingResult) {
		String msg = MessageFormat.format("Atualizar funcionário: {0}", dto);
		LOG.info(msg);
		Response<FuncionarioDto> response = new Response<>();
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
		if (!funcionario.isPresent())
			bindingResult.addError(new ObjectError("funcionario", "Funcionário não encontrado!"));
		this.atualizarDadosFuncionario(funcionario.get(), dto, bindingResult);
		if (bindingResult.hasErrors()) {
			String errorMsg = MessageFormat.format("Erro validando dados do funcionário: {0}", bindingResult.getAllErrors());
			LOG.error(errorMsg);
			bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		this.funcionarioService.persistir(funcionario.get());
		response.setData(this.converterFuncionarioDto(funcionario.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Responsável por atualizar todas as informações do DTO no funcionário.
	 * @param funcionario
	 * @param dto
	 * @param bindingResult
	 */
	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto dto, BindingResult bindingResult) {
		funcionario.setNome(dto.getNome());
		if (!funcionario.getEmail().equals(dto.getEmail())) {
			this.funcionarioService.buscarPorEmail(dto.getEmail())
					.ifPresent(fun -> bindingResult.addError(new ObjectError("email", "E-mail já existente!")));
			funcionario.setEmail(dto.getEmail());
		}
		dto.getQtdHorasAlmoco().ifPresent(qtd -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtd)));
		dto.getQtdHorasTrabalhoDia().ifPresent(qtd -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtd)));
		dto.getValorHora().ifPresent(vlr -> funcionario.setValorHora(new BigDecimal(vlr)));
		if (dto.getSenha().isPresent())
			funcionario.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha().get()));
	}

	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto dto = new FuncionarioDto();
		dto.setId(funcionario.getId());
		dto.setEmail(funcionario.getEmail());
		dto.setCpf(Optional.of(funcionario.getCpf()));
		dto.setNome(funcionario.getNome());
		dto.setQtdHorasAlmoco(Optional.of(Float.toString(funcionario.getQtdHorasAlmoco())));
		dto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(funcionario.getQtdHorasTrabalhoDia())));
		dto.setValorHora(Optional.of(String.valueOf(funcionario.getValorHora())));
		return dto;
	}

}
