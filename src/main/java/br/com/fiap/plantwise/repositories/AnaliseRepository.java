package br.com.fiap.plantwise.repositories;

import br.com.fiap.plantwise.models.Analise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnaliseRepository extends JpaRepository<Analise, Long> {
    Page<Analise> findByPlantaContaining(String planta, Pageable pageable);

}
