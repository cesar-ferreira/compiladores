package org.xtext.example.pascal.validation;

import org.eclipse.emf.common.util.EList;
import org.xtext.example.pascal.pascal.block;
import org.xtext.example.pascal.pascal.declaration_part;
import org.xtext.example.pascal.pascal.variable_declaration;
import org.xtext.example.pascal.pascal.variable_declaration_part;
import org.xtext.example.pascal.validation.exception.InvalidException;
import org.xtext.example.pascal.validation.exception.Message;
import org.xtext.example.pascal.validation.obj.Variable;
import org.xtext.example.pascal.pascal.assignment_statement;

public class VariableValidator {
		
	public static void validateDeclarationVariable(block block, declaration_part declarationPart) {
		variable_declaration_part declaration_variable = (variable_declaration_part) declarationPart.getVariable_declaration_part();

		EList<variable_declaration> varibles_declaration =  declaration_variable.getVariable_declaration();

		for (variable_declaration v_declaration : varibles_declaration){
			EList<String> names = v_declaration.getIdentifier_list().getIdentifier();
			String type = v_declaration.getType().getType_identifier().getIdentifier().getIdentifier();
			
			if (type != null && !BlockValidator.isType(type) && !BlockValidator.hasDeclaredType(type)) {
				BlockValidator.addError(new InvalidException(Message.INVALID_TYPE, v_declaration));
			}			
			
			for (String name : names){
				Variable variable = new Variable(name, type);
				if (BlockValidator.hasVariable(block, variable)) {
					BlockValidator.addError(new InvalidException(Message.DECLARED_VARIABLE, v_declaration));
				} 
				BlockValidator.addVariable(block, variable);
			}
		}	
	}

	public static void validateVariable(assignment_statement statement) {

		if (statement.getVariable().getEntire_variable() != null) {
			String variableName = statement.getVariable().getEntire_variable().getIdentifier().getIdentifier();
			Variable variable = new Variable(variableName);
			
			if (!BlockValidator.hasVariable(null, variable)) {
				BlockValidator.addError(new InvalidException(Message.UNDECLARED_VARIABLE, statement));
			}
		}
		

	}
	
	
	
}
