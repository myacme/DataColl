package com.bonc.base.Util;

import cn.hutool.core.codec.Base64;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/23
 * Time:11:10
 */
public class JwtTokenUtil {
	private static final String secret = "jwtsecretdemo";
	// 过期时间是3600秒，既是1个小时
	public static final long expiration = 3600L;

	/**
	 * 创建token
	 *
	 * @param username 用户名
	 * @return
	 *//*
	public static String generateToken(String username) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.create()
				.withAudience(username)
				.sign(algorithm)
				;

	}

	public static boolean checkToken(String token) {
		try {
			JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
		} catch (IllegalArgumentException | JWTVerificationException e) {

			return false;
		}
		return true;

	}*/
	public static String getToken(String username) {
		// 指定签名的时候使用的签名算法，也就是header那部分
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		// 生成JWT的时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		// 创建payload的私有声明（根据特定的业务需要添加）
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("username", username);
		// 添加payload声明
		// 设置jwt的body
		JwtBuilder builder = Jwts.builder()
				// 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
				.setClaims(claims)
				// 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setId(UUID.randomUUID().toString())
				// iat: jwt的签发时间
				.setIssuedAt(now)
				// 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串
				.setSubject(username)
				// 设置签名使用的签名算法和签名使用的秘钥
				.signWith(signatureAlgorithm, secret);
		if (expiration >= 0) {
			long expMillis = nowMillis + expiration * 1000;
			Date exp = new Date(expMillis);
			// 设置过期时间
			builder.setExpiration(exp);
		}
		return builder.compact();
	}


	public static boolean validateJWT(String jwtStr) {
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public static Claims getClaims(String jwtStr) {
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return claims;
	}

	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(secret);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	/**
	 * 解析JWT字符串
	 *
	 * @param jwt
	 * @return
	 *
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
	}

	/**
	 * 验证token是否过期
	 *
	 * @param token
	 * @return
	 */
	public static boolean verifyTokenExpireDate(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("token签发时间:" + sdf.format(claims.getIssuedAt()));
		System.out.println("token过期时间:" + sdf.format(claims.getExpiration()));
		if (new Date().after(claims.getExpiration())) {
			return false;
		}
		return true;
	}
}
