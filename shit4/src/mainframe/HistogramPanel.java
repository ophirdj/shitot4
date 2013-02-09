package mainframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import partiesList.IPartiesList;
import partiesList.IParty;



public class HistogramPanel extends JPanel {
	

static final long serialVersionUID = 1L;
  private IPartiesList parties;


  /** Set the count and display histogram */
  public void showHistogram(IPartiesList parties) {
	this.parties = parties;
    repaint();
  }

  /** Paint the histogram */
  protected void paintComponent(Graphics g) {
    if (parties == null) return; // No display if count is null

    super.paintComponent(g);

    // Find the panel size and bar width and interval dynamically
    int width = getWidth();
    int height = getHeight();
    int interval = (width - 40) / parties.size();
    int individualWidth = (int)(((width - 40) / parties.size()) * 0.60);

    // Find the maximum count. The maximum count has the highest bar
    int maxCount = 0;
    for (IParty party : parties) {
      if (maxCount < party.getVoteNumber())
        maxCount = party.getVoteNumber();
    }

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

      // Display count (turned into a string) over each bar
      g.drawString(""+party.getVoteNumber(), x, height - barHeight - 55 );

      // Move x for displaying the next character
      x += interval;
    }
  }

  /** Override getPreferredSize */
  public Dimension getPreferredSize() {
    return new Dimension(500, 300);
  }
}