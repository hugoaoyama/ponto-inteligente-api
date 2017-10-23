package com.h2desenvolvimento.pontointeligente.api.services;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.After;
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

import com.h2desenvolvimento.pontointeligente.api.entities.Empresa;
import com.h2desenvolvimento.pontointeligente.api.repositories.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {
	
	@MockBean
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EmpresaService empresaService;
	
	private static final String CNPJ ="22549949803565";

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
		BDDMockito.given(this.empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuscarEmpresaPorCnpj() throws Exception{
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(CNPJ);
		
		assertTrue(empresa.isPresent());
		
	}
	
	@Test
	public void testEmpresaPersist() throws Exception{
		Empresa empresa = this.empresaService.persistir(new Empresa());
		
		assertNotNull(empresa);
		
	}

}
