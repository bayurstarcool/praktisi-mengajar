package com.example.belajar_spring.service;

import com.example.belajar_spring.model.Jurusan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JurusanService {
    private final List<Jurusan> jurusanList = new ArrayList<>();
    private Long idCounter = 3L;

    public JurusanService() {
        // Data awal
        Jurusan jurusanTI = new Jurusan(1L, "Teknik Informatika");
        Jurusan jurusanSI = new Jurusan(2L, "Sistem Informasi");
        jurusanList.add(jurusanTI);
        jurusanList.add(jurusanSI);

    }

    public List<Jurusan> getAllJurusan() {
        return jurusanList;
    }
    
    // Search and Sort method
    public List<Jurusan> searchJurusan(String keyword, String sortBy, String sortDir) {
        List<Jurusan> result = jurusanList;
        
        // Apply search if keyword provided
        if (keyword != null && !keyword.isEmpty()) {
            final String lowerKeyword = keyword.toLowerCase();
            result = result.stream()
                .filter(j -> j.getNamaJurusan().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
        }
        
        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Jurusan> comparator;
            
            switch (sortBy) {
                case "namaJurusan":
                    comparator = Comparator.comparing(Jurusan::getNamaJurusan);
                    break;
                case "id":
                default:
                    comparator = Comparator.comparing(Jurusan::getId);
                    break;
            }
            
            // Apply sort direction
            if ("desc".equals(sortDir)) {
                comparator = comparator.reversed();
            }
            
            result = result.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        }
        
        return result;
    }

    public Jurusan saveJurusan(Jurusan jurusan) {
        if (jurusan.getId() == null) {
            jurusan.setId(idCounter++);
            jurusanList.add(jurusan);
        }
        return jurusan;
    }

    public Jurusan getJurusanByID(Long id) {
        return jurusanList.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }
    
    public void updateJurusan(Jurusan jurusan) {
        jurusanList.replaceAll(m -> m.getId().equals(jurusan.getId()) ? jurusan : m);
    }
    
    public void deleteJurusan(Long id) {
        jurusanList.removeIf(j -> j.getId().equals(id));
    }
    public Jurusan getJurusanById(Long id) {
        return jurusanList.stream()
            .filter(j -> j.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
}