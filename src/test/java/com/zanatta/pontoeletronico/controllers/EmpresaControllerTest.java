package com.zanatta.pontoeletronico.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.zanatta.pontoeletronico.services.EmpresaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("desenvolvimento")
public class EmpresaControllerTest {

	private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj";
	private static final Long ID = Long.valueOf(1);
	private static final String CNPJ = "51463645000100";
	private static final String RAZAO_SOCIAL = "Empresa Teste Automatizado";

	// instancia contexto container WEB
	@Autowired private MockMvc mvc;
	// mock bean do manager
	@MockBean private EmpresaService empresaService;

	@Test
	public void testBuscarEmpresaPorCnpjInvalido() {

	}

}
