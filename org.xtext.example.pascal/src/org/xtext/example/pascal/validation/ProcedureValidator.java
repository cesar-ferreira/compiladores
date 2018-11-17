package org.xtext.example.pascal.validation;

import java.util.ArrayList;
import java.util.List;

import org.xtext.example.pascal.pascal.assignment_statement;
import org.xtext.example.pascal.pascal.block;
import org.xtext.example.pascal.pascal.declaration_part;
import org.xtext.example.pascal.pascal.formal_parameter_list;
import org.xtext.example.pascal.pascal.formal_parameter_section;
import org.xtext.example.pascal.pascal.procedure_block;
import org.xtext.example.pascal.pascal.procedure_body;
import org.xtext.example.pascal.pascal.procedure_heading;
import org.xtext.example.pascal.pascal.procedure_identification;
import org.xtext.example.pascal.pascal.statement;
import org.xtext.example.pascal.pascal.statement_part;
import org.xtext.example.pascal.pascal.variable_parameter_section;
import org.xtext.example.pascal.validation.exception.InvalidException;
import org.xtext.example.pascal.validation.exception.Message;
import org.xtext.example.pascal.validation.obj.Procedure;
import org.xtext.example.pascal.validation.obj.Variable;

public class ProcedureValidator {

	public static void validateDeclarationProcedure(block block, procedure_heading procedure_heading) {
		
		String procedureName = procedure_heading.getIdentifier().getIdentifier();
		List<Variable> params = new ArrayList<>();
		formal_parameter_list formalParameter = procedure_heading.getFormal_parameter_list();
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
		
		Procedure newProcedure = new Procedure(procedureName, params);
				
		if(BlockValidator.hasProcedure(block, newProcedure)) {
			BlockValidator.addError(new InvalidException(Message.DECLARED_PROCEDURE, procedure_heading));
		}
		BlockValidator.addProcedure(newProcedure);
		
	}
	
	public static void validateDeclarationProcedure(block block, procedure_identification procedure_identification) {
		
		String name = procedure_identification.getIdentifier().getIdentifier();
		Procedure newProcedure = new Procedure(name, new ArrayList<>());
				
		if(BlockValidator.hasProcedure(block, newProcedure)) {
			BlockValidator.addError(new InvalidException(Message.DECLARED_PROCEDURE, procedure_identification));
		}
		BlockValidator.addProcedure(newProcedure);
		
	}
	
	public static void verifyProcedureVariables(declaration_part declarationPart) {
		
			
		int index = 0;
		for (procedure_body procedureBody : declarationPart.getProcedure_body()) {
			
			if (!BlockValidator.getProceduresList().isEmpty()) {
				List<String> listParamsProcedure = BlockValidator.getProceduresList().get(index).getNamesList();
				
				procedure_block procedureBlock = procedureBody.getProcedure_block();
				if (procedureBlock != null) {
					statement_part statement_part = procedureBlock.getStatement_part() ;
					if (statement_part != null) {
						
						for (statement statement : statement_part.getStatement_sequence().getStatement()) {
							
							if (statement.getSimple_statement() != null && statement.getSimple_statement().getAssignment_statement() != null 
									&&  statement.getSimple_statement().getAssignment_statement().getVariable() != null) {							
								
								assignment_statement assignment_statement = statement.getSimple_statement().getAssignment_statement();
								String variableName = assignment_statement.getVariable().getEntire_variable().getIdentifier().getIdentifier();
								Variable newVariable = new Variable(variableName);							
								if (!BlockValidator.hasVariable(null, newVariable) && !listParamsProcedure.contains(variableName) ) {
									BlockValidator.addError(new InvalidException(Message.UNDECLARED_VARIABLE, statement));
								} 
								
					
								String paramenterType = Procedure.getTypeParameter(variableName);									
								
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
