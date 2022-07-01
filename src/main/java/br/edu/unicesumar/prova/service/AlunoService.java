package br.edu.unicesumar.prova.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.unicesumar.prova.domain.Aluno;
import br.edu.unicesumar.prova.repository.AlunoRepository;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository repository;

    public Optional<Aluno> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Page<Aluno> findAll(String nome, Pageable pageable) {
        return this.repository.findByNomeIgnoreCaseContaining(nome, pageable);
    }

    public Aluno save(Aluno aluno) {
        if (this.repository.existsByMatricula(aluno.getMatricula())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Matrícula já utilizada!");
        }
        return this.repository.save(aluno);
    }

    public Aluno update(Aluno aluno) {
        Aluno alunoBancoDeDados = this.repository.findById(aluno.getId()).orElse(null);
        if (alunoBancoDeDados == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado!");
        }

        if (alunoBancoDeDados.getMatricula().equals(aluno.getMatricula())) {
            return this.repository.save(aluno);
        }

        return this.save(aluno);
    }

    public void delete(Long id) {
        if (!this.repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado!");
        }

        this.repository.deleteById(id);
    }

}
