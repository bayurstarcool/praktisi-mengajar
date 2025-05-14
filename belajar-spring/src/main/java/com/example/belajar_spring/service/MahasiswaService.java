package com.example.belajar_spring.service;

import com.example.belajar_spring.model.Jurusan;
import com.example.belajar_spring.model.Mahasiswa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MahasiswaService {
    private List<Mahasiswa> mahasiswaList = new ArrayList<>();
    private List<Jurusan> jurusanList = new ArrayList<>();

    public MahasiswaService() {
        // Data awal
        Jurusan jurusanTI = new Jurusan(1L, "Teknik Informatika");
        Jurusan jurusanSI = new Jurusan(2L, "Sistem Informasi");
        jurusanList.add(jurusanTI);
        jurusanList.add(jurusanSI);

        mahasiswaList.add(new Mahasiswa(1L, "Wahyu", jurusanTI));
        mahasiswaList.add(new Mahasiswa(2L, "Andi", jurusanSI));
        mahasiswaList.add(new Mahasiswa(3L, "Budi", jurusanTI));
        mahasiswaList.add(new Mahasiswa(4L, "Cindy", jurusanSI));
    }

    // CRUD Mahasiswa
    public List<Mahasiswa> getAllMahasiswa() {
        return mahasiswaList;
    }
    
    // Search and Sort methods
    public List<Mahasiswa> searchMahasiswa(String keyword, String sortBy, String sortDir) {
        List<Mahasiswa> result = mahasiswaList;
        
        // Apply search if keyword provided
        if (keyword != null && !keyword.isEmpty()) {
            final String lowerKeyword = keyword.toLowerCase();
            result = result.stream()
                .filter(m -> m.getNama().toLowerCase().contains(lowerKeyword) || 
                             m.getJurusan().getNamaJurusan().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
        }
        
        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<Mahasiswa> comparator;
            
            switch (sortBy) {
                case "nama":
                    comparator = Comparator.comparing(Mahasiswa::getNama);
                    break;
                case "jurusan":
                    comparator = Comparator.comparing(m -> m.getJurusan().getNamaJurusan());
                    break;
                case "id":
                default:
                    comparator = Comparator.comparing(Mahasiswa::getId);
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

    public Mahasiswa getMahasiswaById(Long id) {
        return mahasiswaList.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    public void addMahasiswa(Mahasiswa mahasiswa) {
        mahasiswaList.add(mahasiswa);
    }

    public void updateMahasiswa(Mahasiswa mahasiswa) {
        mahasiswaList.replaceAll(m -> m.getId().equals(mahasiswa.getId()) ? mahasiswa : m);
    }

    public void deleteMahasiswa(Long id) {
        mahasiswaList.removeIf(m -> m.getId().equals(id));
    }


}