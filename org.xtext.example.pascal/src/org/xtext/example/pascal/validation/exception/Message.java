package org.xtext.example.pascal.validation.exception;

public enum Message {
	UNEXPECTED_ERROR("Erro inesperado."),
	DECLARED_VARIABLE("Variável já declarada com este nome."),
	DECLARED_PROCEDURE("Procedure já declarada com este nome."),
	DECLARED_FUNCTION("Função já declarada com este nome."),
	UNDECLARED_VARIABLE("Variável não declarada."),
	INVALID_TERM("Termo da expressão inválido."),
	INVALID_TYPE("Tipo inválido."),
	PARAMS_EQUALS("Parâmetros não podem ter o mesmo nome."),
	INVALID_ATTRIBUTION("Tipo e atribuição não compatíveis."),
	INVALID_EXPRESSION("Expressão aritmética inválida."),
	ARITHMETIC_INVALID_OPERATION("Tipo da variável não compatível com operação aritmética."),
	ARITHMETIC_INVALID_REAL("Expressão 'real' não aplicada a variável 'integer'"),
	ARITHMETIC_INVALID_DIV("Operador 'div' aplicado apenas para 'integer'."),
	CASE_INVALID_EXPRESSION("Expressao deve ser dos tipos 'char', 'integer' ou 'boolean'"),
	CASE_INVALID_OPTION("Opção não compatível com o tipo da expressão."),
	BOOLEAN_OP_REL_INVALID("Operador relacional suporta apenas dois operandos."),
	BOOLEAN_INVALID_TYPE("Operador relacional não aplicado aos tipos envolvidos."),
	BOOLEAN_INVALID_EXPRESSION("Tipos diferentes não são compatíveis para a operação relacional."),
	BOOLEAN_INVALID_ATTRIBUTION("Expressão 'boolean' só pode ser atribuída a variável 'boolean'."),
	BOOLEAN_INVALID_OPERATORS("Operadores 'boolean' só podem ser aplicados a operandos do tipo 'boolean'");
	
	
	private String content;
	
	private Message(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
}