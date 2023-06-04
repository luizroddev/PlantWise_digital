package br.com.fiap.plantwise.models;

import br.com.fiap.plantwise.controllers.AnaliseController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_PW_ANALISE")
public class Analise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_analise")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_usuario")
    @JsonIgnore
    private Usuario usuario;

    private boolean doencaEncontrada;

    @Column(name = "nm_doenca")
    private String doenca;

    @NotBlank(message = "O nome da planta é obrigatório")
    @NotNull
    @Column(name = "nm_planta")
    private String planta;

    public EntityModel<Analise> toEntityModel() {
        return EntityModel.of(
                this,
                linkTo(methodOn(AnaliseController.class).show(id)).withSelfRel(),
                linkTo(methodOn(AnaliseController.class).delete(id)).withRel("delete"),
                linkTo(methodOn(AnaliseController.class).index(null, Pageable.unpaged())).withRel("all")
        );
    }
}
