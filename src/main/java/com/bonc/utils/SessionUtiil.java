package com.bonc.utils;

import com.bonc.base.Util.JwtTokenUtil;
import com.bonc.colldata.config.SpringContextUtil;
import com.bonc.colldata.entity.UserManager;
import com.bonc.colldata.service.UserManagerService;
import com.bonc.colldata.service.impl.UserManagerServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/23
 * Time:18:07
 * todo:获取当前登录用户信息
 */
public class SessionUtiil {

	public static UserManager getUserInfo() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			if (request != null) {
				//解决- 跨服务调用接口时，拦截器session判断null转string异常问题
				String token = request.getHeader("token");
				Claims claims = JwtTokenUtil.getClaims(token);
				String username = claims.getSubject();
				UserManagerServiceImpl userManagerService = (UserManagerServiceImpl) SpringContextUtil.getBean(UserManagerService.class);
				UserManager userManager = userManagerService.checkUserByName(username);
				return userManager;
			}
		}
		return null;
	}
}
