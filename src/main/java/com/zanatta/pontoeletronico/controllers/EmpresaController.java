package com.zanatta.pontoeletronico.controllers;

import java.text.MessageFormat;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zanatta.pontoeletronico.dtos.EmpresaDto;
import com.zanatta.pontoeletronico.entities.Empresa;
import com.zanatta.pontoeletronico.response.Response;
import com.zanatta.pontoeletronico.services.EmpresaService;

@RestController
@RequestMapping("api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

	private static final Logger LOG = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired private EmpresaService empresaService;

	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
		String msg = MessageFormat.format("Buscando empresa por CNPJ: {0}", cnpj);
		LOG.info(msg);
		Response<EmpresaDto> response = new Response<>();
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cnpj);
		if (!empresa.isPresent()) {
			msg = MessageFormat.format("Empresa não encontrada para o CNPJ: {0}", cnpj);
			LOG.info(msg);
			response.getErrors().add(msg);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterParaEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Converte dados da Empresa para DTO
	 * @param empresa
	 * @return
	 */
	private EmpresaDto converterParaEmpresaDto(Empresa empresa) {
		EmpresaDto dto = new EmpresaDto();
		dto.setId(empresa.getId());
		dto.setCnpj(empresa.getCnpj());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		return dto;
	}

}
