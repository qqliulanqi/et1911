package com.etoak.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etoak.bean.User;
import com.etoak.exception.Et1911LoginEcxeption;
import com.etoak.service.UserService;
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	@GetMapping("/getById")
	@ResponseBody
	public User getById(int id) {
		return userService.getById(id);
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:/user/toLogin";
	}
	@PostMapping("/login")
	public String login(@RequestParam String name,@RequestParam String password,@RequestParam String code,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionCode = (String) session.getAttribute("code");
		
		if(!code.equals(sessionCode)) {
			throw new Et1911LoginEcxeption("验证码不正确");
			
		}
		password = DigestUtils.md5Hex(password);
		User user=userService.getByNameAndPassword(name, password);
		if(ObjectUtils.isEmpty(user)) {
			throw new Et1911LoginEcxeption("用户名或密码不正确");
		}
		session.invalidate();
		user.setPassword(null);
		session = request.getSession();
		session.setAttribute("user", user);
		return "redirect:/";
	}
}
