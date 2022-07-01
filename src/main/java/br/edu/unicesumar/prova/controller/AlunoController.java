package br.edu.unicesumar.prova.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unicesumar.prova.domain.Aluno;
import br.edu.unicesumar.prova.service.AlunoService;
import lombok.SneakyThrows;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @GetMapping
    public ResponseEntity<Page<Aluno>> buscarTodosPorNome(@RequestParam(name = "nome", required = false, defaultValue = "") String nome, Pageable pageable) {
        return ResponseEntity.ok(service.findAll(nome, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable("id") Long id) {

        Optional<Aluno> alunoOptional = service.buscarPorId(id);
        if (alunoOptional.isPresent()) {
            return ResponseEntity.ok(alunoOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @SneakyThrows
    public ResponseEntity<Aluno> salvar(@RequestBody Aluno aluno) {
        Aluno alunoSalvo = service.save(aluno);
        return ResponseEntity.created(new URI("/aluno/" + alunoSalvo.getId())).body(alunoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable("id") Long id, @RequestBody Aluno aluno) {
        if (!id.equals(aluno.getId())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(service.update(aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
