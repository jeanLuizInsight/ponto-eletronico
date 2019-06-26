package com.zanatta.pontoeletronico.controllers;

import java.text.MessageFormat;
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
import com.zanatta.pontoeletronico.dtos.CadastroPJDto;
import com.zanatta.pontoeletronico.entities.Empresa;
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.enums.PerfilEnum;
import com.zanatta.pontoeletronico.response.Response;
import com.zanatta.pontoeletronico.services.EmpresaService;
import com.zanatta.pontoeletronico.services.FuncionarioService;
import com.zanatta.pontoeletronico.utils.PasswordUtils;

@RestController
@RequestMapping("api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static final Logger LOG = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired private EmpresaService empresaService;
	@Autowired private FuncionarioService funcionarioService;

	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto dto, BindingResult bindingResult) {
		String msg = MessageFormat.format("Cadastrando PJ: {0}", dto);
		LOG.info(msg);
		Response<CadastroPJDto> response = new Response<>();
		this.validarDadosExistentes(dto, bindingResult);
		if (bindingResult.hasErrors()) {
			String errorMsg = MessageFormat.format("Erro validando dados de cadastro PJ: {0}", bindingResult.getAllErrors());
			LOG.error(errorMsg);
			bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Empresa empresa = this.converterDtoParaEmpresa(dto);
		Funcionario funcionario = this.converterDtoParaFuncionario(dto);
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		response.setData(this.converterCadastroPJDto(funcionario));
		return ResponseEntity.ok(response);
	}

	/**
	 * Realiza validação dosa registros existentes na base de dados.
	 * @param dto
	 * @param bindingResult
	 */
	private void validarDadosExistentes(CadastroPJDto dto, BindingResult bindingResult) {
		this.empresaService.buscarPorCnpj(dto.getCnpj()).ifPresent(emp -> bindingResult.addError(new ObjectError("empresa",
				"Empresa já existente!")));
		this.funcionarioService.buscarPorCpf(dto.getCpf()).ifPresent(func -> bindingResult.addError(new ObjectError("funcionario",
				"CPF já existente!")));
		this.funcionarioService.buscarPorEmail(dto.getEmail()).ifPresent(func -> bindingResult.addError(new ObjectError("funcionario",
				"E-mail já existente!")));
	}

	/**
	 * Converte dados do dto para entidade Empresa
	 * @param dto
	 * @return
	 */
	private Empresa converterDtoParaEmpresa(CadastroPJDto dto) {
		Empresa emp = new Empresa();
		emp.setCnpj(dto.getCnpj());
		emp.setRazaoSocial(dto.getRazaoSocial());
		return emp;
	}

	/**
	 * Converte dados do dto para entidade Funcionario
	 * @param dto
	 * @return
	 */
	private Funcionario converterDtoParaFuncionario(CadastroPJDto dto) {
		Funcionario func = new Funcionario();
		func.setNome(dto.getNome());
		func.setEmail(dto.getEmail());
		func.setCpf(dto.getCpf());
		// PJ: dona da empresa > ADMIN
		func.setPerfil(PerfilEnum.ROLE_ADMIN);
		func.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));
		return func;
	}

	/**
	 * Converte dados do funcionario para CadastroPJDto
	 * @param funcionario
	 * @return
	 */
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		CadastroPJDto dto = new CadastroPJDto();
		dto.setId(funcionario.getId());
		dto.setNome(funcionario.getNome());
		dto.setEmail(funcionario.getEmail());
		dto.setCpf(funcionario.getCpf());
		dto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		dto.setCnpj(funcionario.getEmpresa().getCnpj());
		return dto;
	}

}
