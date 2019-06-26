package com.zanatta.pontoeletronico.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

public class PasswordUtils {

	public static final Logger LOG = LoggerFactory.getLogger(PasswordUtils.class);

	private PasswordUtils() {
		throw new IllegalStateException("Classe utilitária!!!");
	}

	public static String gerarBCrypt(String senha) {
		if (StringUtils.isEmpty(senha))
			return senha;
		String msg = "Gerando hash com o BCrypt...";
		LOG.info(msg);
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		return bcpe.encode(senha);
	}
}
