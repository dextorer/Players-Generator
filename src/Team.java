/**
 * Created with IntelliJ IDEA.
 * User: alessandro
 * Date: 9/28/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import net.miginfocom.swing.MigLayout;

public class Team implements ListSelectionListener, ActionListener {
    private String team;
    private String team_data;
    boolean done = false;

    private Player[] players = new Player[18];							// player's array

    MigLayout vertical = new MigLayout("wrap, flowy",
                                       "[center]",                      // column constraints : 1 column
                                       "[][]20[][]10[]");               // row constraints    : 4 rows, middle rows with a gap of 20
    MigLayout grid = new MigLayout("wrap, fillx",
            "[center][center]20[center][center]",
            "[center]20[center]20[center]20[center]");

    // panel of the player's list and of the formation scheme combo box
    private JPanel team_panel = new JPanel(vertical);

    // available formation scheme
    JComboBox formations = new JComboBox(new String[]{ "3-5-2", "4-4-2", "5-3-2"});
    private boolean formation_changed = false;
    private JList list = new JList(new String[]{ "Goal Keeper", "Back 1", "Back 2", "Back 3", "Midfielder 1", "Midfielder 2",
                                                 "Midfielder 3", "Midfielder 4", "Midfielder 5", "Forward 1", "Forward 2",
                                                 "Backup 1", "Backup 2", "Backup 3", "Backup 4", "Backup 5", "Backup 6",
                                                 "Backup 7" });
    // panel of the scroll list
    private JScrollPane players_list_panel = new JScrollPane(list);

    // panel containing the player's statistics
    private JPanel players_stats_panel = new JPanel(grid);
    private JLabel keeper_label;                                        // goal keeper label
    private JTextField enter_number;									// player's number text field
    private JSpinner attack_stat;										// spinner 1 (attack)
    private JSpinner defense_stat;										// spinner 2 (defense)
    private JSpinner power_stat;										// spinner 3 (power)
    private JSpinner precision_stat;									// spinner 4 (precision)
    private JSpinner speed_stat;										// spinner 5 (speed)
    private JSpinner tackle_stat;										// spinner 6 (tackle)
    private JSpinner goal_keeping_stat;									// spinner 7 (goal_keeping)
    private JButton save_button = new JButton("Save");					// saves selected player statistics
    private JButton random_stats_button = new JButton("Random");		// generates random statistics for the selected player
    private JButton random_all_button = new JButton("Random All");		// generates random statistics for all the players
    private JButton start_button = new JButton("Start");				// generates the player's statistics file

    private JSplitPane split_panel;
    private static final int split_panel_width = 550;
    private static final int split_panel_height = 410;

    public Team(String team) {
        this.team = team;

        // creo array giocatori
        for(int i = 1; i <= players.length; i++) {
            players[i-1] = new Player();
            players[i-1].setTeam(team);
            players[i-1].setNumber(i);
        }

       /****************************************
        *			SCELTA FORMAZIONE			*											 												    *
        ****************************************/
        formations.setSelectedIndex(0);
        formations.addActionListener(this);
        formations.setActionCommand("change formation");

       /****************************************
        *			LISTA GIOCATORI				*											 												    *
        ****************************************/
        // Crea la lista dei giocatori e la mette in uno scroll pane
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        /****************************************
         *		PANNELLO MODIFICA STATISTICHE	*											 												    *
         ****************************************/
        // editor per modificare il nome
        enter_number = new JTextField(Integer.toString(players[list.getSelectedIndex()].getNumber()));
        keeper_label = new JLabel("Goal Keeping:");

        // Spinners
        SpinnerNumberModel spinner_model1 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model2 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model3 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model4 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model5 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model6 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model7 = new SpinnerNumberModel(0, 0, 100, 1);

        attack_stat = new JSpinner(spinner_model1);
        defense_stat = new JSpinner(spinner_model2);
        power_stat = new JSpinner(spinner_model3);
        precision_stat = new JSpinner(spinner_model4);
        speed_stat = new JSpinner(spinner_model5);
        tackle_stat = new JSpinner(spinner_model6);
        goal_keeping_stat = new JSpinner(spinner_model7);

         /************************************************************
         *		ASSEMBLA IL PANNELLO DELLA FINESTRA PRINCIPALE		*											 												    *
         ************************************************************/
        team_panel.add(new JLabel("Formation Scheme:"));
        team_panel.add(formations);
        team_panel.add(new JLabel("Players:"));
        team_panel.add(players_list_panel, "wmin 140, hmin 315");

        players_stats_panel.add(new JLabel("Number:"), "span 2, right");
        players_stats_panel.add(enter_number, "wrap, span 2, left, wmin 25");
        players_stats_panel.add(keeper_label);
        players_stats_panel.add(goal_keeping_stat, "wrap");
        players_stats_panel.add(new JLabel("Attack:"));
        players_stats_panel.add(attack_stat);
        players_stats_panel.add(new JLabel("Defense:"));
        players_stats_panel.add(defense_stat);
        players_stats_panel.add(new JLabel("Power:"));
        players_stats_panel.add(power_stat);
        players_stats_panel.add(new JLabel("Precision:"));
        players_stats_panel.add(precision_stat);
        players_stats_panel.add(new JLabel("Speed:"));
        players_stats_panel.add(speed_stat);
        players_stats_panel.add(new JLabel("Tackle:"));
        players_stats_panel.add(tackle_stat);
        players_stats_panel.add(random_stats_button, "span 2, center");
        players_stats_panel.add(save_button, "span 2, wrap 75, center");
        players_stats_panel.add(random_all_button, "span 4");

        // Create a split pane with the two scroll panes in it.
        split_panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, team_panel, players_stats_panel);
        split_panel.setDividerLocation(150);

        //Provide a preferred size for the split pane.
        split_panel.setPreferredSize(new Dimension(split_panel_width, split_panel_height));

        // "Random" button action listener
        random_stats_button.addActionListener(this);
        random_stats_button.setActionCommand("random");

        // "Save" button action listener
        save_button.addActionListener(this);
        save_button.setActionCommand("save player");

        // "Random All" button action listener
        random_all_button.addActionListener(this);
        random_all_button.setActionCommand("random all");

        // "Start" button action listener
        start_button.addActionListener(this);
        start_button.setActionCommand("start");
    }

    public void valueChanged(ListSelectionEvent e) {
        // if the formation scheme has been changed through the combo box
        if (formation_changed) {
            enter_number.setText(Integer.toString(players[0].getNumber()));
            attack_stat.setValue(players[0].getAttack());
            defense_stat.setValue(players[0].getDefense());
            power_stat.setValue(players[0].getPower());
            precision_stat.setValue(players[0].getPrecision());
            speed_stat.setValue(players[0].getSpeed());
            tackle_stat.setValue(players[0].getTackle());
            goal_keeping_stat.setValue(players[0].getGoal_keeping());
            formation_changed = false;
        }
        else {
            JList l = (JList)e.getSource();
            // handle goal keeper special case
            if (l.getSelectedIndex() == 0) {
                // add goal keeping stat only if the goal keeper is selected
              /*  players_stats_panel.add(keeper_label, 2);
                players_stats_panel.add(goal_keeping_stat, "wrap", 3);
                goal_keeping_stat.setValue(players[0].getGoal_keeping());
                players_stats_panel.revalidate();
                players_stats_panel.repaint(); */
                keeper_label.setEnabled(true);
                goal_keeping_stat.setEnabled(true);
                goal_keeping_stat.setValue(players[0].getGoal_keeping());
            }
            else {
             /*   players_stats_panel.remove(keeper_label);
                players_stats_panel.remove(goal_keeping_stat);
                players_stats_panel.revalidate();
                players_stats_panel.repaint();*/
                keeper_label.setEnabled(false);
                goal_keeping_stat.setValue(0);
                goal_keeping_stat.setEnabled(false);
            }
            // update spinners values
            enter_number.setText(Integer.toString(players[l.getSelectedIndex()].getNumber()));
            attack_stat.setValue(players[l.getSelectedIndex()].getAttack());
            defense_stat.setValue(players[l.getSelectedIndex()].getDefense());
            power_stat.setValue(players[l.getSelectedIndex()].getPower());
            precision_stat.setValue(players[l.getSelectedIndex()].getPrecision());
            speed_stat.setValue(players[l.getSelectedIndex()].getSpeed());
            tackle_stat.setValue(players[l.getSelectedIndex()].getTackle());
        }
    }

    public void actionPerformed(ActionEvent e) {
        // fired whenever the formation scheme is changed in the combo box
        if(e.getActionCommand().equals("change formation")){
            formation_changed = true;
            JComboBox cb = (JComboBox)e.getSource();
            String scheme = (String)cb.getSelectedItem();
            updatePlayers(scheme);
        }
        // fired whenever the button "Random" is clicked
        else if(e.getActionCommand().equals("random")){
            // Generates random statistics for the selected player
            Random rand = new Random();
            attack_stat.setValue(rand.nextInt(100));
            defense_stat.setValue(rand.nextInt(100));
            power_stat.setValue(rand.nextInt(100));
            precision_stat.setValue(rand.nextInt(100));
            speed_stat.setValue(rand.nextInt(100));
            tackle_stat.setValue(rand.nextInt(100));
            if (list.getSelectedIndex() == 0)
                goal_keeping_stat.setValue(rand.nextInt(100));
        }
        // fired whenever the button "Save" is clicked
        else if(e.getActionCommand().equals("save player")) {
            // Stores the statistics values of the selected player
    		players[list.getSelectedIndex()].setNumber(Integer.parseInt(enter_number.getText()));
    		players[list.getSelectedIndex()].setAttack((Integer) attack_stat.getValue());
    		players[list.getSelectedIndex()].setDefense((Integer) defense_stat.getValue());
    		players[list.getSelectedIndex()].setPower((Integer) power_stat.getValue());
    		players[list.getSelectedIndex()].setPrecision((Integer) precision_stat.getValue());
    		players[list.getSelectedIndex()].setSpeed((Integer) speed_stat.getValue());
    		players[list.getSelectedIndex()].setTackle((Integer) tackle_stat.getValue());

            // goal keeper special case
            if (list.getSelectedIndex() == 0)
    		    players[0].setGoal_keeping((Integer) goal_keeping_stat.getValue());
            else
                players[list.getSelectedIndex()].setGoal_keeping(0);

            // info box
            JOptionPane.showMessageDialog(null, "Player Statistics Saved!", "Info Message", JOptionPane.INFORMATION_MESSAGE);
    	}
        // fired whenever the button "All Random" is clicked
        else if (e.getActionCommand().equals("random all")) {
            Random rand = new Random();
            for(int i = 0; i < players.length; i++) {
                // update GUI for the current selected player
                if(i == list.getSelectedIndex()) {
                    int rand_int = rand.nextInt(100);
                    attack_stat.setValue(rand_int);
                    players[i].setAttack(rand_int);

                    rand_int = rand.nextInt(100);
                    defense_stat.setValue(rand_int);
                    players[i].setDefense(rand_int);

                    rand_int = rand.nextInt(100);
                    power_stat.setValue(rand_int);
                    players[i].setPower(rand_int);

                    rand_int = rand.nextInt(100);
                    precision_stat.setValue(rand_int);
                    players[i].setPrecision(rand_int);

                    rand_int = rand.nextInt(100);
                    speed_stat.setValue(rand_int);
                    players[i].setSpeed(rand_int);

                    rand_int = rand.nextInt(100);
                    tackle_stat.setValue(rand_int);
                    players[i].setTackle(rand_int);
                    // handle goal keeper special case
                    if (list.getSelectedIndex() == 0) {
                        rand_int = rand.nextInt(100);
                        goal_keeping_stat.setValue(rand_int);
                        players[i].setGoal_keeping(rand_int);
                    }
                }
                else {	// altrimenti aggiorniamo solo i valori del player
                    players[i].setAttack(rand.nextInt(100));
                    players[i].setDefense(rand.nextInt(100));
                    players[i].setPower(rand.nextInt(100));
                    players[i].setPrecision(rand.nextInt(100));
                    players[i].setSpeed(rand.nextInt(100));
                    players[i].setTackle(rand.nextInt(100));
                    // handle goal keeper special case
                    if (i == 0)
                        players[i].setGoal_keeping(rand.nextInt(100));
                }
            }
        }
        // fired whenever the button "Start" is clicked
        else if (e.getActionCommand().equals("start")) {
            // conversione player --> json
            Gson gson = new Gson();

            // concatena le statistiche in un'unica stringa json
            for (int i = 0; i < players.length; i++) {
                team_data = team_data + gson.toJson(players[i]) + '\n';
            }
            done = true;
        }
    }

    private void updatePlayers(String scheme) {
        if (scheme.equals("3-5-2"))
            list.setListData(new String[]{ "Goal Keeper", "Back 1", "Back 2", "Back 3", "Midfielder 1", "Midfielder 2",
                                           "Midfielder 3", "Midfielder 4", "Midfielder 5", "Forward 1", "Forward 2",
                                           "Backup 1", "Backup 2", "Backup 3", "Backup 4", "Backup 5", "Backup 6",
                                           "Backup 7" });
        if (scheme.equals("4-4-2"))
            list.setListData(new String[]{ "Goal Keeper", "Back 1", "Back 2", "Back 3", "Back 4", "Midfielder 1",
                                           "Midfielder 2", "Midfielder 3", "Midfielder 4", "Forward 1", "Forward 2",
                                           "Backup 1", "Backup 2", "Backup 3", "Backup 4", "Backup 5", "Backup 6",
                                           "Backup 7" });

        if (scheme.equals("5-3-2"))
            list.setListData(new String[]{ "Goal Keeper", "Back 1", "Back 2", "Back 3", "Back 4", "Back 5",
                                           "Midfielder 1", "Midfielder 2", "Midfielder 3", "Forward 1", "Forward 2",
                                           "Backup 1", "Backup 2", "Backup 3", "Backup 4", "Backup 5", "Backup 6",
                                           "Backup 7" });

        list.setSelectedIndex(0);
    }

    public void setTeamData(String team_data) {
        this.team_data = team_data;
    }

    // ritorna il pannello padre
    public JSplitPane getSplitPane() {
        return split_panel;
    }

    public JButton getStartButton() {
        return start_button;
    }

    public boolean getDone() {
        return done;
    }

    public String getTeam() {
        return team;
    }

    public String getTeamData() {
        return team_data;
    }

}
