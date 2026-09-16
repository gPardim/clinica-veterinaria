package br.edu.pucgoias.ads1253.clinica.model;

import java.time.LocalDate;

/**
 * ETAPA 2 - Mapeamento da entidade Animal.
 *
 * Utilize a classe Tutor como referencia e complete o mapeamento:
 *  - anote a classe como entidade da tabela "animal";
 *  - declare o identificador com geracao automatica pelo banco;
 *  - mapeie nome (obrigatorio, 60), especie (obrigatoria, 40) e dataNascimento;
 *  - mapeie o lado "muitos" do relacionamento com Tutor, usando a coluna
 *    tutor_id como chave estrangeira e carregamento preguicoso (LAZY).
 */
public class Animal {

    private Long id;

    private String nome;

    private String especie;

    private LocalDate dataNascimento;

    private Tutor tutor;

    protected Animal() {
    }

    public Animal(String nome, String especie, LocalDate dataNascimento) {
        this.nome = nome;
        this.especie = especie;
        this.dataNascimento = dataNascimento;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "Animal{id=%d, nome='%s', especie='%s'}".formatted(id, nome, especie);
    }
}
