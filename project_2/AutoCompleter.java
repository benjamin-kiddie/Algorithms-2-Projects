/**
 * AutoCompleter object for CS 1501 project 2
 * @author	Benjamin Kiddie
 */
package project_2;

import java.util.*;
import java.io.*;

public class AutoCompleter implements AutoComplete_Inter {

    DLB dictionary = new DLB();
    UserHistory history = new UserHistory();

	/**
	 * Constructor for only dictionary file.
	 *
	 * @param	dict File name to be inserted into dictionary.
	 */
    public AutoCompleter(String dict) {
        populateDLB(new File(dict));
    }

	/**
	 * Constructor for only dictionary and user history file.
	 *
	 * @param	dict File name to be inserted into dictionary.
	 * @param	hist File name to be inserted into user history.
	 */
	public AutoCompleter(String dict, String hist) {
		populateDLB(new File(dict));
		populateUserHistory(new File(hist));
	}

	/**
	 * Helper method for constructors. Reads in strings from input
	 * file and adds them to dictionary.
	 *
	 * @param	dict File being read from.
	 */
    private void populateDLB(File dict) {
		try { 
		Scanner scan = new Scanner(dict);
		while (scan.hasNextLine()) 
			dictionary.add(scan.nextLine());
		scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method for constructors. Reads in strings from input
	 * file and adds them to user history.
	 *
	 * @param	hist File being read from.
	 */
	private void populateUserHistory(File hist) {
		try {
		Scanner scan = new Scanner(hist);
		while (scan.hasNextLine())
			history.add(scan.nextLine());
		scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * Produce up to 5 suggestions based on the current word the user has
	 * entered These suggestions should be pulled first from the user history
	 * dictionary then from the initial dictionary. Any words pulled from user
	 * history should be ordered by frequency of use. Any words pulled from
	 * the initial dictionary should be in ascending order by their character
	 * value ("ASCIIbetical" order).
	 *
	 * @param 	next char the user just entered
	 *
	 * @return	ArrayList<String> List of up to 5 words prefixed by cur
	 */	
	public ArrayList<String> nextChar(char next) {
		// array list where reccomendations will be stored
		ArrayList<String> reccomendations = new ArrayList<String>(5);
		// first, pull reccomendations from user history
		int i = 0;
		if (history.count() > 0 && history.searchByChar(next) >= 0) {
			ArrayList<String> histreccs = history.suggest();
			for (String word : histreccs) {
				reccomendations.add(histreccs.get(i++));
				if (i == 5)
					break;
			}
		}
		// if our list is now full, return our reccomendations
		if (i == 5)
			return reccomendations;
		// otherwise, turn to the dictionary
		if (dictionary.count() > 0 && dictionary.searchByChar(next) >= 0 ) {
			ArrayList<String> dictreccs = dictionary.suggest();
			int j = 0;
			for (String word : dictreccs) {
				reccomendations.add(dictreccs.get(j++));
				if (++i == 5)
					break;
			}
		}
		// return the finished product
		return reccomendations;
    }

	/**
	 * Process the user having selected the current word
	 *
	 * @param 	cur String representing the text the user has entered so far
	 */
	public void finishWord(String cur) {
		// add word to history
		history.add(cur);
		// reset the byCharSearch in both data structures
		history.resetByChar();
		dictionary.resetByChar();
    }

	/**
	 * Save the state of the user history to a file
	 *
	 * @param	fname String filename to write history state to
	 */
	public void saveUserHistory(String fname) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fname));
			ArrayList<String> state = history.getState();
			for (String word : state) {
				writer.write(word);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }

}
