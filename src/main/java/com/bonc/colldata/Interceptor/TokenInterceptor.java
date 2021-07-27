package com.bonc.colldata.Interceptor;

import com.bonc.base.Util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			return false;
		}
		if (token == null) {
			return false;
		}
		if (!JwtTokenUtil.verifyTokenExpireDate(token)){
			throw new  RuntimeException("token过期，请重新登录！");
		}
		boolean flag = JwtTokenUtil.validateJWT(token);
		return flag;
	}

}
