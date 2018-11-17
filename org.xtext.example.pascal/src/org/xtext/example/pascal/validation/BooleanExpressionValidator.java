package org.xtext.example.pascal.validation;

import java.util.List;

import org.xtext.example.pascal.pascal.addition_operator;
import org.xtext.example.pascal.pascal.assignment_statement;
import org.xtext.example.pascal.pascal.expression;
import org.xtext.example.pascal.pascal.factor;
import org.xtext.example.pascal.pascal.simple_expression;
import org.xtext.example.pascal.pascal.term;
import org.xtext.example.pascal.validation.exception.InvalidException;
import org.xtext.example.pascal.validation.exception.Message;

public class BooleanExpressionValidator {

	public static void validateBooleanAttribution(assignment_statement assignment_statement) {
		String relational_operator = assignment_statement.getExpression().getRelational_operator();
		if (relational_operator != null) {

			if (assignment_statement.getVariable() != null) {
				String variableName = assignment_statement.getVariable().getEntire_variable().getIdentifier()
						.getIdentifier();
				String type = BlockValidator.getTypeVariable(variableName);
				if (type == null || !type.equals("boolean")) {
					BlockValidator.addError(
							new InvalidException(Message.INVALID_ATTRIBUTION, assignment_statement.getExpression()));
				}
			}

		}
		validateBooleanExpression(assignment_statement.getExpression());
		validateAndOrExpression(assignment_statement);
	}

	public static void validateBooleanExpression(expression expression) {
		List<String> listTypesExpression = ExpressionValidator.getTypesExpression(expression);

		String relational_operator = expression.getRelational_operator();

		if (relational_operator != null) {
			if (listTypesExpression.size() != 2) {
				BlockValidator.addError(new InvalidException(Message.BOOLEAN_OP_REL_INVALID, expression));
			}
		}
		
		if (relational_operator != null && !relational_operator.equals("=") && !relational_operator.equals("<>")) {
			if (listTypesExpression.contains("string") || listTypesExpression.contains("char")
					|| listTypesExpression.contains("boolean")) {
				BlockValidator.addError(new InvalidException(Message.BOOLEAN_INVALID_TYPE, expression));
			}
		}
	}

	public static void validateAndOrExpression(assignment_statement assignment_statement) {

		String declaratedVariableName = assignment_statement.getVariable().getEntire_variable().getIdentifier()
				.getIdentifier();
		String declaratedVariableType = BlockValidator.getTypeVariable(declaratedVariableName);
		for (simple_expression simple_expression : assignment_statement.getExpression().getSimple_expression()) {
			if (simple_expression.getAddition_operator() != null) {
				for (addition_operator addition_operator : simple_expression.getAddition_operator()) {
					if (addition_operator != null && addition_operator.equals("or")) {
						if (!declaratedVariableType.equals("boolean")) {
							BlockValidator.addError(
									new InvalidException(Message.BOOLEAN_INVALID_ATTRIBUTION, assignment_statement));
						}

						List<String> listTypes = ExpressionValidator.getTypesExpression(assignment_statement.getExpression());
						if (listContainsAnyTypeBesidesBool(listTypes)) {
							BlockValidator.addError(new InvalidException(Message.BOOLEAN_INVALID_OPERATORS,
									assignment_statement));
						}
						
					}
				}
			}

			for (term term : simple_expression.getTerm()) {
				if (term.getMultiplication_operator() != null) {
					for (String multiplication_operator : term.getMultiplication_operator()) {
						if (multiplication_operator != null && multiplication_operator.contains("and")) {
							if (!declaratedVariableType.equals("boolean")) {
								BlockValidator.addError(new InvalidException(Message.BOOLEAN_INVALID_ATTRIBUTION,
										assignment_statement));
							}
							List<String> listTypes = ExpressionValidator.getTypesExpression(assignment_statement.getExpression());
							if (listContainsAnyTypeBesidesBool(listTypes)) {
								BlockValidator.addError(new InvalidException(Message.BOOLEAN_INVALID_OPERATORS,
										assignment_statement));
							}
						}
					}
				}
			}

		}

	}

	private static boolean listContainsAnyTypeBesidesBool(List<String> listTypes) {
		for (String type : listTypes) {
			if (!type.equals("boolean")) {
				return true;
			}
		}
		return false;
	}

}
