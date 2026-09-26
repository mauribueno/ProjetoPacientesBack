package br.com.fortec.service;

public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String cpf) { super("CPF já cadastrado: " + cpf); }
}
