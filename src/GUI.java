
/**
 * Created with IntelliJ IDEA.
 * User: alessandro
 * Date: 10/1/13
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GUI {
    private static final int frame_width = 570;
    private static final int frame_height = 480;

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window for team 1.
        JFrame t1_frame = new JFrame("Team 1");
        t1_frame.setLayout(new FlowLayout());

        // aggiunge elementi al pannello
        Team t1 = new Team("Team_One");
        t1_frame.getContentPane().add(t1.getSplitPane());
        t1_frame.getContentPane().add(t1.getStartButton());

        //Display the window.
        t1_frame.pack();
        t1_frame.setVisible(true);
        t1_frame.setSize(frame_width, frame_height);
        t1_frame.setResizable(false);
        t1_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Puts the window in the center of the screen
        Dimension dim1 = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = 25;
        int y1 = (dim1.height-frame_height)/2;
        t1_frame.setLocation(x1,y1);

        //Create and set up the window for team 2.
        JFrame t2_frame = new JFrame("Team 2");
        t2_frame.setLayout(new FlowLayout());

        // aggiunge elementi al pannello
        Team t2 = new Team("Team_Two");
        t2_frame.getContentPane().add(t2.getSplitPane());
        t2_frame.getContentPane().add(t2.getStartButton());

        //Display the window.
        t2_frame.pack();
        t2_frame.setVisible(true);
        t2_frame.setSize(frame_width, frame_height);
        t2_frame.setResizable(false);
        t2_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Puts the window in the center of the screen
        Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
        int x2 = dim2.width-frame_width-25;
        int y2 = (dim2.height-frame_height)/2;
        t2_frame.setLocation(x2,y2);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
