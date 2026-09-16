package br.edu.pucgoias.ads1253.clinica.service.exception;

public class CpfDuplicadoException extends RuntimeException {

    public CpfDuplicadoException(String cpf) {
        super("Ja existe tutor cadastrado com o CPF " + cpf);
    }
}
