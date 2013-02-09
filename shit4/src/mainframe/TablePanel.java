package mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import partiesList.IPartiesList;
import partiesList.IParty;

public class TablePanel extends JPanel{
	static final long serialVersionUID = 1L;
	
	  public void showTable(IPartiesList parties) {
		  JTable table = new JTable(parties.size()+1,2);
		  table.setValueAt("party symbol", 0, 0);
		  table.setValueAt("counts", 0, 1);
		  int i=1;
		  for(IParty party : parties){
		    table.setValueAt(party.getSymbol(), i, 0);
		    table.setValueAt(party.getVoteNumber(), i, 1);
		    i++;
		  }
		  this.removeAll();
		  this.add(table,BorderLayout.CENTER);
	  }


	  /** Override getPreferredSize */
	  public Dimension getPreferredSize() {
	    return new Dimension(500, 300);
	  }
}
