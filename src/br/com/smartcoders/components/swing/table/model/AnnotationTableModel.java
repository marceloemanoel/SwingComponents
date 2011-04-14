package br.com.smartcoders.components.swing.table.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import br.com.smartcoders.components.swing.table.model.mapping.ColumnMapping;
import br.com.smartcoders.components.swing.table.model.mapping.annotations.Column;
import br.com.smartcoders.components.swing.table.model.mapping.exception.InvalidMethodMapperDefinition;

@SuppressWarnings("serial")
public class AnnotationTableModel<Row> extends AbstractTableModel {

	private static final String BLANK = "";
	
	private List<Row> rows;
	private Map<Integer, ColumnMapping> mappings;

	public AnnotationTableModel() {
		rows = new ArrayList<Row>();
		mappings = new HashMap<Integer, ColumnMapping>();
		collectMappings();
	}

	private void collectMappings() {
		Method[] methods = getClass().getDeclaredMethods();
		for (Method method : methods) {
			method.setAccessible(true);
			if (method.isAnnotationPresent(Column.class)) {
				Column column = method.getAnnotation(Column.class);
				ColumnMapping mapping = new ColumnMapping(column, method);
				mappings.put(column.index(), mapping);
			}
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		ColumnMapping mapping = getColumnMapping(columnIndex);
		Method method = mapping.getMethod();
		return method.getReturnType();
	}

	private ColumnMapping getColumnMapping(int columnIndex) {
		return mappings.get(columnIndex);
	}

	@Override
	public String getColumnName(int columnIndex) {
		ColumnMapping mapping = getColumnMapping(columnIndex);
		Column column = mapping.getColumn();
		return column.name();
	}

	public void add(Row line) {
		rows.add(line);
		fireTableDataChanged();
	}

	public void addAll(List<Row> lines) {
		rows.addAll(lines);
		fireTableDataChanged();
	}

	public void remove(Row line) {
		rows.remove(line);
		fireTableDataChanged();
	}

	public Row remove(int lineIndex) {
		ensureValidRow(lineIndex);
		Row line = rows.remove(lineIndex);
		fireTableDataChanged();
		return line;
	}

	public void removeAll(List<Row> lines) {
		rows.removeAll(lines);
		fireTableDataChanged();
	}

	public Row get(int index) {
		ensureValidRow(index);
		return rows.get(index);
	}

	private void ensureValidRow(int index) {
		if(!(index >= 0 && index < getRowCount())){
			throw new IndexOutOfBoundsException("The index, " + index + " ,should be 0 <= index < " + getRowCount());
		}
	}
	

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return getColumnMapping(columnIndex).getColumn().editable();
	}
	
	@Override
	public int getColumnCount() {
		return mappings.size();
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Object getValueAt(int row, int column) {

		Row line = rows.get(row);
		ColumnMapping mapping = getColumnMapping(column);
		Method method = mapping.getMethod();
		method.setAccessible(true);
		Object value = BLANK;
		try {
			value = method.invoke(this, line);
		} catch (IllegalArgumentException e) {
			throw new InvalidMethodMapperDefinition();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
		return (value != null ? value : BLANK);
	}

}