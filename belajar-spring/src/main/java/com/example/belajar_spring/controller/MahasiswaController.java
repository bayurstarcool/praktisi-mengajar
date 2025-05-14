package com.example.belajar_spring.controller;

import com.example.belajar_spring.model.Jurusan;
import com.example.belajar_spring.model.Mahasiswa;
import com.example.belajar_spring.service.JurusanService;
import com.example.belajar_spring.service.MahasiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {

    private final JurusanService jurusanService;

    @Autowired
    private MahasiswaService mahasiswaService;

    MahasiswaController(JurusanService jurusanService) {
        this.jurusanService = jurusanService;
    }

    @GetMapping
    public String listMahasiswa(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {
        
        model.addAttribute("mahasiswaList", mahasiswaService.searchMahasiswa(keyword, sortBy, sortDir));
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", "asc".equals(sortDir) ? "desc" : "asc");
        
        return "mahasiswa/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("mahasiswa", new Mahasiswa());
        model.addAttribute("jurusanList", jurusanService.getAllJurusan());
        model.addAttribute("isEdit", false);
        return "mahasiswa/add";
    }

    @PostMapping("/add")
    public String addMahasiswa(@ModelAttribute Mahasiswa mahasiswa, Model model, RedirectAttributes redirectAttributes) {
        // Validate input
        if (mahasiswa.getNama() == null || mahasiswa.getNama().trim().isEmpty()) {
            model.addAttribute("error", "Nama mahasiswa tidak boleh kosong");
            model.addAttribute("jurusanList", jurusanService.getAllJurusan());
            model.addAttribute("isEdit", false);
            return "mahasiswa/add";
        }
        
        if (mahasiswa.getJurusan() == null || mahasiswa.getJurusan().getId() == null) {
            model.addAttribute("error", "Pilih jurusan untuk mahasiswa");
            model.addAttribute("jurusanList", jurusanService.getAllJurusan());
            model.addAttribute("isEdit", false);
            return "mahasiswa/add";
        }
        
        // Get the full Jurusan object from the ID
        Long jurusanId = mahasiswa.getJurusan().getId();
        Jurusan jurusan = jurusanService.getJurusanById(jurusanId);
        if (jurusan == null) {
            model.addAttribute("error", "Jurusan tidak valid");
            model.addAttribute("jurusanList", jurusanService.getAllJurusan());
            model.addAttribute("isEdit", false);
            return "mahasiswa/add";
        }
        
        mahasiswa.setJurusan(jurusan);
        mahasiswa.setId(System.currentTimeMillis()); // Generate ID
        mahasiswaService.addMahasiswa(mahasiswa);
        
        redirectAttributes.addFlashAttribute("success", "Data mahasiswa berhasil ditambahkan");
        return "redirect:/mahasiswa";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Mahasiswa mahasiswa = mahasiswaService.getMahasiswaById(id);
        if (mahasiswa == null) {
            redirectAttributes.addFlashAttribute("error", "Mahasiswa dengan ID " + id + " tidak ditemukan");
            return "redirect:/mahasiswa";
        }
        
        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("jurusanList", jurusanService.getAllJurusan());
        model.addAttribute("isEdit", true);
        return "mahasiswa/add";
    }

    @PostMapping("/edit/{id}")
    public String editMahasiswa(@PathVariable Long id, @ModelAttribute Mahasiswa mahasiswa, 
                              Model model, RedirectAttributes redirectAttributes) {
        // Validate input
        if (mahasiswa.getNama() == null || mahasiswa.getNama().trim().isEmpty()) {
            model.addAttribute("error", "Nama mahasiswa tidak boleh kosong");
            model.addAttribute("jurusanList", jurusanService.getAllJurusan());
            model.addAttribute("isEdit", true);
            return "mahasiswa/add";
        }
        
        if (mahasiswa.getJurusan() == null || mahasiswa.getJurusan().getId() == null) {
            model.addAttribute("error", "Pilih jurusan untuk mahasiswa");
            model.addAttribute("jurusanList", jurusanService.getAllJurusan());
            model.addAttribute("isEdit", true);
            return "mahasiswa/add";
        }
        
        // Ensure the ID from path is set on the object
        mahasiswa.setId(id);
        
        // Get the full Jurusan object from the ID
        Long jurusanId = mahasiswa.getJurusan().getId();
        Jurusan jurusan = jurusanService.getJurusanById(jurusanId);
        if (jurusan == null) {
            model.addAttribute("error", "Jurusan tidak valid");
            model.addAttribute("jurusanList", jurusanService.getAllJurusan());
            model.addAttribute("isEdit", true);
            return "mahasiswa/add";
        }
        
        mahasiswa.setJurusan(jurusan);
        mahasiswaService.updateMahasiswa(mahasiswa);
        
        redirectAttributes.addFlashAttribute("success", "Data mahasiswa berhasil diperbarui");
        return "redirect:/mahasiswa";
    }

    @GetMapping("/delete/{id}")
    public String deleteMahasiswa(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Mahasiswa mahasiswa = mahasiswaService.getMahasiswaById(id);
        if (mahasiswa != null) {
            mahasiswaService.deleteMahasiswa(id);
            redirectAttributes.addFlashAttribute("success", "Mahasiswa '" + mahasiswa.getNama() + "' berhasil dihapus");
        } else {
            redirectAttributes.addFlashAttribute("error", "Mahasiswa dengan ID " + id + " tidak ditemukan");
        }
        return "redirect:/mahasiswa";
    }
}