package org.xtext.example.pascal.validation.obj;

import java.util.ArrayList;
import java.util.List;

public class Function {

	private String name;
	private static List<Variable> parameters;
	private List<String> typesList;
	private List<String> namesList;
	
	public Function(String name, List<Variable> parameters) {
		this.name = name;
		this.parameters = parameters;
		this.namesList = new ArrayList<>();
		this.typesList = new ArrayList<>();
		setParamsLists();
	}
	
	public void setParamsLists() {
		for (Variable var : parameters) {
			typesList.add(var.getType());
			namesList.add(var.getName());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<Variable> getParameters() {
		return parameters;
	}

	public void setParameters(List<Variable> parameters) {
		this.parameters = parameters;
	}

	public List<String> getTypesList() {
		return typesList;
	}

	public void setTypesList(List<String> typesList) {
		this.typesList = typesList;
	}

	public List<String> getNamesList() {
		return namesList;
	}

	public void setNamesList(List<String> namesList) {
		this.namesList = namesList;
	}
	
	public static String getTypeParameter(String variableName) {
		for (Variable var : getParameters()) {
			if (var.getName().equals(variableName)) {
				return var.getType();
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((typesList == null) ? 0 : typesList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Function other = (Function) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (typesList == null) {
			if (other.typesList != null)
				return false;
		} else if (!typesList.equals(other.typesList))
			return false;
		return true;
	}

	
	

}
