package com.zanatta.pontoeletronico.repositories;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.zanatta.pontoeletronico.entities.Empresa;
import com.zanatta.pontoeletronico.entities.Funcionario;
import com.zanatta.pontoeletronico.entities.Lancamento;
import com.zanatta.pontoeletronico.enums.PerfilEnum;
import com.zanatta.pontoeletronico.enums.TipoEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("desenvolvimento")
public class LancamentoRepositoryTest {

	@Autowired private EmpresaRepository empresaRepository;
	@Autowired private FuncionarioRepository funcionarioRepository;
	@Autowired private LancamentoRepository lancamentoRepository;

	private Long funcionarioId;

	@Before
	public void setUp() {
		Empresa empresa = this.empresaRepository.save(this.obterDadosEmpresa());
		Funcionario funcionario = this.funcionarioRepository.save(this.obterDadosFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		this.lancamentoRepository.save(this.obterDadosLancamento(funcionario));
		this.lancamentoRepository.save(this.obterDadosLancamento(funcionario));
	}

	@After
	public final void tearDown() {
		this.lancamentoRepository.deleteAll();
		this.funcionarioRepository.deleteAll();
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testQueryBuscarLancamentosPorFuncionarioId() {
		PageRequest page = PageRequest.of(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId, page);
		assertEquals(2, lancamentos.getNumberOfElements());
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
		funcionario.setCpf("03445698723");
		funcionario.setEmail("teste.email@teste.com");
		funcionario.setEmpresa(empresa);
		return funcionario;
	}

	private Lancamento obterDadosLancamento(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		return lancamento;
	}

}
