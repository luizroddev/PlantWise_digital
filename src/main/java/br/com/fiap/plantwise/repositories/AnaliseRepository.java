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

    @Query("SELECT a FROM Analise a WHERE a.usuario.id = :usuarioId")
    List<Analise> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT a FROM Analise a WHERE LOWER(a.doenca) LIKE LOWER(concat('%', :doenca, '%'))")
    List<Analise> findAllByDoenca(String doenca);

    @Query("SELECT a FROM Analise a JOIN a.usuario u WHERE LOWER(a.planta) LIKE LOWER(concat('%', :planta, '%')) AND LOWER(u.nome) LIKE LOWER(concat('%', :nome, '%'))")
    List<Analise> findAllByPlantaAndUsuarioNome(String planta, String nome);

}
