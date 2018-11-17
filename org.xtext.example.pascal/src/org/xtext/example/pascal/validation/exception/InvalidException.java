package org.xtext.example.pascal.validation.exception;

import java.io.Serializable;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("serial")
public class InvalidException extends RuntimeException implements Serializable {
	
	private EObject component;

	public InvalidException(Message message, EObject component) {
		super(message.getContent());
		this.component = component;
	}
	
	public EObject getComponent() {
		return component;
	}

	public static void error(Message message, EObject component) {
		throw new InvalidException(message, component);
	}

	public static void invalid(EObject e) {
		error(Message.UNEXPECTED_ERROR, e);
	}
}
