package com.example.BloggingPlatform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloggingPlatform.collections.User;
import com.example.BloggingPlatform.model.JwtRequest;
import com.example.BloggingPlatform.model.JwtResponse;
import com.example.BloggingPlatform.service.UserService;



@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/signUp")
	public String signUp(Model model) throws Exception {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/signUp")
	public String signUp(@ModelAttribute User user, Model model) throws Exception {
		try{
			userService.signUp(user);
	        return "redirect:/signIn";
	    } catch (Exception e) {
	    	model.addAttribute("user", new User()); //to show form with empty values
	        model.addAttribute("error", e.getMessage());
	        return "signup";
	    }
	}
	
	@GetMapping("/signIn")
	//added request param in case return from authenticationEntryPoint
	public String signIn(@RequestParam(value = "signInErrorMsg", required = false) String signInErrorMsg, Model model) throws Exception {
		if(signInErrorMsg!=null) {
			model.addAttribute("signInErrorMsg", "Please Sign in first to continue using application");
		}
		model.addAttribute("jwtRequest", new JwtRequest());
		return "login";
	}
	
	@PostMapping("/signIn")
	public String signIn(@ModelAttribute JwtRequest jwtRequest, HttpServletRequest request,Model model) throws Exception {
		try{
			return userService.signIn(jwtRequest, request);
		} catch (Exception e) {
			model.addAttribute("jwtRequest", new JwtRequest());
			model.addAttribute("error", e.getMessage());
			return "login";
		}
	}
	
	@GetMapping("/signOut")
	public String signOut(HttpSession session) {
	    SecurityContextHolder.clearContext();
	    session.invalidate();
	    return "redirect:/signIn";
	}
	
}
