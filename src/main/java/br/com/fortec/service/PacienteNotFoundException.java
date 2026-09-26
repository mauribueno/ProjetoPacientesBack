package br.com.fortec.service;

public class PacienteNotFoundException extends RuntimeException {
    public PacienteNotFoundException(Object identifier) { super("Paciente nao encontrado: " + identifier); }
}
