/**
 * Created with IntelliJ IDEA.
 * User: alessandro
 * Date: 9/28/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Team implements ListSelectionListener, ActionListener {
    private Player[] players = new Player[18];							// vettore di giocatori

    MigLayout vertical = new MigLayout("wrap, flowy",
                                       "[center]",                      // column constraints : 1 column
                                       "[][]20[][]");                   // row constraints    : 4 rows, middle rows with a gap of 20
    // pannello padre della finestra
    private JPanel team_panel = new JPanel(vertical);

    // formazioni disponibili e mene a tendina per sceglierle
    JComboBox formations = new JComboBox(new String[]{ "3-5-2", "4-4-2", "5-3-2"});

    boolean formation_changed = false;

    private JList list = new JList(new String[]{ "Goal Keeper", "Back 1", "Back 2", "Back 3", "Midfielder 1", "Midfielder 2",
                                                 "Midfielder 3", "Midfielder 4", "Midfielder 5", "Forward 1", "Forward 2",
                                                 "Backup 1", "Backup 2", "Backup 3", "Backup 4", "Backup 5", "Backup 6",
                                                 "Backup 7" });

    // pannello contenente la lista dei giocatori sulla sinistra
    private JScrollPane players_list_panel = new JScrollPane(list);

    MigLayout grid = new MigLayout("wrap, fillx",
                                    "[][]20[][]",
                                    "[]20[]20[]20[]");
    // pannello contenente le statistiche dei giocatori
    private JPanel players_stats_panel = new JPanel(grid);

    private JSplitPane split_panel;     								// pannello "padre"

    private JLabel keeper_label;
    private JTextField enter_number;									// campo in cui inserire il numero
    private JSpinner stat_field1;										// spinner parametro 1 (attack)
    private JSpinner stat_field2;										// spinner parametro 2 (defense)
    private JSpinner stat_field3;										// spinner parametro 3 (power)
    private JSpinner stat_field4;										// spinner parametro 4 (precision)
    private JSpinner stat_field5;										// spinner parametro 5 (speed)
    private JSpinner stat_field6;										// spinner parametro 6 (tackle)
    private JSpinner stat_field7;										// spinner parametro 7 (goal_keeping)
    private JButton save_all_button = new JButton("Print");				// bottone per generare il file di statistiche
    private JButton random_all_stats = new JButton("All Random");		// bottone per generare statistiche random per tutti i giocatori
    private JButton save_button = new JButton("Save");					// bottone per salvare le statistiche del giocatore corrente
    private JButton random_stats = new JButton("Random");				// bottone per generare statistiche random per il giocatore corrente

    public Team() {

        // creo array giocatori
        for(int i = 1; i <= players.length; i++) {
            players[i-1] = new Player();
            players[i-1].setNumber(i);
        }

       /****************************************
        *			SCELTA FORMAZIONE			*											 												    *
        ****************************************/
        JLabel formation_label = new JLabel("Formazione:");
        formations.setSelectedIndex(0);
        formations.addActionListener(this);
        formations.setActionCommand("change_formation");

       /****************************************
        *			LISTA GIOCATORI				*											 												    *
        ****************************************/
        JLabel players_label = new JLabel("Giocatori:");
        // Crea la lista dei giocatori e la mette in uno scroll pane
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);

        /****************************************
         *		PANNELLO MODIFICA STATISTICHE	*											 												    *
         ****************************************/
        // label del nome
        JLabel number = new JLabel("Number:");
        number.setHorizontalAlignment(JLabel.CENTER);

        // editor per modificare il nome
        enter_number = new JTextField(Integer.toString(players[list.getSelectedIndex()].getNumber()));

        // Spinners
        SpinnerNumberModel spinner_model1 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model2 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model3 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model4 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model5 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model6 = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinner_model7 = new SpinnerNumberModel(0, 0, 100, 1);

        // Parametro 1
        JLabel stat1 = new JLabel("Attack:");
        stat1.setHorizontalAlignment(JLabel.CENTER);
        stat_field1 = new JSpinner(spinner_model1);

        // Parametro 2
        JLabel stat2 = new JLabel("Defense:");
        stat2.setHorizontalAlignment(JLabel.CENTER);
        stat_field2 = new JSpinner(spinner_model2);

        // Parametro 3
        JLabel stat3 = new JLabel("Power:");
        stat3.setHorizontalAlignment(JLabel.CENTER);
        stat_field3 = new JSpinner(spinner_model3);

        // Parametro 4
        JLabel stat4 = new JLabel("Precision:");
        stat4.setHorizontalAlignment(JLabel.CENTER);
        stat_field4 = new JSpinner(spinner_model4);

        // Parametro 5
        JLabel stat5 = new JLabel("Speed:");
        stat5.setHorizontalAlignment(JLabel.CENTER);
        stat_field5 = new JSpinner(spinner_model5);

        // Parametro 6
        JLabel stat6 = new JLabel("Tackle:");
        stat1.setHorizontalAlignment(JLabel.CENTER);
        stat_field6 = new JSpinner(spinner_model6);

        // Parametro 7
        keeper_label = new JLabel("Goal Keeping:");
        stat5.setHorizontalAlignment(JLabel.CENTER);
        stat_field7 = new JSpinner(spinner_model7);

         /************************************************************
         *		ASSEMBLA IL PANNELLO DELLA FINESTRA PRINCIPALE		*											 												    *
         ************************************************************/
        team_panel.add(formation_label);
        team_panel.add(formations);
        team_panel.add(players_label);
        team_panel.add(players_list_panel, "wmin 150, hmin 325");

        players_stats_panel.add(number, "span 2, right");
        players_stats_panel.add(enter_number, "wrap, span 2, left, wmin 25");
        players_stats_panel.add(keeper_label);
        players_stats_panel.add(stat_field7, "wrap");
        players_stats_panel.add(stat1);
        players_stats_panel.add(stat_field1);
        players_stats_panel.add(stat2);
        players_stats_panel.add(stat_field2);
        players_stats_panel.add(stat3);
        players_stats_panel.add(stat_field3);
        players_stats_panel.add(stat4);
        players_stats_panel.add(stat_field4);
        players_stats_panel.add(stat5);
        players_stats_panel.add(stat_field5);
        players_stats_panel.add(stat6);
        players_stats_panel.add(stat_field6);
        players_stats_panel.add(random_stats, "span 2, center");
        players_stats_panel.add(save_button, "span 2, center");


        // Create a split pane with the two scroll panes in it.
        split_panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, team_panel, players_stats_panel);
        split_panel.setDividerLocation(150);

        //Provide a preferred size for the split pane.
        split_panel.setPreferredSize(new Dimension(550, 400));

        // listener bottone random giocatore corrente
        random_stats.addActionListener(this);
        random_stats.setActionCommand("random");

        // listener bottone salva giocatore corrente
        save_button.addActionListener(this);
        save_button.setActionCommand("save_player");

        // listener bottone salva tutto
        save_all_button.addActionListener(this);
        save_all_button.setActionCommand("print");

        // listener bottone random tutti i giocatori
        random_all_stats.addActionListener(this);
        random_all_stats.setActionCommand("all");
    }

    public void valueChanged(ListSelectionEvent e) {
        if (formation_changed) {
            enter_number.setText(Integer.toString(players[0].getNumber()));
            stat_field1.setValue(players[0].getAttack());
            stat_field2.setValue(players[0].getDefense());
            stat_field3.setValue(players[0].getPower());
            stat_field4.setValue(players[0].getPrecision());
            stat_field5.setValue(players[0].getSpeed());
            stat_field6.setValue(players[0].getTackle());
            stat_field7.setValue(players[0].getGoal_keeping());
            formation_changed = false;
        }
        else {
            JList l = (JList)e.getSource();
            if (l.getSelectedIndex() == 0) {
                // add goal keeping stat only if the goal keeper is selected
                players_stats_panel.add(keeper_label, 2);
                players_stats_panel.add(stat_field7, "wrap", 3);
                stat_field7.setValue(players[0].getGoal_keeping());
                players_stats_panel.revalidate();
                players_stats_panel.repaint();
            }
            else {
                players_stats_panel.remove(keeper_label);
                players_stats_panel.remove(stat_field7);
                players_stats_panel.revalidate();
                players_stats_panel.repaint();
            }
            enter_number.setText(Integer.toString(players[l.getSelectedIndex()].getNumber()));
            stat_field1.setValue(players[l.getSelectedIndex()].getAttack());
            stat_field2.setValue(players[l.getSelectedIndex()].getDefense());
            stat_field3.setValue(players[l.getSelectedIndex()].getPower());
            stat_field4.setValue(players[l.getSelectedIndex()].getPrecision());
            stat_field5.setValue(players[l.getSelectedIndex()].getSpeed());
            stat_field6.setValue(players[l.getSelectedIndex()].getTackle());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("change_formation")){
            formation_changed = true;
            JComboBox cb = (JComboBox)e.getSource();
            String scheme = (String)cb.getSelectedItem();
            updatePlayers(scheme);
        }
    }

    public void updatePlayers(String scheme) {
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

    // ritorna il pannello padre
    public JSplitPane getSplitPane() {
        return split_panel;
    }

    public JPanel getTeamPanel() {
        return team_panel;
    }

    public JButton getGenerateButton() {
        return save_all_button;
    }

    public JButton getRandomButton() {
        return random_all_stats;
    }
}
