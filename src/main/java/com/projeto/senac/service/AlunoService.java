package com.projeto.senac.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.senac.model.Aluno;
import com.projeto.senac.repository.AlunoRepository;

@Service
public class AlunoService {
	@Autowired
	private AlunoRepository alunoRepository;

	public String geraMatricula(int id) {
		Date data = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		int year = cal.get(Calendar.YEAR);
		String txt = "SENAC";
		return (txt + year + (id + 1));// A Primeira matricula SENAC20231, Segunda SENAC20232
	}// Fim gerador de matricula

	public String cadastrarAluno(Aluno aluno) {
		// Verificar se já existe um aluno com o mesmo CPF
		Aluno alunoExistente = alunoRepository.findByCpf(aluno.getCpf());
		if (alunoExistente == null) {
			Aluno aux = alunoRepository.findLastInsertedAluno();
			if (aux == null) {
				// primeiro aluno no BD
				aluno.setMatricula(this.geraMatricula(0));
			} else {
				aluno.setMatricula(this.geraMatricula(Integer.parseInt(aux.getId().toString())));
			}
			alunoRepository.save(aluno);
		} else {
			// Um aluno com o mesmo CPF já existe, então você pode avisar.
			return ("Já existe um aluno com o mesmo CPF.");
		}
		return null;
	}

	public String alterarAluno(Aluno aluno, Long id) {
		// Verificar se já existe um aluno com o mesmo CPF
		Aluno alunoExistente = alunoRepository.findByCpf(aluno.getCpf());

			if ((alunoExistente != null && alunoExistente.getId() == id) || alunoExistente == null) {
				alunoRepository.save(aluno);
			} else {// significa que o cpf alterado já é de outro aluno no BD
				return "Já existe um aluno cadastrado com o mesmo CPF!";
			}
		return null;
	}

}