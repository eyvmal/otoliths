package no.eyvind.otoliths.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import no.eyvind.otoliths.util.LoginUtil;

@Controller
@RequestMapping("/${app.url.login}")
public class LoginController {

	@Value("${app.message.invalidUsername}") private String INVALID_USERNAME_MESSAGE;
	@Value("${app.url.login}")   private String LOGIN_URL;
	@Value("${app.url.homepage}") private String HOMEPAGE_URL;

	@GetMapping
    public String getLoginPage() {
		return "loginView";
    }

	@PostMapping
    public String tryLogin(@RequestParam String username, @RequestParam String difficulty,
    		HttpServletRequest request,	RedirectAttributes ra) {
		
		LoginUtil.logInUser(request, username, difficulty);

		return "redirect:" + HOMEPAGE_URL;
    }
}
