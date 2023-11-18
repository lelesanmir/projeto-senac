package com.projeto.senac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.senac.model.Professor;
import com.projeto.senac.repository.ProfessorRepository;

@Service
public class ProfessorService {

	@Autowired
	ProfessorRepository professorRepository;

	public String cadastrarProfessor(Professor professor) {

		// verifica se existe um cpf na tabela professor
		Professor profExiste = professorRepository.findByCpf(professor.getCpf());
		if (profExiste != null) {
			return "JÁ EXISTE UM PROFESSOR CADASTRADO COM O MESMO CPF CADASTRADO!";
		} else {
			professorRepository.save(professor);
		}

		return null;
	}

	public String alterarProfessor(Professor professor, long id) {
	    Professor professorExistente = professorRepository.findByCpf(professor.getCpf());

	    if ((professorExistente != null && professorExistente.getId() == id) || professorExistente == null) {
	        // Se o professor existente for o mesmo do ID fornecido ou se não houver nenhum professor com esse CPF
	        professorRepository.save(professor);
	    } else {
	        return "Já existe um professor cadastrado com o mesmo CPF!";
	    }
	    return null;
	}


}
