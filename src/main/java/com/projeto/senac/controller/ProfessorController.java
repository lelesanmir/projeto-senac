package com.projeto.senac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.senac.model.Professor;
import com.projeto.senac.repository.ProfessorRepository;
import com.projeto.senac.service.ProfessorService;

@Controller
public class ProfessorController {

	@Autowired
	ProfessorService professorService;
	@Autowired
	ProfessorRepository professorRepository;

	@Controller
	public class HomeController {


		@GetMapping("/inserirProfessor")
		public ModelAndView insertProfessor() {
			ModelAndView mv = new ModelAndView();
			Professor obj = new Professor();
			mv.addObject("professor", obj);
			mv.setViewName("Professor/inserirProfessor");// Aqui seleciona a pasta e o arquivo
			return mv;
		}

		@PostMapping("/inserirProfessor")
		public ModelAndView Inserir(Professor professor) {
			ModelAndView mv = new ModelAndView();

			String out = professorService.cadastrarProfessor(professor);

			if (out != null) {
				mv.addObject("professor", new Professor());
				mv.addObject("msg", out);
				mv.setViewName("Professor/inserirProfessor");
			} else {
				mv.addObject("professores", professorRepository.findAllOrderedById());
				mv.setViewName("redirect:/listarProfessores");
			}
			return mv;
		}

		@GetMapping("/listarProfessores")
		public ModelAndView listarProfessores() {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("Professor/listarProfessores");
			mv.addObject("professores", professorRepository.findAllOrderedById());

			return mv;
		}

		@GetMapping("/listarAlterarProfessores")
		public ModelAndView listarAlterarProfessores() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("professores", professorRepository.findAllOrderedById());
			mv.setViewName("Professor/listarAlterar");
			return mv;
		}

		@GetMapping("/alterarProfessor/{id}")
		public ModelAndView alterarProfessor(@PathVariable("id") Long id) {
			ModelAndView mv = new ModelAndView();
			Professor professor = professorRepository.findById(id).get();
			mv.addObject("professor", professor);
			mv.setViewName("Professor/alterar");
			return mv;
		}

		@PostMapping("/alterarProfessor")
		public ModelAndView alterarProfessor(Professor professor) {
			ModelAndView mv = new ModelAndView();
			try {
				String out = professorService.alterarProfessor(professor, professor.getId());

				if (out != null) {
					// Não foi possível atualizar o professor, o CPF pertence a outro professor
					mv.addObject("msg", out);
					mv.addObject("professor", professor);
					mv.setViewName("Professor/alterar");
				} else {
					// Professor atualizado com sucesso
					mv.addObject("professores", professorRepository.findAllOrderedById());
					mv.setViewName("Professor/listarAlterar");
				}
			} catch (Exception e) {
				// Lidar com exceções aqui, pode ser uma exceção de serviço ou outra coisa
				mv.addObject("error", "Ocorreu um erro ao tentar atualizar o professor.");
				mv.setViewName("error"); // Defina uma página de erro apropriada
			}
			return mv;
		}

		@GetMapping("/listarExluirProfessores")
		public ModelAndView listarExcluirProfessores() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("professores", professorRepository.findAllOrderedById());
			mv.setViewName("Professor/listarExcluir");
			return mv;
		}

		@GetMapping("/excluirProfessor/{id}")
		public String excluirProfessor(@PathVariable("id") Long id) {

			professorRepository.deleteById(id);
			return "redirect:/listarExluirProfessores";
		}

	}
}
