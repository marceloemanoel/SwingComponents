package br.com.smartcoders.components.swing.table.renderer;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 * Permite exibir em uma tabela uma célula com tamanho ajustavel exibindo o
 * conteúdo em várias linhas.
 * 
 * @author marceloemanoel
 * 
 */
@SuppressWarnings("serial")
public class MultiLineCellRenderer extends JTextArea implements
		TableCellRenderer {
	
	private static final String BLANK = "";
	private Map<Integer, Map<Integer, Integer>> cells;
	
	public MultiLineCellRenderer() {
		cells = new HashMap<Integer, Map<Integer,Integer>>();

		setLineWrap(true);
		setWrapStyleWord(false);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		
		setValue(value);
		
		return this;
	}

	private void setValue(Object value) {
		setText(value != null ? value.toString() : BLANK);
	}
}
