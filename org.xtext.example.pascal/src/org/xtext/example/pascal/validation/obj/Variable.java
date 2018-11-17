package org.xtext.example.pascal.validation.obj;

import com.google.common.base.Objects;

public class Variable {
	private String type;
	private String name;
	
	
	public Variable(String name){
		this.name = name;
	}
	public Variable(String name, String type){	
		this.name = name;
		this.type = type;
	}

	public String getType(){
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Variable)) {
			return false;
		}
		Variable other = (Variable) obj;
		return this.name.equals(other.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}
}
