package com.projeto.senac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.projeto.senac.Enum.Curso;
import com.projeto.senac.Enum.Status;
import com.projeto.senac.model.Aluno;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {
	@Query("SELECT a FROM Aluno a WHERE a.id = (SELECT MAX(a2.id) FROM Aluno a2)")
	public Aluno findLastInsertedAluno();

	@Query("SELECT a FROM Aluno a WHERE a.cpf = :cpf")
	public Aluno findByCpf(String cpf);

	@Query("SELECT a FROM Aluno a ORDER BY a.id")
	public List<Aluno> findAllOrderedById();
	
	@Query("SELECT a FROM Aluno a WHERE a.turno = :turno AND a.curso = :curso AND a.status = :status ORDER BY a.nome")
	public List<Aluno> findByTurnoECursoEStatus(String turno, Curso curso, Status status);

}