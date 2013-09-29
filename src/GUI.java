/*******************************************************
 * 													   *
 * Tool usato per generare le statistiche iniziali dei *
 * giocatori.										   * 
 * 													   *
 *******************************************************/

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.google.gson.Gson;

import net.miginfocom.swing.MigLayout;

public class GUI implements ListSelectionListener, ActionListener {

	private JList list;													// lista nomi giocatori
	private Player[] players = new Player[22];							// vettore di giocatori
	
	// vettore di comodo con i nomi dei giocatori usato per creare la lista iniziale
	private String[] players_names = new String[22];					
	private JSplitPane split_panel;     								// pannello "padre" 
	
	/**** Elementi Interfaccia ****/
	private JLabel name;												// nome del giocatore
	private JTextField enter_name;										// campo in cui inserire il nome
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
	
	// Costruttore
	public GUI() {
		
		// creo array giocatori ed estraggo nomi per la lista del menu laterale
		for(int i = 1; i <= players.length; i++) {
			players[i-1] = new Player();
			players_names[i-1] = "Player " + i;
		}
		
        /****************************************
    	 *			LISTA GIOCATORI				*											 												    *
    	 ****************************************/
		// Crea la lista dei giocatori e la mette in uno scroll pane
        list = new JList(players_names);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        JScrollPane players_list_panel = new JScrollPane(list);
        players_list_panel.setSize(100, 400);
        
        /****************************************
    	 *		PANNELLO MODIFICA STATISTICHE	*											 												    *
    	 ****************************************/
        // crea il pannello in cui sarà possibile modificare le statistiche 
        JPanel players_stats_panel = new JPanel();
        players_stats_panel.setSize(250, 400);
        MigLayout mlayout = new MigLayout(
        	      "wrap 4",           					  // Layout Constraints
        	      "[center][center]40[center][center]",   // Column constraints
        	      "[center]20[center]");    			  // Row constraints
        players_stats_panel.setLayout(mlayout);
        
        // label del nome
        name = new JLabel("Name:");
        name.setHorizontalAlignment(JLabel.CENTER);
        
        // editor per modificare il nome
     //   enter_name = new JTextField(players[list.getSelectedIndex()].getName());
        
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
        JLabel stat7 = new JLabel("Goal Keeping Skills:");
        stat5.setHorizontalAlignment(JLabel.CENTER);
        stat_field7 = new JSpinner(spinner_model7);
                        
        // aggiunge widget al pannello
        players_stats_panel.add(name);
        players_stats_panel.add(enter_name, "span, grow");
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
        players_stats_panel.add(stat7);
        players_stats_panel.add(stat_field7,"wrap 25" );
        players_stats_panel.add(random_stats, "span 2, center");
        players_stats_panel.add(save_button, "span 2, center");

        /************************************************************
    	 *		ASSEMBLA IL PANNELLO DELLA FINESTRA PRINCIPALE		*											 												    *
    	 ************************************************************/
        // Create a split pane with the two scroll panes in it.
        split_panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, players_list_panel, players_stats_panel);
        split_panel.setDividerLocation(150);

        //Provide a preferred size for the split pane.
        split_panel.setPreferredSize(new Dimension(550, 400));
        
        // listener bottone random giocatore corrente
        random_stats.addActionListener(this);
        random_stats.setActionCommand("random");
        
        // listener bottone salva giocatore corrente
        save_button.addActionListener(this);
        save_button.setActionCommand("save");
        
        // listener bottone salva tutto
        save_all_button.addActionListener(this);
        save_all_button.setActionCommand("print");
        
        // listener bottone random tutti i giocatori
        random_all_stats.addActionListener(this);
        random_all_stats.setActionCommand("all");
    }
		
    //Listens to the list
    public void valueChanged(ListSelectionEvent e) {   
    	
    /*	// aggiorna i valori dell'interfaccia
        name.setText(players[list.getSelectedIndex()].getName());
    	enter_name.setText(players[list.getSelectedIndex()].getName());
    	stat_field1.setValue(players[list.getSelectedIndex()].getParameter(1));
    	stat_field2.setValue(players[list.getSelectedIndex()].getParameter(2));
    	stat_field3.setValue(players[list.getSelectedIndex()].getParameter(3));
    	stat_field4.setValue(players[list.getSelectedIndex()].getParameter(4));
    	stat_field5.setValue(players[list.getSelectedIndex()].getParameter(5));
    	stat_field6.setValue(players[list.getSelectedIndex()].getParameter(6));
    	stat_field7.setValue(players[list.getSelectedIndex()].getParameter(7));      */
    }

    public void actionPerformed(ActionEvent e) {
    	
    	// statistiche random giocatore corrente
    	if(e.getActionCommand().equals("random")){
    		Random rand = new Random();
    		stat_field1.setValue(rand.nextInt(100)); 
    		stat_field2.setValue(rand.nextInt(100)); 
    		stat_field3.setValue(rand.nextInt(100)); 
    		stat_field4.setValue(rand.nextInt(100)); 
    		stat_field5.setValue(rand.nextInt(100)); 
    		stat_field6.setValue(rand.nextInt(100)); 
    		stat_field7.setValue(rand.nextInt(100)); 
    	}
    	
    	// salva giocatore corrente
/*    	if(e.getActionCommand().equals("save")) {
    		players[list.getSelectedIndex()].setName(enter_name.getText());
    		players[list.getSelectedIndex()].setParameter(1, (Integer)stat_field1.getValue());
    		players[list.getSelectedIndex()].setParameter(2, (Integer)stat_field2.getValue());
    		players[list.getSelectedIndex()].setParameter(3, (Integer)stat_field3.getValue());
    		players[list.getSelectedIndex()].setParameter(4, (Integer)stat_field4.getValue());
    		players[list.getSelectedIndex()].setParameter(5, (Integer)stat_field5.getValue());
    		players[list.getSelectedIndex()].setParameter(6, (Integer)stat_field6.getValue());
    		players[list.getSelectedIndex()].setParameter(7, (Integer)stat_field7.getValue());
    	}   */
    	
    	// statistiche random tutti i giocatori
    /*	if(e.getActionCommand().equals("all")) {
    		Random rand = new Random();
    		for(int i = 0; i < players.length; i++) {
    			
    			// se genera stat per il giocatore la cui tab è aperta, aggiorniamo anche l'interfaccia grafica
    			if(i == list.getSelectedIndex()) {

    				int rand_int = rand.nextInt(100);
    				stat_field1.setValue(rand_int); 
    				players[i].setParameter(1, rand_int);
    				
    				rand_int = rand.nextInt(100);
    				stat_field2.setValue(rand_int); 
    				players[i].setParameter(2, rand_int);
    				
    				rand_int = rand.nextInt(100);
    				stat_field3.setValue(rand_int); 
    				players[i].setParameter(3, rand_int);
    				
    				rand_int = rand.nextInt(100);
    				stat_field4.setValue(rand_int); 
    				players[i].setParameter(4, rand_int);
    				
    				rand_int = rand.nextInt(100);
    				stat_field5.setValue(rand_int); 
    				players[i].setParameter(5, rand_int);
    				
    				rand_int = rand.nextInt(100);
    				stat_field6.setValue(rand_int); 
    				players[i].setParameter(6, rand_int);
    				
    				rand_int = rand.nextInt(100);
    				stat_field7.setValue(rand_int); 
    				players[i].setParameter(7, rand_int);
    			}
    			else {	// altrimenti aggiorniamo solo i valori del player
	    			players[i].setParameter(1, rand.nextInt(100));
	    			players[i].setParameter(2, rand.nextInt(100));
	    			players[i].setParameter(3, rand.nextInt(100));
	    			players[i].setParameter(4, rand.nextInt(100));
	    			players[i].setParameter(5, rand.nextInt(100));
	    			players[i].setParameter(6, rand.nextInt(100));
	    			players[i].setParameter(7, rand.nextInt(100));
    			}
    		}
    	}  */
    	
    	// salva le stat di tutti i giocatori e genera il file
    	if(e.getActionCommand().equals("print")){
    		
    		// conversione player --> json
        	Gson gson = new Gson();	
        	String json = new String();
        	
        	// concatena le statistiche in un'unica stringa json
        	for (int i = 0; i < players.length; i++) {
        		json = json + gson.toJson(players[i]) + '\n';
        	}
        	
        	// stampa su file
        	try {
        		// crea file di nome "Settings"
        		FileWriter fstream = new FileWriter("Settings");
        		BufferedWriter out = new BufferedWriter(fstream);
        		
        		// scrive sul file
        		out.write(json);
        		
        		// chiude lo stream
        		out.close();
        	} catch (Exception exc) { System.err.println("Error: " + exc.getMessage());}
    	}
    }
    
    // ritorna il pannello padre
    public JSplitPane getSplitPane() {
        return split_panel;
    }
    
    public JButton getGenerateButton() {
    	return save_all_button;
    }
    
    public JButton getRandomButton() {
    	return random_all_stats;
    }
    
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
    	   	
    	//Create and set up the window.
    /*    JFrame t1_frame = new JFrame("Team 1");
        t1_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t1_frame.setLayout(new FlowLayout());
        
        // aggiunge elementi al pannello
        GUI t1 = new GUI();
        t1_frame.getContentPane().add(t1.getSplitPane());
        t1_frame.getContentPane().add(t1.getRandomButton());
        t1_frame.getContentPane().add(t1.getGenerateButton());

        //Display the window.
        t1_frame.pack();
        t1_frame.setVisible(true);
        t1_frame.setSize(570, 470);
        t1_frame.setResizable(false);     */


        //Create and set up the window.
        JFrame t2_frame = new JFrame("Team 2");
        t2_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t2_frame.setLayout(new FlowLayout());

        // aggiunge elementi al pannello
        Team t2 = new Team();
        t2_frame.getContentPane().add(t2.getSplitPane());
     //   t2_frame.getContentPane().add(t2.getTeamPanel());
    //    t2_frame.getContentPane().add(t2.getRandomButton());
    //    t2_frame.getContentPane().add(t2.getGenerateButton());

        //Display the window.
        t2_frame.pack();
        t2_frame.setVisible(true);
        t2_frame.setSize(570, 470);
        t2_frame.setResizable(false);
    }	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createAndShowGUI();
            }
        });
	}

}
