package br.edu.unicesumar.prova.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unicesumar.prova.domain.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Page<Aluno> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

    boolean existsByMatricula(String matricula);

}
