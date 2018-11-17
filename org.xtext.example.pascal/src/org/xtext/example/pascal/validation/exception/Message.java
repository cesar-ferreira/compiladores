package org.xtext.example.pascal.validation.exception;

public enum Message {
	UNEXPECTED_ERROR("Erro inesperado."),
	DECLARED_VARIABLE("Vari�vel j� declarada com este nome."),
	DECLARED_PROCEDURE("Procedure j� declarada com este nome."),
	DECLARED_FUNCTION("Fun��o j� declarada com este nome."),
	UNDECLARED_VARIABLE("Vari�vel n�o declarada."),
	INVALID_TERM("Termo da express�o inv�lido."),
	INVALID_TYPE("Tipo inv�lido."),
	PARAMS_EQUALS("Par�metros n�o podem ter o mesmo nome."),
	INVALID_ATTRIBUTION("Tipo e atribui��o n�o compat�veis."),
	INVALID_EXPRESSION("Express�o aritm�tica inv�lida."),
	ARITHMETIC_INVALID_OPERATION("Tipo da vari�vel n�o compat�vel com opera��o aritm�tica."),
	ARITHMETIC_INVALID_REAL("Express�o 'real' n�o aplicada a vari�vel 'integer'"),
	ARITHMETIC_INVALID_DIV("Operador 'div' aplicado apenas para 'integer'."),
	CASE_INVALID_EXPRESSION("Expressao deve ser dos tipos 'char', 'integer' ou 'boolean'"),
	CASE_INVALID_OPTION("Op��o n�o compat�vel com o tipo da express�o."),
	BOOLEAN_OP_REL_INVALID("Operador relacional suporta apenas dois operandos."),
	BOOLEAN_INVALID_TYPE("Operador relacional n�o aplicado aos tipos envolvidos."),
	BOOLEAN_INVALID_EXPRESSION("Tipos diferentes n�o s�o compat�veis para a opera��o relacional."),
	BOOLEAN_INVALID_ATTRIBUTION("Express�o 'boolean' s� pode ser atribu�da a vari�vel 'boolean'."),
	BOOLEAN_INVALID_OPERATORS("Operadores 'boolean' s� podem ser aplicados a operandos do tipo 'boolean'");
	
	
	private String content;
	
	private Message(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
}