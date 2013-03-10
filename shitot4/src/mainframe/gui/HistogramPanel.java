package mainframe.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import partiesList.model.IPartiesList;
import partiesList.model.IParty;


/**
 * Panel to show histogram of election results
 * @author Ziv Ronen
 *
 */
public class HistogramPanel extends JPanel {
	

static final long serialVersionUID = 1L;
private IPartiesList parties;
private static final int minHeight = 180;

  
  public HistogramPanel(){
	  super();
	  setLayout(new BorderLayout());
  }
  
  /** Set the count and display histogram
   * @param parties the list of the parties that we need to show the histogram for  
   */
  public void setParties(IPartiesList parties) {
	this.parties = parties;
    repaint();
  }
  
  /**
   * Get preferred width so that panel will grow with window and will not cap
   * @return the prefered width
   */
  private int getPreferredWidth(){
	  if(parties == null) return getWidth();
	    int maxStringLength = 0;
	    FontMetrics metrics = getFontMetrics(getFont());
	    
	    for (IParty party : parties) {
	      if (maxStringLength < metrics.stringWidth(party.getName()))
	    	  maxStringLength = metrics.stringWidth(party.getName());
	    }
	    return 2*parties.size()*maxStringLength;
  }

  /**
   * Paint the histogram 
   * @param g the panel's graphic object (we draw with it)
   */
  protected void paintComponent(Graphics g) {
    if (parties == null) return; // No display if count is null

    super.paintComponent(g);

    // Find the panel size and bar width and interval dynamically
    int width = getWidth();
    int height = getHeight();

    // Find the maximum count. The maximum count has the highest bar
    int maxCount = 0;
    int maxStringLength = 0;
    FontMetrics metrics = g.getFontMetrics();
    
    for (IParty party : parties) {
      if (maxCount < party.getVoteNumber())
        maxCount = party.getVoteNumber();
      if (maxStringLength < metrics.stringWidth(party.getName()))
    	  maxStringLength = metrics.stringWidth(party.getName());
    }
    
    int interval = Math.max(2*maxStringLength,(width - 40) / parties.size());
    int individualWidth = Math.max(maxStringLength,(int)(((width - 40) / parties.size()) * 0.60));
    
    // x is the start position for the first bar in the histogram
    int x = 30;

    // Draw a horizontal base line
    g.drawLine(10, height - 45, width - 10, height - 45);
   for (IParty party : parties) {
      // Find the bar height
      int barHeight =
        (int)(((double)party.getVoteNumber() / (double)maxCount) * (height - 85));

      // Display a bar (i.e. rectangle)
      g.setColor(Color.blue);
      g.fillRect(x, height - 45 - barHeight, individualWidth,
        barHeight);


      g.setColor(Color.black);
      // Display a letter under the base line
      g.drawString(party.getSymbol(), x, height - 30);
      g.drawString(party.getName(), x, height - 10);

      // Display count (turned into a string) over each bar
      g.drawString(""+party.getVoteNumber(), x, height - barHeight - 55 );

      // Move x for displaying the next character
      x += interval;
    }
  }
  

  @Override
  public Dimension getPreferredSize(){
	  return new Dimension(getPreferredWidth(), minHeight);
  }
}