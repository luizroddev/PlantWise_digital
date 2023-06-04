package br.com.fiap.plantwise.exceptions;

public record RestError (
        int cod,
        String message
) {}
