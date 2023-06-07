package br.com.fiap.plantwise.repositories;

import br.com.fiap.plantwise.models.Analise;
import br.com.fiap.plantwise.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Page<Usuario> findByNomeContaining(String nome, Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(concat('%', :nome, '%'))")
    List<Usuario> findByNomeContaining(String nome);
}
