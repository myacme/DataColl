package com.bonc.colldata.controller;

import com.bonc.base.RestRecord;
import com.bonc.base.Util.JwtTokenUtil;
import com.bonc.colldata.entity.LoginUser;
import com.bonc.colldata.entity.UserManager;
import com.bonc.colldata.service.UserManagerService;
import com.bonc.utils.SessionUtiil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * author:kangxingqiao
 * Date:2021/7/23
 * Time:11:47
 */
@RestController
public class LoginController {
	@Resource
	private UserManagerService userManagerService;

	@PostMapping("/login")
	public Object login(@RequestBody LoginUser user) throws JsonProcessingException {
		//
		String username = user.getUsername();
		String password = user.getPassword();
		UserManager userManager = userManagerService.checkUserByName(username);
		if (userManager == null) {
			return new RestRecord(500, "用户不存在");
		}
		//检验密码是否争取
		if (!password.equals(userManager.getPassword())) {
			return new RestRecord(500, "用户密码不正确");
		}
		String token = JwtTokenUtil.getToken(username);
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("user", userManager);
		return new RestRecord(200, "成功", map);
	}

	@GetMapping("/info")
	public Object info() {
		return new RestRecord(200, "chengg", SessionUtiil.getUserInfo());
	}

	@GetMapping("/updatePwd")
	public Object updatePwd(String password) {
		UserManager userManager = SessionUtiil.getUserInfo();
		userManagerService.updatePassword(userManager.getUserId(), password);
		return new RestRecord(200, "成功", "修改成功！");
	}
}
