package com.zanatta.pontoeletronico.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.zanatta.pontoeletronico.entities.Empresa;
import com.zanatta.pontoeletronico.repositories.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("desenvolvimento")
public class EmpresaServiceTest {

	private static final String CNPJ = "51463645000100";

	@MockBean private EmpresaRepository empresaRepository;
	@Autowired private EmpresaService empresaService;

	@Before
	public void setUp() {
		BDDMockito.given(this.empresaRepository.findByCnpj(CNPJ)).willReturn(new Empresa());
		BDDMockito.given(this.empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
	}

	@Test
	public void testBuscarEmpresaPeloCnpj() {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(CNPJ);
		assertTrue(empresa.isPresent());
	}

	@Test
	public void testPersistirEmpresa() {
		Empresa empresa = this.empresaService.persistir(new Empresa());
		assertNotNull(empresa);
	}

}
