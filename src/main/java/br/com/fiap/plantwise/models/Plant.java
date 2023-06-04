package br.com.fiap.plantwise.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record Plant (String disease, String description) { }
