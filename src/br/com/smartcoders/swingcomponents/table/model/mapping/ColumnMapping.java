package br.com.smartcoders.swingcomponents.table.model.mapping;

import java.lang.reflect.Method;

import br.com.smartcoders.swingcomponents.table.model.mapping.annotations.Column;

public class ColumnMapping {

	private Column column;
	private Method method;
	
	public ColumnMapping(Column column, Method method){
		this.column = column;
		this.method = method;
	}
	
	public Column getColumn() {
		return column;
	}
	
	public Method getMethod() {
		return method;
	}
}
