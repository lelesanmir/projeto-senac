package com.projeto.senac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.projeto.senac.model.Professor;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {

	@Query("SELECT a FROM Professor a WHERE a.cpf = :cpf")
	public Professor findByCpf(String cpf);

	@Query("SELECT a FROM Professor a ORDER BY a.id")
	public List<Professor> findAllOrderedById();
}
