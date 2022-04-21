package br.com.DemandFlix.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.DemandFlix.annotation.Publico;

@Controller
public class LoginController {

	@RequestMapping("/")
	@Publico
	public String index(HttpSession session) {
		
		session.invalidate();
		
		return "index";
	}
}
