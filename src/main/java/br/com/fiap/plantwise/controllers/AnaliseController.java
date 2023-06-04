package br.com.fiap.plantwise.controllers;

import br.com.fiap.plantwise.models.Analise;
import br.com.fiap.plantwise.records.AnaliseResponse;
import br.com.fiap.plantwise.records.PlantImageRequest;
import br.com.fiap.plantwise.records.PlantImageResponse;
import br.com.fiap.plantwise.service.AnaliseService;
import br.com.fiap.plantwise.service.PlantImageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/analises")
@Slf4j
public class AnaliseController {

    @Autowired
    AnaliseService analiseService;

    @Autowired
    PlantImageService plantImageService;

    @PostMapping("image")
    public ResponseEntity<PlantImageResponse> analisarImagem(@RequestBody PlantImageRequest plantImageRequest) {
        PlantImageResponse plantImageResponse = plantImageService.analyzePlantImage(plantImageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(plantImageResponse);
    }


    @PostMapping("usuario/{id}")
    public ResponseEntity<AnaliseResponse> registrarAnaliseUsuario(@PathVariable Long id, @RequestBody PlantImageRequest plantImageRequest) {
        AnaliseResponse analise = analiseService.registrarAnaliseUsuario(id, plantImageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(analise);
    }

    @PostMapping("{id}")
    public ResponseEntity<Analise> registrar(@PathVariable Long id, @RequestBody @Valid Analise analise) {
        Analise novaAnalise = analiseService.registrar(id, analise);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAnalise);
    }

    @GetMapping("usuario/{id}")
    public List<Analise> findAllByUsuarioId(@PathVariable("id") Long usuarioId) {
        return analiseService.findAnalisesPorUsuarioId(usuarioId);
    }

    @GetMapping("doenca/{doenca}")
    public List<Analise> findAllByDoenca(@PathVariable("doenca") String doenca) {
        return analiseService.findAnalisesPorDoenca(doenca);
    }

    @GetMapping("planta/{planta}/usuario/{nome}")
    public List<Analise> findAllByPlantaAndUsuarioNome(@PathVariable String planta, @PathVariable String nome) {
        return analiseService.findAnalisesPorPlantaEUsuario(planta, nome);
    }

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String planta, @PageableDefault(size = 5) Pageable pageable) {
        return analiseService.findAllAnalises(planta, pageable);
    }

    @GetMapping("{id}")
    public EntityModel<Analise> show(@PathVariable Long id) {
        Analise analise = analiseService.findAnaliseById(id);
        return analise.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        analiseService.deleteAnalise(id);
        return ResponseEntity.noContent().build();
    }
}
