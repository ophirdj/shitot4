package mainframe.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import partiesList.model.IPartiesList;
import partiesList.model.IParty;

public class TablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private IPartiesList all_parties;
	private JTable table;

	public TablePanel() {
		super(new BorderLayout());
	}
	
	private void displayTable() {
		table = new ReadOnlyTable(all_parties.size(), 3);
		table.getColumnModel().getColumn(0).setHeaderValue("parties symbol");
		table.getColumnModel().getColumn(1).setHeaderValue("parties name");
		table.getColumnModel().getColumn(2).setHeaderValue("vote count");
		int i = 0;
		for (IParty party : all_parties) {
			table.setValueAt(party.getSymbol(), i, 0);
			table.setValueAt(party.getName(), i, 1);
			table.setValueAt(party.getVoteNumber(), i, 2);
			i++;
		}
		
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(table.getModel());
		rowSorter.setComparator(1, new AlphabeticComparator());
		rowSorter.setComparator(2, new NumericComparator());
		table.setRowSorter(rowSorter);
		
		
		this.removeAll();
		JScrollPane sp = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		this.add(sp);
	}
	
	
	private static class AlphabeticComparator implements Comparator<String> {

		@Override
		public int compare(String s1, String s2) {
			return s1.compareTo(s2);
		}
		
	}
	
	
	private static class NumericComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer i1, Integer i2) {
			return i1.compareTo(i2);
		}
		
	}
	
	

	public void showTable(IPartiesList parties) {
		all_parties = parties;
		displayTable();
	}

	/** Override getPreferredSize */
	public Dimension getPreferredSize() {
		return new Dimension(500, 300);
	}
	
	class ReadOnlyTable extends JTable{
		static final long serialVersionUID = 1L;
		
		public ReadOnlyTable(int rows, int columns) {
			super(rows,columns);
		}
		
		@Override
		public TableCellRenderer getCellRenderer(int row, int col){
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(SwingConstants.CENTER);
			return center;
		}
		
		@Override
		public boolean isCellEditable(int row, int col){
			return false;
		}
	}
}
