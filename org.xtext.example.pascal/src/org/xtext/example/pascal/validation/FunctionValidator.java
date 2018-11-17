package org.xtext.example.pascal.validation;

import java.util.ArrayList;
import java.util.List;

import org.xtext.example.pascal.pascal.assignment_statement;
import org.xtext.example.pascal.pascal.block;
import org.xtext.example.pascal.pascal.declaration_part;
import org.xtext.example.pascal.pascal.formal_parameter_list;
import org.xtext.example.pascal.pascal.formal_parameter_section;
import org.xtext.example.pascal.pascal.function_block;
import org.xtext.example.pascal.pascal.function_body;
import org.xtext.example.pascal.pascal.function_heading;
import org.xtext.example.pascal.pascal.function_identification;
import org.xtext.example.pascal.pascal.statement;
import org.xtext.example.pascal.pascal.statement_part;
import org.xtext.example.pascal.pascal.variable_parameter_section;
import org.xtext.example.pascal.validation.exception.InvalidException;
import org.xtext.example.pascal.validation.exception.Message;
import org.xtext.example.pascal.validation.obj.Function;
import org.xtext.example.pascal.validation.obj.Variable;

public class FunctionValidator {

public static void validateDeclarationFunction(block block, function_heading function_heading) {
		
		String name = function_heading.getIdentifier().getIdentifier();
		
		String resultType = function_heading.getResult_type().getType_identifier().getIdentifier().getIdentifier();
		if (!BlockValidator.isType(resultType) && !BlockValidator.hasDeclaredType(resultType)) {
			BlockValidator.addError(new InvalidException(Message.INVALID_TYPE, function_heading.getResult_type()));
		}
		
		List<Variable> params = new ArrayList<>();
		formal_parameter_list formalParameter = function_heading.getFormal_parameter_list();
		if (formalParameter!= null && formalParameter.getFormal_parameter_section() != null) {
			for ( formal_parameter_section paramSection : formalParameter.getFormal_parameter_section()) {
				variable_parameter_section var = paramSection.getVariable_parameter_section();
				
				if (var != null) {
					for (String param : var.getIdentifier_list().getIdentifier()) {
						String type = var.getParameter_type().getType_identifier().getIdentifier().getIdentifier();
						if (type != null && !BlockValidator.isType(type) && !BlockValidator.hasDeclaredType(type)) {
							BlockValidator.addError(new InvalidException(Message.INVALID_TYPE, var.getParameter_type()));
						} else {
							Variable newVariable = new Variable(param, var.getParameter_type().getType_identifier().getIdentifier().getIdentifier());
							if (params.contains(newVariable)) {
								BlockValidator.addError(new InvalidException(Message.PARAMS_EQUALS, var));
							} else {
								params.add(newVariable);
							}					
							
						}						
					}					
				}
			}
		}	
		
		
		Function newFunction = new Function(name, params);
		
		if(BlockValidator.hasFunction(block, newFunction)) {
			BlockValidator.addError(new InvalidException(Message.DECLARED_FUNCTION, function_heading));
		}
		BlockValidator.addFunction(newFunction);
		
	}
	
	public static void validateDeclarationFunction(block block, function_identification function_identification) {
		
		String name = function_identification.getFunction_identifier().getIdentifier();
		Function newFunction = new Function(name, new ArrayList<>());
		
		if(BlockValidator.hasFunction(block, newFunction)) {
			BlockValidator.addError(new InvalidException(Message.DECLARED_FUNCTION, function_identification));
		}
		BlockValidator.addFunction(newFunction);
		
	}
	
	public static void verifyFunctionVariables(declaration_part declarationPart) {
		
		
		int index = 0;
		for (function_body functionBody : declarationPart.getFunction_body()) {
			
			if (!BlockValidator.getFunctionsList().isEmpty()) {
				List<String> listParamsFunction = BlockValidator.getFunctionsList().get(index).getNamesList();
				
				function_block functionBlock = functionBody.getFunction_block();
				if (functionBlock != null) {
					statement_part statement_part = functionBlock.getStatement_part() ;
					if (statement_part != null) {
						
						for (statement statement : statement_part.getStatement_sequence().getStatement()) {
							
							if (statement.getSimple_statement() != null && statement.getSimple_statement().getAssignment_statement() != null 
									&&  statement.getSimple_statement().getAssignment_statement().getVariable() != null) {							
								assignment_statement assignment_statement = statement.getSimple_statement().getAssignment_statement(); 
								String variableName = assignment_statement.getVariable().getEntire_variable().getIdentifier().getIdentifier();
								Variable newVariable = new Variable(variableName);							
								if (!BlockValidator.hasVariable(null, newVariable) && !listParamsFunction.contains(variableName) ) {
									BlockValidator.addError(new InvalidException(Message.UNDECLARED_VARIABLE, statement));
								}
								
								String paramenterType = Function.getTypeParameter(variableName);									
								
								ExpressionValidator.validateExpression(assignment_statement, paramenterType);
							}
							
						}
					}
					
				}
				
			}
			index ++;
			
				
		}
	}

}
