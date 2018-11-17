package org.xtext.example.pascal.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.xtext.example.pascal.pascal.*;
import org.xtext.example.pascal.validation.exception.InvalidException;
import org.xtext.example.pascal.validation.exception.Message;
import org.xtext.example.pascal.validation.obj.Function;
import org.xtext.example.pascal.validation.obj.Procedure;
import org.xtext.example.pascal.validation.obj.Variable;

public class BlockValidator {

	private static final String[] ARRAY_TYPES = { "string", "char", "integer", "shortint", "longint", "byte", "single",
			"extended", "word", "double", "real", "comp", "boolean" };

	private static List<String> declaredTypes;
	private static List<String> typeList;
	private static List<Variable> variablesList;
	private static List<Procedure> proceduresList;
	private static List<Function> functionsList;
	private static List<InvalidException> errorList;

	private static void init() {

		typeList = Arrays.asList(ARRAY_TYPES);
		declaredTypes = new ArrayList<>();
		variablesList = new ArrayList<>();
		proceduresList = new ArrayList<>();
		functionsList = new ArrayList<>();
		errorList = new ArrayList<>();
	}

	public static void validateBlock(block block) {
		init();
		addField(block, block.getDeclaration_part());
		verifyVariables(block.getStatement_part());		
	}

	private static void verifyVariables(statement_part statement_part) {
		if (statement_part!= null && statement_part.getStatement_sequence() != null) {
			for (statement statement : statement_part.getStatement_sequence().getStatement()) {
				simple_statement simpleStatement = statement.getSimple_statement();
				if (simpleStatement != null && simpleStatement.getAssignment_statement() != null) {
					String name = simpleStatement.getAssignment_statement().getVariable().getEntire_variable()
							.getIdentifier().getIdentifier();

					if (name != null && !hasVariable(null, new Variable(name))) {
						addError(new InvalidException(Message.UNDECLARED_VARIABLE,
								statement.getSimple_statement().getAssignment_statement().getVariable()));
					}
				}

			}
		}
	}

	public static void addField(block block, declaration_part declarationPart) {

		if (declarationPart != null) {
			if (declarationPart.getVariable_declaration_part() != null) {
				if (declarationPart != null && declarationPart.getType_definition_part() != null && declarationPart.getType_definition_part().getType_definition() != null) {
					for (type_definition type_definition : declarationPart.getType_definition_part().getType_definition()) {
						addDeclaredType(type_definition.getIdentifier().getIdentifier());
					}
				}
				

				VariableValidator.validateDeclarationVariable(block, declarationPart);

			}

			if (declarationPart != null && declarationPart.getProcedure_heading() != null) {

				EList<procedure_heading> procedures = declarationPart.getProcedure_heading();

				for (procedure_heading procedure : procedures) {
					ProcedureValidator.validateDeclarationProcedure(block, procedure);
				}
			} else if (declarationPart != null  && declarationPart.getProcedure_identification() != null) {

				EList<procedure_identification> procedures = declarationPart.getProcedure_identification();

				for (procedure_identification procedure : procedures) {
					ProcedureValidator.validateDeclarationProcedure(block, procedure);
				}
			}

			ProcedureValidator.verifyProcedureVariables(declarationPart);

			if (declarationPart.getFunction_heading() != null) {
				EList<function_heading> functions = declarationPart.getFunction_heading();

				for (function_heading function : functions) {
					FunctionValidator.validateDeclarationFunction(block, function);
				}
			} else if (declarationPart.getFunction_identification() != null) {
				EList<function_identification> functions = declarationPart.getFunction_identification();

				for (function_identification function : functions) {
					FunctionValidator.validateDeclarationFunction(block, function);
				}
			}
			FunctionValidator.verifyFunctionVariables(declarationPart);
		}

	}

	public static void addVariable(block block, Variable variable) {
		getVariables().add(variable);
	}

	public static void addProcedure(Procedure procedure) {
		getProceduresList().add(procedure);
	}

	public static void addFunction(Function function) {
		getFunctionsList().add(function);
	}

	public static void addError(InvalidException error) {
		getErrorList().add(error);
	}

	public static List<InvalidException> getErrorList() {
		if (errorList == null) {
			errorList = new ArrayList<>();
		}
		return errorList;
	}

	public static List<Function> getFunctionsList() {
		if (functionsList == null) {
			functionsList = new ArrayList<>();
		}
		return functionsList;
	}

	public static List<Variable> getVariables() {
		if (variablesList == null) {
			variablesList = new ArrayList<>();
		}
		return variablesList;
	}

	public static boolean hasVariable(block block, Variable variable) {
		return getVariables().contains(variable);
	}

	public static List<Procedure> getProceduresList() {
		if (proceduresList == null) {
			proceduresList = new ArrayList<>();
		}
		return proceduresList;
	}

	public static boolean hasProcedure(block block, Procedure procedure) {
		return getProceduresList().contains(procedure);
	}

	public static boolean hasFunction(block block, Function function) {
		return getFunctionsList().contains(function);
	}

	public static boolean isType(String string) {
		return typeList.contains(string);
	}

	public static void addDeclaredType(String newType) {
		declaredTypes.add(newType);
	}

	public static boolean hasDeclaredType(String type) {
		return declaredTypes.contains(type);
	}

	public static String getTypeVariable(String variableName) {
		for (Variable var : getVariables()) {
			if (var.getName().equals(variableName)) {
				return var.getType();
			}
		}
		return null;
	}

}
