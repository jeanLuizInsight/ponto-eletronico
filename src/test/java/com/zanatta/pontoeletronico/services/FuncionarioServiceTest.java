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
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.repositories.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("desenvolvimento")
public class FuncionarioServiceTest {

	@MockBean private FuncionarioRepository funcionarioRepository;
	@Autowired private FuncionarioService funcionarioService;

	@Before
	public void setUp() {
		BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
	}

	@Test
	public void testPersistirFuncionario() {
		Funcionario funcionario = this.funcionarioService.persistir(new Funcionario());
		assertNotNull(funcionario);
	}

	@Test
	public void testBuscarFuncionarioPeloId() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(Long.valueOf(1));
		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPeloEmail() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorEmail("teste.email@teste.com");
		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPeloCpf() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf("03468422994");
		assertTrue(funcionario.isPresent());
	}

}
