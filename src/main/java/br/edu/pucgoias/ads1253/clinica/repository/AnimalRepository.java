package br.edu.pucgoias.ads1253.clinica.repository;

import br.edu.pucgoias.ads1253.clinica.model.Animal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByTutorId(Long tutorId);

    List<Animal> findByEspecieIgnoreCase(String especie);

    long countByTutorId(Long tutorId);
}