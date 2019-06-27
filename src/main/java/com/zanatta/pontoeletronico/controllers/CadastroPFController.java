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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zanatta.pontoeletronico.dtos.CadastroPFDto;
import com.zanatta.pontoeletronico.entities.Empresa;
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.enums.PerfilEnum;
import com.zanatta.pontoeletronico.response.Response;
import com.zanatta.pontoeletronico.services.EmpresaService;
import com.zanatta.pontoeletronico.services.FuncionarioService;
import com.zanatta.pontoeletronico.utils.PasswordUtils;

@RestController
@RequestMapping("api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

	private static final Logger LOG = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired private EmpresaService empresaService;
	@Autowired private FuncionarioService funcionarioService;

	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto dto, BindingResult bindingResult) {
		String msg = MessageFormat.format("Cadastrando PF: {0}", dto);
		LOG.info(msg);
		Response<CadastroPFDto> response = new Response<>();
		this.validarDadosExistentes(dto, bindingResult);
		if (bindingResult.hasErrors()) {
			String errorMsg = MessageFormat.format("Erro validando dados de cadastro PF: {0}", bindingResult.getAllErrors());
			LOG.error(errorMsg);
			bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Funcionario funcionario = this.converterDtoParaFuncionario(dto);
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(dto.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		this.funcionarioService.persistir(funcionario);
		response.setData(this.converterCadastroPFDto(funcionario));
		return ResponseEntity.ok(response);
	}

	/**
	 * Realiza validação dosa registros existentes na base de dados.
	 * @param dto
	 * @param bindingResult
	 */
	private void validarDadosExistentes(CadastroPFDto dto, BindingResult bindingResult) {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(dto.getCnpj());
		if (!empresa.isPresent())
			bindingResult.addError(new ObjectError("empresa", "Empresa não cadastrada!"));
		this.funcionarioService.buscarPorCpf(dto.getCpf()).ifPresent(func -> bindingResult.addError(new ObjectError("funcionario",
				"CPF já existente!")));
		this.funcionarioService.buscarPorEmail(dto.getEmail()).ifPresent(func -> bindingResult.addError(new ObjectError("funcionario",
				"E-mail já existente!")));
	}

	/**
	 * Converte dados do dto para entidade Funcionario
	 * @param dto
	 * @return
	 */
	private Funcionario converterDtoParaFuncionario(CadastroPFDto dto) {
		Funcionario func = new Funcionario();
		func.setNome(dto.getNome());
		func.setEmail(dto.getEmail());
		func.setCpf(dto.getCpf());
		func.setPerfil(PerfilEnum.ROLE_USUARIO);
		func.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));
		dto.getQtdHorasAlmoco().ifPresent(qtd -> func.setQtdHorasAlmoco(Float.valueOf(qtd)));
		dto.getQtdHorasTrabalhoDia().ifPresent(qtd -> func.setQtdHorasTrabalhoDia(Float.valueOf(qtd)));
		dto.getValorHora().ifPresent(val -> func.setValorHora(new BigDecimal(val)));
		return func;
	}

	/**
	 * Converte dados do funcionario para CadastroPFDto
	 * @param funcionario
	 * @return
	 */
	private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
		CadastroPFDto dto = new CadastroPFDto();
		dto.setId(funcionario.getId());
		dto.setNome(funcionario.getNome());
		dto.setEmail(funcionario.getEmail());
		dto.setCpf(funcionario.getCpf());
		dto.setCnpj(funcionario.getEmpresa().getCnpj());
		funcionario.getQtdHorasAlmocoOpt().ifPresent(qtd -> dto.setQtdHorasAlmoco(Optional.of(Float.toString(qtd))));
		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtd -> dto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtd))));
		funcionario.getValorHoraOpt().ifPresent(valr -> dto.setValorHora(Optional.of(valr.toString())));
		return dto;
	}
}
