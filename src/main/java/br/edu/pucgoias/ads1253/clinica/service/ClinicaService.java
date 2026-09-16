package br.edu.pucgoias.ads1253.clinica.service;

import br.edu.pucgoias.ads1253.clinica.model.Animal;
import br.edu.pucgoias.ads1253.clinica.model.Tutor;
import br.edu.pucgoias.ads1253.clinica.repository.TutorRepository;
import br.edu.pucgoias.ads1253.clinica.service.exception.CpfDuplicadoException;
import br.edu.pucgoias.ads1253.clinica.service.exception.RecursoNaoEncontradoException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ETAPA 4 - Camada de servico.
 *
 * As duas primeiras operacoes estao implementadas como referencia.
 * Implemente as demais, mantendo as assinaturas e as regras de negocio
 * descritas no material da aula. Substitua cada lancamento de
 * UnsupportedOperationException pela implementacao correspondente.
 *
 * Observacao: declare tambem o AnimalRepository como dependencia do
 * construtor apos concluir a ETAPA 3.
 */
@Service
public class ClinicaService {

    private final TutorRepository tutorRepository;

    public ClinicaService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    // --- Implementado como referencia ---------------------------------------

    @Transactional
    public Tutor cadastrarTutor(Tutor tutor) {
        if (tutorRepository.existsByCpf(tutor.getCpf())) {
            throw new CpfDuplicadoException(tutor.getCpf());
        }
        return tutorRepository.save(tutor);
    }

    @Transactional(readOnly = true)
    public Tutor buscarTutor(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tutor", id));
    }

    // --- A implementar ------------------------------------------------------

    @Transactional(readOnly = true)
    public List<Tutor> listarTutores() {
        throw new UnsupportedOperationException("ETAPA 4: implementar");
    }

    /** Atualiza nome e telefone do tutor; identificador inexistente deve falhar. */
    @Transactional
    public Tutor atualizarTutor(Long id, String novoNome, String novoTelefone) {
        throw new UnsupportedOperationException("ETAPA 4: implementar");
    }

    /** Remove o tutor apenas se nao houver animais vinculados. */
    @Transactional
    public void removerTutor(Long id) {
        throw new UnsupportedOperationException("ETAPA 4: implementar");
    }

    /** Cadastra o animal vinculando-o ao tutor informado. */
    @Transactional
    public Animal cadastrarAnimal(Long tutorId, Animal animal) {
        throw new UnsupportedOperationException("ETAPA 4: implementar");
    }

    @Transactional(readOnly = true)
    public List<Animal> listarAnimaisDoTutor(Long tutorId) {
        throw new UnsupportedOperationException("ETAPA 4: implementar");
    }

    @Transactional(readOnly = true)
    public List<Animal> buscarAnimaisPorEspecie(String especie) {
        throw new UnsupportedOperationException("ETAPA 4: implementar");
    }

    @Transactional
    public void removerAnimal(Long animalId) {
        throw new UnsupportedOperationException("ETAPA 4: implementar");
    }
}
