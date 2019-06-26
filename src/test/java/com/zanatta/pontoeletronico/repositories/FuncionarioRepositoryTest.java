package com.zanatta.pontoeletronico.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.zanatta.pontoeletronico.entities.Empresa;
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.enums.PerfilEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("desenvolvimento")
public class FuncionarioRepositoryTest {

	private static final String CPF = "51463645000100";
	private static final String EMAIL = "teste.email@teste.com";

	@Autowired private EmpresaRepository empresaRepository;
	@Autowired private FuncionarioRepository funcionarioRepository;

	@Before
	public void setUp() {
		Empresa empresa = this.empresaRepository.save(this.obterDadosEmpresa());
		this.funcionarioRepository.save(this.obterDadosFuncionario(empresa));
	}

	@After
	public final void tearDown() {
		this.funcionarioRepository.deleteAll();
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testQueryBuscarFuncionarioPeloEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
		assertEquals(EMAIL, funcionario.getEmail());
	}

	@Test
	public void testQueryBuscarFuncionarioPeloCpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
		assertEquals(CPF, funcionario.getCpf());
	}

	@Test
	public void testQueryBuscarFuncionarioPeloEmailECpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		assertNotNull(funcionario);
	}

	@Test
	public void testQueryBuscarFuncionarioPeloEmailECpfParaCpfInvalido() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "teste.invalido@teste.com");
		assertNotNull(funcionario);
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa teste");
		empresa.setCnpj("51463645000100");
		return empresa;
	}

	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Funcionario teste");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha("123456");
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setEmpresa(empresa);
		return funcionario;
	}
}
