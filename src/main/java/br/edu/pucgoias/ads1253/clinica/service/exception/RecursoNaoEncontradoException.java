package br.edu.pucgoias.ads1253.clinica.service.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super("%s nao encontrado para o identificador %d".formatted(recurso, id));
    }
}
