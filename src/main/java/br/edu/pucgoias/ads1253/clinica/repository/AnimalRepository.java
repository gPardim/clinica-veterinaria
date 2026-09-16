package br.edu.pucgoias.ads1253.clinica.repository;

import br.edu.pucgoias.ads1253.clinica.model.Animal;

/**
 * ETAPA 3 - Repositorio da entidade Animal.
 *
 * Utilize TutorRepository como referencia:
 *  - faca esta interface estender JpaRepository<Animal, Long>;
 *  - declare as consultas derivadas necessarias a atividade:
 *      * lista de animais de um tutor (a partir do identificador do tutor);
 *      * lista de animais por especie, ignorando maiusculas e minusculas;
 *      * contagem de animais vinculados a um tutor.
 *
 * Nenhuma implementacao deve ser escrita: o Spring Data JPA a gera em
 * tempo de execucao a partir dos nomes dos metodos.
 */
public interface AnimalRepository {
}
