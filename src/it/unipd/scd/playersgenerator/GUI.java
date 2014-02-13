package it.unipd.scd.playersgenerator;

/**
 * Created with IntelliJ IDEA.
 * User: alessandro
 * Date: 10/1/13
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private static final int frame_width = 570;
    private static final int frame_height = 480;
    private static final JFrame t1_frame = new JFrame("Team 1");
    private static final JFrame t2_frame = new JFrame("Team 2");
    private static final Team t1 = new Team("Team_One");
    private static final Team t2 = new Team("Team_Two");
    private static Thread check_t1, check_t2;
    private static boolean one_team_left = false;
    private static JsonObject data;
    private static String team1_data = "";
    private static String team2_data = "";

    private static ConfigurationCallback CALLBACK;

    private static Thread makeThread(final Team t, final JFrame f) {
        Runnable runloop = new Runnable() {
            @Override
            public void run() {
                try {
                    while (!t.getDone())
                        Thread.sleep(500);
                }catch(InterruptedException e) {return;}

                synchronized (this) {
                    if (!one_team_left) {
                        one_team_left = true;
                        data.add("one", t.getTeamData());
                        String message = "Setup ";
                        if (t.getTeam().equals("Team_One"))
                            message = message + "Team 2 ";
                        else
                            message = message + "Team 1 ";

                        message = message + "to start the game";
                        JOptionPane.showMessageDialog(null, message, "Info Message", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        data.add("two", t.getTeamData());
                        if (CALLBACK != null) {
                            CALLBACK.onConfigurationConfirmed(data.toString());
                        }
                    }
                }
                f.dispose();
            }
        };
        return new Thread(runloop);
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window for team 1.
        t1_frame.setLayout(new FlowLayout());

        // aggiunge elementi al pannello
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

        t1.setTeamData(null);

        //Create and set up the window for team 2.
        t2_frame.setLayout(new FlowLayout());

        // aggiunge elementi al pannello
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

        t2.setTeamData(null);
    }

    public static void showGUI(ConfigurationCallback callback) {

        GUI.CALLBACK = callback;

        data = new JsonObject();

        createAndShowGUI();

        check_t1 = makeThread(t1,t1_frame);
        check_t2 = makeThread(t2,t2_frame);

        check_t1.start();
        check_t2.start();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        showGUI(null);
    }
}
