/**
 * DLB object for CS 1501 project 2
 * @author	Benjamin Kiddie 
 */
package project_2;

// for array list
import java.util.ArrayList;
// for string methods
import java.lang.String;

public class DLB implements Dict {

	// constant termination character
	private final char terminator = '^';
	
	// root node of DLB
    private DLBNode root = null;

	// used for by-character searching
	private DLBNode bcnode = root;
	private String history = "";

    /**
	 * Add a new word to the dictionary
	 *
	 * @param 	key New word to be added to the dictionary
	 */
	public void add(String key) {
		// add terminator to end
		key += terminator;
		// call recursive method
		this.root = recursiveAdd(this.root, key, 0);
    }

	/**
	 * Helper method for add(). Steps through DLB recursively, adding nodes as necessary
	 * along the way.
	 * 
	 * @param		 curr DLBNode currently being operated on
	 * @param		  key String being added to DLB
	 * @param	charCount Index of current character being added into DLB
	 * 
	 * @return Current DLBNode when alterations are finished
	 */
	private DLBNode recursiveAdd(DLBNode curr, String key, int charCount) {
		// base case: current node is null
		if (curr == null) {
			curr = new DLBNode(key.charAt(charCount));
			if (curr.getLet() == terminator)
				return curr;
			else
				curr.setDown(recursiveAdd(curr.getDown(), key, ++charCount));
		}
		// otherwise, check validity of this letter
		else if (curr.getLet() == key.charAt(charCount)) {
			// if we reach the end of inserting a dupe, return
			if (curr.getLet() == terminator)
				return curr;
			// otherwise, continue down the DLB
			else
				curr.setDown(recursiveAdd(curr.getDown(), key, ++charCount));
		}
		// key doesn't match, we'll have to go to the right
		else
			curr.setRight(recursiveAdd(curr.getRight(), key, charCount));
		// when we're done, return
		return curr;
	}

    /**
	 * Check if the dictionary contains a word
	 *
	 * @param	key	Word to search the dictionary for
	 *
	 * @return	true if key is in the dictionary, false otherwise
	 */
	public boolean contains(String key) {
		// add terminator to key
		key += terminator;
		// call recursive method
		return recursiveContains(this.root, key, 0);
    }

	/**
	 * Helper method for contains(). Steps through DLB recursively looking for given String.
	 * 
	 * @param 	   curr DLBNode currently being operated on
	 * @param 	    key String being searched for
	 * @param charCount Index of current character being looked for
	 * 
	 * @return True if entire string is found, false otherwise
	 */
	private boolean recursiveContains(DLBNode curr, String key, int charCount) {
		// base case: dead end
		if (curr == null)
			return false;
		// otherwise, check validity of this letter
		else if (curr.getLet() == key.charAt(charCount)) {
			// if this letter matches and this is the termination character, return true
			if (curr.getLet() == terminator)
				return true;
			// otherwise, go down another layer
			return recursiveContains(curr.getDown(), key, ++charCount);
		}
		// if this letter doesn't match, we need to traverse right
		else
			return recursiveContains(curr.getRight(), key, charCount);
	}

    /**
	 * Check if a String is a valid prefix to a word in the dictionary
	 *
	 * @param	pre	Prefix to search the dictionary for
	 *
	 * @return	true if prefix is valid, false otherwise
	 */
	public boolean containsPrefix(String pre) {
		// call recursive method
		return recursiveContainsPrefix(this.root, pre, 0);
    }

	/**
	 * Helper method for containsPrefix(). Recursively steps through DLB to determine if 
	 * given prefix is contained in trie.
	 * 
	 * @param 	   curr DLBNode currently being operated on.
	 * @param 		pre Prefix being searched for.
	 * @param charCount Index of current character being looked for.
	 * 
	 * @return True if prefix is in DLB, false otherwise.
	 */
	private boolean recursiveContainsPrefix(DLBNode curr, String pre, int charCount) {
		// base case: dead end
		if (curr == null)
			return false;
		// otherwise, check validity of current letter
		else if (curr.getLet() == pre.charAt(charCount)) {
			// if we've reached the end of this prefix, check if it is a prefix
			if (charCount == (pre.length() - 1))
				return isPrefix(curr);
			// otherwise, continue downwards
			else
				return recursiveContainsPrefix(curr.getDown(), pre, ++charCount);
		}
		// if letter doesn't match, we have to move right
		else
			return recursiveContainsPrefix(curr.getRight(), pre, charCount);
	}

	/**
	 * Helper method for multiple methods. Determines if a given node
	 * is a prefix of another word.
	 * 
	 * @param node DLBNode being examined.
	 * 
	 * @return True if node is prefix, false otherwise.
	 */
	private boolean isPrefix(DLBNode node) {
		// isolate the next node in the DLB
		DLBNode down = node.getDown();
		// if it's a dead end, return false
		if (down == null)
			return false;
		// if down contains the termination character, check if there are words that contain this prefix
		else if (down.getLet() == terminator) {
			if (down.getRight() == null)
				return false;
			else
				return true;
		}
		// if down is not null and is not the terminator, then this is a prefix
		else
			return true;
	}

	/**
	 * Helper method for multiple method. Determines if node meing examined is a word.
	 * 
	 * @param node DLBNode being examined.
	 * 
	 * @return True if node is a word, false otherwise.
	 */
	private boolean isWord(DLBNode node) {
		// isolate the next node in the DLB
		DLBNode down = node.getDown();
		// if it's a dead end, return false
		if (down == null)
			return false;
		// otherwise, examine this level for the termination character
		else {
			for (DLBNode curr = down; curr != null; curr = curr.getRight()) {
				if (curr.getLet() == terminator)
					return true;
			}
		}
		// if we never encounter the terminator, return false;
		return false;
	}

    /**
	 * Search for a word one character at a time
	 *
	 * @param	next Next character to search for
	 *
	 * @return	int value indicating result for current by-character search:
	 *				-1: not a valid word or prefix
	 *				 0: valid prefix, but not a valid word
	 *				 1: valid word, but not a valid prefix to any other words
	 *				 2: both valid word and a valid prefix to other words
	 */
	public int searchByChar(char next) {
		// this int will keep track of the conditions that our search meets
		int result = -1;
		// base case: dead end
		if (bcnode == null)
			return result;
		// examine this level for the next character
		for(DLBNode curr = bcnode; curr != null; curr = curr.getRight()) {
			// if we find the correct letter
			if (curr.getLet() == next) {
				// progress bcnode along the DLB and update history
				bcnode = curr;
				history += curr.getLet();
				// we first check if this node is a prefix
				if (isPrefix(curr))
					result++;
				if (isWord(curr))
					result += 2;
			}
		}
		// return the result
		return result;
    }

    /**
	 * Reset the state of the current by-character search
	 */
	public void resetByChar() {
		// we'll bring bcnode back to the root and erase history, resetting our traversal along the DLB
		bcnode = root;
		history = "";
    }

    /**
	 * Suggest up to 5 words from the dictionary based on the current
	 * by-character search. Ordering should depend on the implementation.
	 * 
	 * @return	ArrayList<String> List of up to 5 words that are prefixed by
	 *			the current by-character search
	 */
	public ArrayList<String> suggest() {
		// make an array list to put values into
		ArrayList<String> suggestions = new ArrayList<String>();
		// find all of the words that begin with our history
		ArrayList<String> allWords = recursiveTraverse(bcnode, new ArrayList<String>(), history);
		// take the first 5 (most likely alphabetical) and return them
		for (int i = 0; i < 5; i++)
			suggestions.add(allWords.get(i));
		return suggestions;
    }

    /**
	 * List all of the words currently stored in the dictionary
	 * @return	ArrayList<String> List of all valid words in the dictionary
	 */
	public ArrayList<String> traverse() {
		return recursiveTraverse(this.root, new ArrayList<String>(), "");
    }

	/**
	 * Helper method for multiple methods. Recursively traverses DLB and puts words in an
	 * ArrayList as it goes.
	 * 
	 * @param curr DLBNode currently being operated on.
	 * @param list List of words.
	 * @param word Current set of characters.
	 * 
	 * @return Updated ArrayList of words.
	 */
	private ArrayList<String> recursiveTraverse(DLBNode curr, ArrayList<String> list, String word) {
		// base case: dead end
		if (curr == null)
			return list;
		// otherwise, consider this node
		else {
			// if this is the end of a word, add that word to the list
			if (curr.getLet() == terminator)
				list.add(word);
			// now examine the nodes downward and to the right and modify the list accordingly
			list = recursiveTraverse(curr.getDown(), list, (word + curr.getLet()));
			list = recursiveTraverse(curr.getRight(), list, word);
		}
		// once we're done, return the list
		return list;
	}

    /**
	 * Count the number of words in the dictionary
	 *
	 * @return	int, the number of (distinct) words in the dictionary
	 */
	public int count() {
		return this.traverse().size();
    }

}