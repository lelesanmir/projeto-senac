package com.projeto.senac.Enum;

public enum Curso {
	 
	ADMINISTRACAO("Administracao"), 
	ADS("ADS"), 
	CONTABILIDADE("Contabilidade"),
	ENFERMAGEM("Enfermagem"),
	MARKETING("Marketing");

	private String curso;

	private Curso(String curso) {
		this.curso = curso;
	}

}
