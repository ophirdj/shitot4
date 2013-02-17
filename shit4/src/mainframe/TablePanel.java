package mainframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import GUI.Global_Window;
import partiesList.IPartiesList;
import partiesList.IParty;

public class TablePanel extends JPanel {
	static final long serialVersionUID = 1L;
	IPartiesList all_parties;
	final int PARTIES_IN_WINDOW = 10;
	int current_place;
	JTable table;

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
		this.removeAll();
		JScrollPane sp = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		this.add(sp);
		Global_Window.main_window.show_if_current(this, this);
	}

	public void showTable(IPartiesList parties) {
		current_place = 0;
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
