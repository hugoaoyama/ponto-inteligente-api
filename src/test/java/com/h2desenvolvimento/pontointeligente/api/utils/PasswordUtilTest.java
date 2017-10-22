package com.h2desenvolvimento.pontointeligente.api.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilTest {

	private static final String SENHA = "12345";
	private final BCryptPasswordEncoder bcryp = new BCryptPasswordEncoder();
	
	@Test
	public void testSenhaNula() throws Exception{
		assertNull(PasswordUtils.gerarBCrypt(null));
	}
	
	@Test
	public void testGeraSenhaHash() throws Exception{
		String hash = PasswordUtils.gerarBCrypt(SENHA);
		assertTrue(bcryp.matches(SENHA, hash));
	}

}
