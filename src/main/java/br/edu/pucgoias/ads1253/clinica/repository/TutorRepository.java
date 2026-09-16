package br.edu.pucgoias.ads1253.clinica.repository;

import br.edu.pucgoias.ads1253.clinica.model.Tutor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    boolean existsByCpf(String cpf);

    Optional<Tutor> findByCpf(String cpf);

    List<Tutor> findByNomeContainingIgnoreCaseOrderByNomeAsc(String trecho);
}
