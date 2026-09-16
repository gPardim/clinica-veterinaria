package br.edu.pucgoias.ads1253.clinica.service;

import br.edu.pucgoias.ads1253.clinica.model.Animal;
import br.edu.pucgoias.ads1253.clinica.model.Tutor;
import br.edu.pucgoias.ads1253.clinica.repository.AnimalRepository;
import br.edu.pucgoias.ads1253.clinica.repository.TutorRepository;
import br.edu.pucgoias.ads1253.clinica.service.exception.CpfDuplicadoException;
import br.edu.pucgoias.ads1253.clinica.service.exception.RecursoNaoEncontradoException;
import br.edu.pucgoias.ads1253.clinica.service.exception.TutorComAnimaisException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClinicaService {

    private final TutorRepository tutorRepository;
    private final AnimalRepository animalRepository;

    public ClinicaService(TutorRepository tutorRepository, AnimalRepository animalRepository) {
        this.tutorRepository = tutorRepository;
        this.animalRepository = animalRepository;
    }

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

    @Transactional(readOnly = true)
    public List<Tutor> listarTutores() {
        return tutorRepository.findAll();
    }

    @Transactional
    public Tutor atualizarTutor(Long id, String novoNome, String novoTelefone) {
        Tutor tutor = buscarTutor(id);
        tutor.setNome(novoNome);
        tutor.setTelefone(novoTelefone);
        return tutorRepository.save(tutor);
    }

    @Transactional
    public void removerTutor(Long id) {
        Tutor tutor = buscarTutor(id);
        garantirTutorSemAnimaisVinculados(tutor);
        tutorRepository.delete(tutor);
    }

    private void garantirTutorSemAnimaisVinculados(Tutor tutor) {
        long quantidadeDeAnimais = animalRepository.countByTutorId(tutor.getId());
        if (quantidadeDeAnimais > 0) {
            throw new TutorComAnimaisException(tutor.getId(), quantidadeDeAnimais);
        }
    }

    @Transactional
    public Animal cadastrarAnimal(Long tutorId, Animal animal) {
        Tutor tutor = buscarTutor(tutorId);
        animal.setTutor(tutor);
        return animalRepository.save(animal);
    }

    @Transactional(readOnly = true)
    public List<Animal> listarAnimaisDoTutor(Long tutorId) {
        return animalRepository.findByTutorId(tutorId);
    }

    @Transactional(readOnly = true)
    public List<Animal> buscarAnimaisPorEspecie(String especie) {
        return animalRepository.findByEspecieIgnoreCase(especie);
    }

    @Transactional
    public void removerAnimal(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new RecursoNaoEncontradoException("Animal", animalId);
        }
        animalRepository.deleteById(animalId);
    }
}