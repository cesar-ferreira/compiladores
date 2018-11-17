package org.xtext.example.pascal.validation;

import org.eclipse.emf.common.util.EList;
import org.xtext.example.pascal.pascal.case_limb;
import org.xtext.example.pascal.pascal.case_statement;
import org.xtext.example.pascal.pascal.constant;
import org.xtext.example.pascal.pascal.factor;
import org.xtext.example.pascal.pascal.simple_expression;
import org.xtext.example.pascal.pascal.term;
import org.xtext.example.pascal.validation.exception.InvalidException;
import org.xtext.example.pascal.validation.exception.Message;

public class CaseValidator {

	public static void validateCase(case_statement case_statement) {

		BooleanExpressionValidator.validateBooleanExpression(case_statement.getExpression());
		
		for (simple_expression simple_expression : case_statement.getExpression().getSimple_expression()) {

			String caseType = null;
			
			if (case_statement.getExpression().getRelational_operator() != null) {
				caseType = "boolean";
				validateTypeOptions(caseType, case_statement.getCase_limb(), null);
			} else {
				for (term term : simple_expression.getTerm()) {
					for (factor factor : term.getFactor()) {
						if (factor.getVariable() != null && factor.getVariable().getEntire_variable() == null) {
							BlockValidator.addError(new InvalidException(Message.CASE_INVALID_EXPRESSION, factor));
						} else if (factor.getVariable() != null) {
							String variableName = factor.getVariable().getEntire_variable().getIdentifier().getIdentifier();
							caseType = BlockValidator.getTypeVariable(variableName);
							
							validateTypeOptions(caseType, case_statement.getCase_limb(), factor);
						}
					}
				}
			}
			
			
			

		}

	}
	
	public static void validateTypeOptions(String typeCase, EList<case_limb> case_limb_list, factor factor) {
		if (typeCase == null && factor != null) {
			BlockValidator.addError(new InvalidException(Message.UNDECLARED_VARIABLE, factor));

		} else if (factor != null && !typeCase.equals("char") && !typeCase.equals("integer")
				&& !typeCase.equals("boolean")) {
			BlockValidator.addError(new InvalidException(Message.CASE_INVALID_EXPRESSION, factor));

		} else if (factor != null)  {

			for (case_limb case_limb : case_limb_list) {
				for (constant constant : case_limb.getCase_label_list().getConstant()) {
					if (typeCase.equals("integer") && (constant.getNumber() == null
							|| constant.getNumber().getInteger_number() == null)) {
						BlockValidator
								.addError(new InvalidException(Message.CASE_INVALID_OPTION, constant));
					}

					if (typeCase.equals("char")
							&& (constant.getStrings() == null || constant.getStrings().length() > 3)) {
						BlockValidator
								.addError(new InvalidException(Message.CASE_INVALID_OPTION, constant));
					}

					if (typeCase.equals("boolean") && constant.getBoolean() == null) {
						BlockValidator
								.addError(new InvalidException(Message.CASE_INVALID_OPTION, constant));
					}
				}
			}
		}
	}

}
