package com.example.belajar_spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.belajar_spring.service.MahasiswaService;
import com.example.belajar_spring.service.JurusanService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class BelajarController {
    
    @Autowired
    private MahasiswaService mahasiswaService;
    
    @Autowired
    private JurusanService jurusanService;
    
    @GetMapping("/")
    public String showDashboard(Model model) {
        model.addAttribute("totalMahasiswa", mahasiswaService.getAllMahasiswa().size());
        model.addAttribute("totalJurusan", jurusanService.getAllJurusan().size());
        return "dashboard";
    }
}