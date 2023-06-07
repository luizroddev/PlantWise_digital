package br.com.fiap.plantwise.service;

import br.com.fiap.plantwise.models.Analise;
import br.com.fiap.plantwise.models.Usuario;
import br.com.fiap.plantwise.records.AnaliseResponse;
import br.com.fiap.plantwise.records.PlantImageRequest;
import br.com.fiap.plantwise.records.PlantImageResponse;
import br.com.fiap.plantwise.repositories.AnaliseRepository;
import br.com.fiap.plantwise.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class AnaliseService {

    @Autowired
    AnaliseRepository analiseRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PlantImageService plantImageService;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    public AnaliseResponse registrarAnaliseUsuario(Long id, PlantImageRequest plantImageRequest) {

        PlantImageResponse plantImageResponse = plantImageService.analyzePlantImage(plantImageRequest);
        Analise analise = new Analise();

        analise.setPlanta(plantImageResponse.analise().nome());
        analise.setDoenca(plantImageResponse.analise().doenca());
        analise.setDoencaEncontrada(!plantImageResponse.analise().doenca().equalsIgnoreCase("saudável"));
        registrar(id, analise);
        return new AnaliseResponse(analise, plantImageResponse.analise().descricao());
    }

    public Analise registrar(Long id, Analise analise) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        analise.setUsuario(usuario);
        usuario.getAnalises().add(analise);
        usuarioRepository.save(usuario);
        return analise;
    }

    public PagedModel<EntityModel<Object>> findAllAnalises(String planta, Pageable pageable) {
        log.info("Buscando análises");
        Page<Analise> analises = (planta == null) ?
                analiseRepository.findAll(pageable) :
                analiseRepository.findByPlantaContaining(planta, pageable);

        return assembler.toModel(analises.map(Analise::toEntityModel));
    }

    public Analise findAnaliseById(Long id) {
        log.info("Buscar análise com ID " + id);
        return analiseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Análise não encontrada"));
    }

    public void deleteAnalise(Long id) {
        log.info("Deletando análise");
        analiseRepository.delete(findAnaliseById(id));
    }
}
