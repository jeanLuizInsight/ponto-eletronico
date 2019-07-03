package com.zanatta.pontoeletronico.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.zanatta.pontoeletronico.entities.Empresa;
import com.zanatta.pontoeletronico.services.EmpresaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("desenvolvimento")
public class EmpresaControllerTest {

	private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
	private static final Long ID = Long.valueOf(1);
	private static final String CNPJ = "51463645000100";
	private static final String RAZAO_SOCIAL = "Empresa Teste Automatizado";

	// instancia contexto container WEB
	@Autowired private MockMvc mvc;
	// mock bean do manager
	@MockBean private EmpresaService empresaService;

	@Test
	public void testBuscarEmpresaPorCnpjInvalido() throws Exception {
		// criando ação para o método buscar, para não retornar informações
		BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());
		/**
		 * Chamada ao serviço validando retorno esperado:
		 * {
		 * "data": null,
		 * "errors": [
		 * "Empresa não encontrada para o CNPJ XXXX"
		 * ]
		 * }
		 */
		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ: " + CNPJ));
	}

	@Test
	public void testBuscarEmpresaPorCnpjValido() throws Exception {
		// criando ação para o método buscar, para retornar informações específicas da empresa
		BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(this.obterDadosEmpresa()));
		/**
		 * Chamada ao serviço validando retorno esperado:
		 * {
		 * "data": {
		 * "id": xxxx,
		 * "razaoSocial": xxxx,
		 * "cnpj": xxxx
		 * },
		 * "errors": [
		 * ]
		 * }
		 */
		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.razaoSocial").value(RAZAO_SOCIAL))
				.andExpect(jsonPath("$.data.cnpj").value(CNPJ))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setId(ID);
		empresa.setCnpj(CNPJ);
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		return empresa;
	}

}
