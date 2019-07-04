package com.zanatta.pontoeletronico.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe utilitária para manipular token de autenticação.
 * @author <a href="">Jean Luiz Zanatta</a>
 * @since 04/07/2019
 */
@Component
public class JwtTokenUtil {

	private static final Logger LOG = LoggerFactory.getLogger(JwtTokenUtil.class);

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}") private String secret;
	@Value("${jwt.expiration}") private Long expiration;

	/**
	 * Obtém o username (email) contido no token JWT.
	 * @param token
	 * @return String
	 */
	public String getUsernameFromToken(String token) {
		String username = null;
		try {
			Claims claims = getClaimsFromToken(token);
			if (claims != null)
				username = claims.getSubject();
		} catch (Exception e) {
			LOG.error("Ocorreu um erro ao recuperar usuário do token: {}", e.getMessage());
		}
		return username;
	}

	/**
	 * Retorna a data de expiração de um token JWT.
	 * @param token
	 * @return Date
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expirationLoc = null;
		try {
			Claims claims = getClaimsFromToken(token);
			if (claims != null)
				expirationLoc = claims.getExpiration();
		} catch (Exception e) {
			LOG.error("Ocorreu um erro ao recuperar data de expiração  token: {}", e.getMessage());
		}
		return expirationLoc;
	}

	/**
	 * Cria um novo token (refresh).
	 * @param token
	 * @return String
	 */
	public String refreshToken(String token) {
		String refreshedToken = null;
		try {
			Claims claims = getClaimsFromToken(token);
			if (claims != null) {
				claims.put(CLAIM_KEY_CREATED, new Date());
				refreshedToken = gerarToken(claims);
			}
		} catch (Exception e) {
			LOG.error("Ocorreu um erro ao fazer refresh no token: {}", e.getMessage());
		}
		return refreshedToken;
	}

	/**
	 * Verifica e retorna se um token JWT é válido.
	 * @param token
	 * @return boolean
	 */
	public boolean tokenValido(String token) {
		return !tokenExpirado(token);
	}

	/**
	 * Retorna um novo token JWT com base nos dados do usuários.
	 * @param userDetails
	 * @return String
	 */
	public String obterToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());
		return gerarToken(claims);
	}

	/**
	 * Realiza o parse do token JWT para extrair as informações contidas no
	 * corpo dele.
	 * @param token
	 * @return Claims
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 * Retorna a data de expiração com base na data atual.
	 * @return Date
	 */
	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * Verifica se um token JTW está expirado.
	 * @param token
	 * @return boolean
	 */
	private boolean tokenExpirado(String token) {
		Date dataExpiracao = this.getExpirationDateFromToken(token);
		if (dataExpiracao == null) {
			return false;
		}
		return dataExpiracao.before(new Date());
	}

	/**
	 * Gera um novo token JWT contendo os dados (claims) fornecidos.
	 * @param claims
	 * @return String
	 */
	private String gerarToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
