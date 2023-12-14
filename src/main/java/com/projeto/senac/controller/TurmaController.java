package com.projeto.senac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.senac.Enum.Status;
import com.projeto.senac.model.Aluno;
import com.projeto.senac.model.Professor;
import com.projeto.senac.model.Turma;
import com.projeto.senac.repository.AlunoRepository;
import com.projeto.senac.repository.ProfessorRepository;
import com.projeto.senac.repository.TurmaRepository;
import com.projeto.senac.service.TurmaService;

@Controller
public class TurmaController {

	@Autowired
	ProfessorRepository professorRepository;
	@Autowired
	TurmaRepository turmaRepository;
	@Autowired
	TurmaService turmaService;
	@Autowired
	AlunoRepository alunoRepository;

	@GetMapping("/carregarTurma")
	public ModelAndView carregarTurma() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("professores", professorRepository.findAllOrderedById());
		mv.addObject("turma", new Turma());
		mv.setViewName("Turma/turmaInsert");
		return mv;
	}

	@PostMapping("/inserirTurma")
	public ModelAndView carregarTurma(Turma turma) {
		ModelAndView mv = new ModelAndView();
		String out = turmaService.cadastrarTurma(turma);
		if (out != null) {
			mv.addObject("professores", professorRepository.findAllOrderedById());
			mv.addObject("turma", new Turma());
			mv.addObject("msg", out);
			mv.setViewName("Turma/turmaInsert");
		} // fim if
		else {// não existe turma cadastrada, logo posso inserir uma nova turma
			List<Aluno> alunos = alunoRepository.findByTurnoECursoEStatus(turma.getTurno(), turma.getCurso(),
					Status.ATIVO);
			Professor professor = professorRepository.findById(turma.getProfessor().getId()).get();
			turma.setProfessor(professor);
			mv.addObject("alunos", alunos);
			turma.setAlunos(alunos);
			mv.addObject("turma", turma);
			mv.setViewName("Turma/inserirAlunosTurma");
		} // fim else
		return mv;
	}// fim carregarTurma Post
	
	@PostMapping("/inserirAlunosTurma")
	public ModelAndView inserirAlunosTurma(Turma turma) {
		ModelAndView mv = new ModelAndView();
		turmaRepository.save(turma);
		mv.setViewName("redirect:/home");
		return mv;
	}
  
}





