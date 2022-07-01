package br.edu.unicesumar.prova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.unicesumar.prova.domain.Aluno;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback
public class AlunoControllerIntTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void criarUmAlunoTest() throws Exception {
		
		Aluno aluno = new Aluno(null, "123", "Teste", LocalDate.of(2001, 12, 8), new ArrayList<>()); //cria o objeto aluno
		
		String jsonAluno = objectMapper.writeValueAsString(aluno);//cria o json para o objeto aluno criado
		
		MvcResult andReturn = mockMvc.perform(post("/aluno")//performa uma simulação similar ao usar o postman
				.contentType(MediaType.APPLICATION_JSON)//diz que a forma do body é json
				.content(jsonAluno))//passa o json do aluno
		.andExpect(status().is2xxSuccessful())//espera um retorno de sucesso (200)
		.andReturn();// força o retorno para a variavel acima andReturn
		
		String corpoDaResposta = andReturn.getResponse().getContentAsString();
		
		Aluno alunoResposta = objectMapper.readValue(corpoDaResposta, Aluno.class);
		
		assertNotNull(alunoResposta.getId());
	}
	
	@Test
	public void buscarAlunoPeloId() throws Exception{
		Aluno aluno = new Aluno(null, "123", "Teste", LocalDate.of(2001, 12, 8), new ArrayList<>()); //cria o objeto aluno
		
		String jsonAluno = objectMapper.writeValueAsString(aluno);//cria o json para o objeto aluno criado
		
		MvcResult andReturn = mockMvc.perform(post("/aluno")//performa uma simulação similar ao usar o postman
				.contentType(MediaType.APPLICATION_JSON)//diz que a forma do body é json
				.content(jsonAluno))//passa o json do aluno
		.andExpect(status().is2xxSuccessful())//espera um retorno de sucesso (200)
		.andReturn();// força o retorno para a variavel acima andReturn
		
		String corpoDaResposta = andReturn.getResponse().getContentAsString();
		
		Aluno alunoResposta = objectMapper.readValue(corpoDaResposta, Aluno.class);
		
		MvcResult alunoIdRetorno = mockMvc.perform(get("/aluno" + alunoResposta.getId()))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		
	
	}
}
