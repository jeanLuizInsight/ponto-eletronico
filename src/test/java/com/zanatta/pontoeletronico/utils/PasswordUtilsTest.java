package com.zanatta.pontoeletronico.utils;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * OBS: não depende de contexto do Spring, portanto não há necessidade de anotações
 */
public class PasswordUtilsTest {

	private static final String SENHA = "123456";
	private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

	@Test
	public void testGerarHashSenha() {
		String hash = PasswordUtils.gerarBCrypt(SENHA);
		assertTrue(bCryptEncoder.matches(SENHA, hash));
	}

}
