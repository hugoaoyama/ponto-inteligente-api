package com.h2desenvolvimento.pontointeligente.api.repositories;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.h2desenvolvimento.pontointeligente.api.entities.Empresa;
import com.h2desenvolvimento.pontointeligente.api.entities.Funcionario;
import com.h2desenvolvimento.pontointeligente.api.enums.PerfilEnum;
import com.h2desenvolvimento.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CPF ="22549949803";
	private static final String EMAIL ="teste@email.com";
	
	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.funcionarioRepository.save(obterDadosFuncionario(empresa));
	}
	
	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testFindByCpf() {
		Funcionario fun = this.funcionarioRepository.findByCpf(CPF);
		
		assertEquals(CPF, fun.getCpf());
	}
	
	@Test
	public void testFindByEmail() {
		Funcionario fun = this.funcionarioRepository.findByEmail(EMAIL);
		
		assertEquals(EMAIL, fun.getEmail());
	}
	
	@Test
	public void testFindByEmailOrCpf() {
		Funcionario fun = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		
		assertNotNull(fun);
	}
	
	@Test
	public void testFindByEmailOrCpfEmailInvalido() {
		Funcionario fun = this.funcionarioRepository.findByCpfOrEmail(CPF, "naoexiste@hedesenvolvimento.com");
		
		assertNotNull(fun);
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

	

}
