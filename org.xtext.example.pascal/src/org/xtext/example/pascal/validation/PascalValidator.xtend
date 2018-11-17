/*
 * generated by Xtext 2.10.0
 */
package org.xtext.example.pascal.validation

import org.eclipse.xtext.validation.Check
import org.xtext.example.pascal.pascal.assignment_statement
import org.xtext.example.pascal.pascal.block
import org.xtext.example.pascal.pascal.case_statement
import org.xtext.example.pascal.validation.exception.InvalidException

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class PascalValidator extends AbstractPascalValidator {
	
	
	@Check
	def checkBlockDeclaration(block block) {
		try {
			BlockValidator.validateBlock(block)				
			for (InvalidException exc : BlockValidator.getErrorList()) {
				error(exc.message, exc.component, null)
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
	}
	
	@Check
	def checkExpression(assignment_statement assignment_statement) {
		try {
			BooleanExpressionValidator.validateBooleanAttribution(assignment_statement)	
			ExpressionValidator.validateSimpleExpression(assignment_statement)
			ExpressionValidator.validateArithmeticExpression(assignment_statement)
			for (InvalidException exc : BlockValidator.getErrorList()) {
				error(exc.message, exc.component, null)
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
	}
	
	@Check
	def checkCase(case_statement case_statement) {
		try {
			CaseValidator.validateCase(case_statement)
			for (InvalidException exc : BlockValidator.getErrorList()) {
				error(exc.message, exc.component, null)
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
	}
	
	
	
	
}