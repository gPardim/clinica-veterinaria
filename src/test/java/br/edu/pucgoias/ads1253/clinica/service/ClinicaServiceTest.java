package br.edu.pucgoias.ads1253.clinica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.pucgoias.ads1253.clinica.model.Animal;
import br.edu.pucgoias.ads1253.clinica.model.Tutor;
import br.edu.pucgoias.ads1253.clinica.service.exception.CpfDuplicadoException;
import br.edu.pucgoias.ads1253.clinica.service.exception.RecursoNaoEncontradoException;
import br.edu.pucgoias.ads1253.clinica.service.exception.TutorComAnimaisException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ClinicaServiceTest {

    @Autowired
    private ClinicaService servico;

    private Tutor novoTutor(String nome, String cpf) {
        return new Tutor(nome, cpf, "62999990000");
    }

    @Test
    @DisplayName("1. Cadastro de tutor atribui identificador gerado pelo banco")
    void cadastroDeTutorAtribuiIdentificador() {
        Tutor salvo = servico.cadastrarTutor(novoTutor("Ana Souza", "11111111111"));

        assertNotNull(salvo.getId());
        assertEquals("Ana Souza", servico.buscarTutor(salvo.getId()).getNome());
    }

    @Test
    @DisplayName("2. Cadastro com CPF ja existente lanca CpfDuplicadoException")
    void cadastroComCpfDuplicadoLancaExcecao() {
        servico.cadastrarTutor(novoTutor("Ana Souza", "22222222222"));

        CpfDuplicadoException erro = assertThrows(CpfDuplicadoException.class,
                () -> servico.cadastrarTutor(novoTutor("Bruno Lima", "22222222222")));

        assertTrue(erro.getMessage().contains("22222222222"));
    }

    @Test
    @DisplayName("3. Busca por identificador inexistente lanca RecursoNaoEncontradoException")
    void buscaPorIdentificadorInexistenteLancaExcecao() {
        assertThrows(RecursoNaoEncontradoException.class, () -> servico.buscarTutor(9999L));
    }

    @Test
    @DisplayName("4. Cadastro de animal vincula o registro ao tutor informado")
    void cadastroDeAnimalVinculaAoTutor() {
        Tutor tutor = servico.cadastrarTutor(novoTutor("Carla Dias", "33333333333"));

        Animal animal = servico.cadastrarAnimal(tutor.getId(),
                new Animal("Rex", "Cao", LocalDate.of(2022, 3, 15)));

        assertNotNull(animal.getId());
        assertEquals(tutor.getId(), animal.getTutor().getId());
    }

    @Test
    @DisplayName("5. Atualizacao de tutor persiste as alteracoes")
    void atualizacaoDeTutorPersisteAlteracoes() {
        Tutor tutor = servico.cadastrarTutor(novoTutor("Diego Alves", "44444444444"));

        Tutor atualizado = servico.atualizarTutor(tutor.getId(), "Diego A. Souza", "62988887777");

        assertEquals(tutor.getId(), atualizado.getId());
        assertEquals("Diego A. Souza", servico.buscarTutor(tutor.getId()).getNome());
        assertEquals("62988887777", servico.buscarTutor(tutor.getId()).getTelefone());
    }

    @Test
    @DisplayName("6. Remocao de tutor com animais vinculados lanca TutorComAnimaisException")
    void remocaoDeTutorComAnimaisLancaExcecao() {
        Tutor tutor = servico.cadastrarTutor(novoTutor("Elisa Prado", "55555555555"));
        servico.cadastrarAnimal(tutor.getId(), new Animal("Bidu", "Cao", LocalDate.of(2021, 5, 10)));

        TutorComAnimaisException erro = assertThrows(TutorComAnimaisException.class,
                () -> servico.removerTutor(tutor.getId()));

        assertTrue(erro.getMessage().contains(tutor.getId().toString()));
    }

    @Test
    @DisplayName("7. Listagem retorna apenas os animais do tutor informado")
    void listagemRetornaApenasAnimaisDoTutor() {
        Tutor tutor1 = servico.cadastrarTutor(novoTutor("Fabio Nunes", "66666666666"));
        Tutor tutor2 = servico.cadastrarTutor(novoTutor("Gabriela Reis", "77777777777"));

        servico.cadastrarAnimal(tutor1.getId(), new Animal("Mel", "Gato", LocalDate.of(2020, 1, 1)));
        servico.cadastrarAnimal(tutor2.getId(), new Animal("Thor", "Cao", LocalDate.of(2019, 6, 20)));

        List<Animal> animaisDoTutor1 = servico.listarAnimaisDoTutor(tutor1.getId());

        assertEquals(1, animaisDoTutor1.size());
        assertEquals("Mel", animaisDoTutor1.get(0).getNome());
    }
}