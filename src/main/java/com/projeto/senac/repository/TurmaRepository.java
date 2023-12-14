package com.projeto.senac.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projeto.senac.model.Turma;

@Repository
public interface TurmaRepository extends CrudRepository<Turma, Long> {

	@Query("SELECT t FROM Turma t WHERE t.codTurma = codTurma")
	public Turma findByCodTurma(String codTurma);

}
