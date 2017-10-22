package com.h2desenvolvimento.pontointeligente.api.repositories;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.h2desenvolvimento.pontointeligente.api.entities.Empresa;
import com.h2desenvolvimento.pontointeligente.api.entities.Funcionario;
import com.h2desenvolvimento.pontointeligente.api.entities.Lancamento;
import com.h2desenvolvimento.pontointeligente.api.enums.PerfilEnum;
import com.h2desenvolvimento.pontointeligente.api.enums.TipoEnum;
import com.h2desenvolvimento.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	private static final String CPF ="22549949803";
	private static final String EMAIL ="teste@email.com";
	
	private Long funcionarioId;

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		this.lancamentoRepository.save(obterDadosLancamento(funcionario));
		this.lancamentoRepository.save(obterDadosLancamento(funcionario));
	}

	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarLancamentoPorFuncionarioId() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
		assertEquals(2,lancamentos.size());
		
	}
	
	@Test
	public void testBuscarLancamentoPorFuncionarioIdPaginado() {
		PageRequest page = new PageRequest(0,10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId,page);
		assertEquals(2,lancamentos.getTotalElements());
		
	}

	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario fun = new Funcionario();
		fun.setCpf(CPF);
		fun.setEmail(EMAIL);
		fun.setEmpresa(empresa);
		fun.setNome("Funionario 1");
		fun.setPerfil(PerfilEnum.ROLE_USUARIO);
		fun.setSenha(PasswordUtils.gerarBCrypt("12345"));
		
		return fun;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("123456789098");
		empresa.setRazaoSocial("Empresa teste 1");
		
		return empresa;
	}
	
	private Lancamento obterDadosLancamento(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setFuncionario(funcionario);
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		
		return lancamento;
	}
}
