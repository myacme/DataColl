package com.bonc.colldata.Interceptor;

import com.bonc.base.RestRecord;
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
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		if (token == null) {
			RestRecord r2 = new RestRecord(HttpServletResponse.SC_UNAUTHORIZED, "无token", new RuntimeException().getMessage());
			response.getWriter().append(r2.toString());
			return false;
		}
		if (!JwtTokenUtil.verifyTokenExpireDate(token)) {
			RestRecord r2 = new RestRecord(HttpServletResponse.SC_UNAUTHORIZED, "token过期", new RuntimeException("token过期！").getMessage());
			response.getWriter().append(r2.toString());
			return false;
		}
		boolean flag = JwtTokenUtil.validateJWT(token);
		return flag;
	}
}
