package com.example.belajar_spring.controller;

import com.example.belajar_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request, Model model) {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password, 
                       HttpServletRequest request, 
                       Model model) {
        if (username == null || username.isEmpty()) {
            model.addAttribute("error", "Username tidak boleh kosong");
            return "auth/login";
        }
        
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "Password tidak boleh kosong");
            return "auth/login";
        }
        
        if (userService.login(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", username);
            return "redirect:/";
        }
        model.addAttribute("error", "Username atau password salah!");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Anda berhasil logout");
        return "redirect:/auth/login";
    }

    @GetMapping("/register")
    public String registerForm(HttpServletRequest request) {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, 
                          @RequestParam String password, 
                          Model model,
                          RedirectAttributes redirectAttributes) {
        // Validate input
        if (username == null || username.isEmpty() || username.length() < 3) {
            model.addAttribute("error", "Username harus minimal 3 karakter");
            return "auth/register";
        }
        
        if (password == null || password.isEmpty() || password.length() < 6) {
            model.addAttribute("error", "Password harus minimal 6 karakter");
            return "auth/register";
        }
        
        if (userService.register(username, password)) {
            redirectAttributes.addFlashAttribute("success", "Registrasi berhasil! Silakan login.");
            return "redirect:/auth/login";
        }
        
        model.addAttribute("error", "Username sudah digunakan. Pilih username lain.");
        return "auth/register";
    }
}