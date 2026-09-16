package br.edu.pucgoias.ads1253.clinica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.pucgoias.ads1253.clinica.model.Animal;
import br.edu.pucgoias.ads1253.clinica.model.Tutor;
import br.edu.pucgoias.ads1253.clinica.service.exception.CpfDuplicadoException;
import br.edu.pucgoias.ads1253.clinica.service.exception.RecursoNaoEncontradoException;
import java.time.LocalDate;
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

    // ------------------------------------------------------------------
    // ETAPA 5 - Escreva os tres testes restantes, seguindo o padrao acima.
    // ------------------------------------------------------------------

    @Test
    @DisplayName("5. Atualizacao de tutor persiste as alteracoes")
    void atualizacaoDeTutorPersisteAlteracoes() {
        fail("Teste a ser escrito pelo aluno");
    }

    @Test
    @DisplayName("6. Remocao de tutor com animais vinculados lanca TutorComAnimaisException")
    void remocaoDeTutorComAnimaisLancaExcecao() {
        fail("Teste a ser escrito pelo aluno");
    }

    @Test
    @DisplayName("7. Listagem retorna apenas os animais do tutor informado")
    void listagemRetornaApenasAnimaisDoTutor() {
        fail("Teste a ser escrito pelo aluno");
    }
}
