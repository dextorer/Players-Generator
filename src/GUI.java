/*******************************************************
 * 													   *
 * Tool usato per generare le statistiche iniziali dei *
 * giocatori.										   * 
 * 													   *
 *******************************************************/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;

public class GUI  {

    public void actionPerformed(ActionEvent e) {

    	
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
    /*
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
    	}*/
    }

	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
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

        // Puts the window in the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width-570)/2;
        int y = (dim.height-450)/2;
        t2_frame.setLocation(x,y);
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
