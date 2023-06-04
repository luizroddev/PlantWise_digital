package br.com.fiap.plantwise.controllers;

import br.com.fiap.plantwise.models.Analise;
import br.com.fiap.plantwise.models.Usuario;
import br.com.fiap.plantwise.repositories.AnaliseRepository;
import br.com.fiap.plantwise.repositories.UsuarioRepository;
import br.com.fiap.plantwise.service.TokenService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/analises")
@Slf4j
public class AnaliseController {

    @Autowired
    AnaliseRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @PostMapping("{id}")
    public ResponseEntity<Analise> registrar(@PathVariable Long id, @RequestBody @Valid Analise analise) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        analise.setUsuario(usuario);

        usuario.getAnalises().add(analise);
        usuarioRepository.save(usuario);

        repository.save(analise);

        return ResponseEntity.status(HttpStatus.CREATED).body(analise);
    }

    @GetMapping("usuario/{id}")
    public List<Analise> findAllByUsuarioId(@PathVariable("id") Long usuarioId) {
        log.info("Buscando análises por ID de usuário");
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return repository.findByUsuarioId(usuario.getId());
    }

    @GetMapping("doenca/{doenca}")
    public List<Analise> findAllByDoenca(@PathVariable("doenca") String doenca) {
        log.info("Buscando análises por doença");
        return repository.findAllByDoenca(doenca);
    }

    @GetMapping("planta/{planta}/usuario/{nome}")
    public List<Analise> findAllByPlantaAndUsuarioNome(@PathVariable String planta, @PathVariable String nome) {
        log.info("Buscando análises por planta e nome do usuário");
        return repository.findAllByPlantaAndUsuarioNome(planta, nome);
    }


    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String planta, @PageableDefault(size = 5) Pageable pageable) {
        log.info("Buscando usuários");

        Page<Analise> analises = (planta == null) ?
                repository.findAll(pageable) :
                repository.findByPlantaContaining(planta, pageable);

        return assembler.toModel(analises.map(Analise::toEntityModel));
    }

    @GetMapping("{id}")
    public EntityModel<Analise> show(@PathVariable Long id) {
        log.info("Buscar análise com ID " + id);
        Analise analise = getAnaliseByIdOrThrow(id);
        return analise.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Deletando análise");

        repository.delete(getAnaliseByIdOrThrow(id));
        return ResponseEntity.noContent().build();
    }

    private Analise getAnaliseByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Análise não encontrada"));
    }
}
