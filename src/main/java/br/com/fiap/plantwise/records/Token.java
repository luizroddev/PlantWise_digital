package br.com.fiap.plantwise.records;

public record Token(
        String token,
        String type,
        String prefix
) {}
