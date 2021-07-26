package com.bonc.colldata.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.bonc.base.Util.JwtTokenUtil;
import com.bonc.colldata.entity.UserManager;
import com.bonc.colldata.mapper.UserManagerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/23
 * Time:11:35
 */
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("authorization");
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		if (token == null) {
			return false;
		}
		boolean flag = JwtTokenUtil.validateJWT(token);
		return flag;
	}

}
