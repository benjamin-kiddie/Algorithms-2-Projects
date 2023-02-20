/**
 * UserHistory object for CS 1501 project 2
 * @author	Benjamin Kiddie
 */
package project_2;

// for array list
import java.util.ArrayList;
// for hashmap object
import java.util.HashMap;
// for string methods
import java.lang.String;

public class UserHistory implements Dict {

	// constant termination character
	private final char terminator = '^';
	
	// root node of history
    private WeightedDLBNode root = new WeightedDLBNode(' ');

	// count of words in history
	private int count = 0;

	// used for by-character searching
	private WeightedDLBNode bcnode = this.root;
	private String currentSearch = "";

    /**
	 * Add a new word to the dictionary
	 *
	 * @param 	key New word to be added to the dictionary
	 */
	public void add(String key) {
		// add terminator to end
		key += terminator;
		// call recursive method
		this.root.setDown(recursiveAdd(this.root.getDown(), key, 0));
    }

	/**
	 * Helper method for add(). Steps through DLB recursively, adding nodes as necessary
	 * along the way.
	 * 
	 * @param		 curr WeightedDLBNode currently being operated on
	 * @param		  key String being added to DLB
	 * @param	charCount Index of current character being added into DLB
	 * 
	 * @return Current WeightedDLBNode when alterations are finished
	 */
	private WeightedDLBNode recursiveAdd(WeightedDLBNode curr, String key, int charCount) {
		// base case: current node is null
		if (curr == null) {
			curr = new WeightedDLBNode(key.charAt(charCount));
			if (curr.getLet() == terminator) {
				curr.setWeight(1);
				++count;
				return curr;
			}
			else
				curr.setDown(recursiveAdd(curr.getDown(), key, ++charCount));
		}
		// otherwise, check validity of this letter
		else if (curr.getLet() == key.charAt(charCount)) {
			// if we reach the end of inserting a dupe, increment weight and return
			if (curr.getLet() == terminator) {
				curr.setWeight(curr.getWeight() + 1);
				return curr;
			}
			// otherwise, continue down the DLB
			else
				curr.setDown(recursiveAdd(curr.getDown(), key, ++charCount));
		}
		// key doesn't match, we'll have to go to the right
		else
			curr.setRight(recursiveAdd(curr.getRight(), key, charCount));
		// sort and return curr when we're done updating
		return sort(curr);
	}

	/**
	 * Helper method for recursiveAdd(). Places a node into a sorted position in 
	 * the current portion of the linked list within the DLB. 
	 *
	 * @param 	start WeightedDLBNode that is being sorted
	 *
	 * @return 	New head of this portion of the linked list
	 */
	private WeightedDLBNode sort(WeightedDLBNode start) {
		// if start is null, alone, in the right place, or a terminator, return the list as is
		if (start == null || start.getRight() == null || start.getLet() == terminator)
			return start;
		if (start.getLet() < start.getRight().getLet() && start.getRight().getLet() != terminator)
			return start;
		// make a reference to the eventual new head of the list and an incrementing node
		WeightedDLBNode head = start.getRight();
		WeightedDLBNode curr = head;
		// otherwise, peek through the list for a value greater than start.getLet(), then
		// insert start behind that node
		while (curr.getRight() != null) {
			if (start.getLet() < curr.getRight().getLet() && curr.getRight().getLet() != terminator) {
				start.setRight(curr.getRight());
				curr.setRight(start);
				return head;
			}
			curr = curr.getRight();
		}
		// if this fails and we reach the end, then make start the last node
		start.setRight(null);
		curr.setRight(start);
		return head;
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
		return recursiveContains(this.root.getDown(), key, 0);
    }

	/**
	 * Helper method for contains(). Steps through DLB recursively looking for given String.
	 * 
	 * @param 	   curr WeightedDLBNode currently being operated on
	 * @param 	    key String being searched for
	 * @param charCount Index of current character being looked for
	 * 
	 * @return True if entire string is found, false otherwise
	 */
	private boolean recursiveContains(WeightedDLBNode curr, String key, int charCount) {
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
		return recursiveContainsPrefix(this.root.getDown(), pre, 0);
    }

	/**
	 * Helper method for containsPrefix(). Recursively steps through DLB to determine if 
	 * given prefix is contained in trie.
	 * 
	 * @param 	   curr WeightedDLBNode currently being operated on.
	 * @param 		pre Prefix being searched for.
	 * @param charCount Index of current character being looked for.
	 * 
	 * @return True if prefix is in DLB, false otherwise.
	 */
	private boolean recursiveContainsPrefix(WeightedDLBNode curr, String pre, int charCount) {
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
	 * @param node WeightedDLBNode being examined.
	 * 
	 * @return True if node is prefix, false otherwise.
	 */
	private boolean isPrefix(WeightedDLBNode node) {
		// isolate the next node in the DLB
		WeightedDLBNode down = node.getDown();
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
	 * Helper method for multiple methods. Determines if node meing examined is a word.
	 * 
	 * @param node WeightedDLBNode being examined.
	 * 
	 * @return True if node is a word, false otherwise.
	 */
	private boolean isWord(WeightedDLBNode node) {
		// isolate the next node in the DLB
		WeightedDLBNode down = node.getDown();
		// if it's a dead end, return false
		if (down == null)
			return false;
		// otherwise, examine this level for the termination character
		else {
			for (WeightedDLBNode curr = down; curr != null; curr = curr.getRight()) {
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
		if (bcnode.getDown() == null)
			return result;
		// examine this level for the next character
		for(WeightedDLBNode curr = bcnode.getDown(); curr != null; curr = curr.getRight()) {
			// if we find the correct letter
			if (curr.getLet() == next) {
				// progress bcnode along the DLB and update history
				bcnode = curr;
				currentSearch += curr.getLet();
				// we first check if this node is a prefix
				if (isPrefix(curr))
					++result;
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
		bcnode = this.root;
		currentSearch = "";
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
		ArrayList<String> allWords = new ArrayList<String>();
		HashMap<String, Integer> frequency = new HashMap<String, Integer>();
		// find all words and order them by frequency
		frequency = findFrequency(bcnode.getDown(), frequency, currentSearch);
		allWords = recursiveSuggest(bcnode.getDown(), frequency, allWords, currentSearch);
		// take the first 5 (most frequent) and return them
		int i = 0;
		for (String word : allWords) {
			if (i == 5)
				break;
			suggestions.add(allWords.get(i++));
		}
		return suggestions;
    }

	/**
	 * Helper method for suggest(). Recursively traverses DLB and puts words in an
	 * HashMap with their frequency as it goes.
	 * 
	 * @param curr WeightedDLBNode currently being operated on.
	 * @param frequency Map of words to their frequency.
	 * @param word Current set of characters.
	 * 
	 * @return Updated map of words and their frequency.
	 */
	private HashMap<String, Integer> findFrequency(WeightedDLBNode curr, HashMap<String, Integer> frequency, String prefix) {
		// base case: dead end
		if (curr == null)
			return frequency;
		// otherwise, consider this node
		// if this is the end of a word, add that word to the list
		if (curr.getLet() == terminator)
			frequency.put(prefix, curr.getWeight());
		// now examine the nodes downward and to the right and modify the list accordingly
		frequency = findFrequency(curr.getDown(), frequency, (prefix + curr.getLet()));
		frequency = findFrequency(curr.getRight(), frequency, prefix);
		// once we're done, return the list
		return frequency;
	}

	/**
	 * Helper method for suggest() methods. Recursively traverses DLB and puts words in an
	 * ArrayList as it goes.
	 * 
	 * @param curr WeightedDLBNode currently being operated on.
	 * @param list List of words.
	 * @param word Current set of characters.
	 * 
	 * @return Updated ArrayList of words.
	 */
	private ArrayList<String> recursiveSuggest(WeightedDLBNode curr, HashMap<String, Integer> frequency, ArrayList<String> list, String prefix) {
		// base case: dead end
		if (curr == null)
			return list;
		// otherwise, consider this node
		// if this is the end of a word, add that word to the list
		if (curr.getLet() == terminator) {
			int i = 0;
			while (i < list.size()) {
				if (curr.getWeight() > frequency.get(list.get(i)))
					break;
				++i;
			}
			if (i == list.size())
				list.add(prefix);
			else
				list.add(i, prefix);
		}
		// now examine the nodes downward and to the right and modify the list accordingly
		list = recursiveSuggest(curr.getDown(), frequency, list, (prefix + curr.getLet()));
		list = recursiveSuggest(curr.getRight(), frequency, list, prefix);
		// once we're done, return the list
		return list;
	}

	/**
	 * List all of the words currently stored in the dictionary
	 * 
	 * @return	ArrayList<String> List of all valid words in the dictionary
	 */
	public ArrayList<String> traverse() {
		// call recursive method
		ArrayList<String> words = new ArrayList<String>();
		return recursiveTraverse(this.root.getDown(), words, "");
    }

	/**
	 * Helper method for multiple methods. Recursively traverses DLB and puts words in an
	 * ArrayList as it goes.
	 * 
	 * @param curr WeightedDLBNode currently being operated on.
	 * @param list List of words.
	 * @param word Current set of characters.
	 * 
	 * @return Updated ArrayList of words.
	 */
	private ArrayList<String> recursiveTraverse(WeightedDLBNode curr, ArrayList<String> list, String prefix) {
		// base case: dead end
		if (curr == null)
			return list;
		// otherwise, consider this node
		// if this is the end of a word, add that word to the list
		if (curr.getLet() == terminator)
			list.add(prefix);
		// now examine the nodes downward and to the right and modify the list accordingly
		list = recursiveTraverse(curr.getDown(), list, (prefix + curr.getLet()));
		list = recursiveTraverse(curr.getRight(), list, prefix);
		// once we're done, return the list
		return list;
	}

	/**
	 * Count the number of words in the dictionary
	 *
	 * @return	int, the number of (distinct) words in the dictionary
	 */
	public int count() {
		return this.count;
    }

	public ArrayList<String> getState() {
		ArrayList<String> state = new ArrayList<String>();
		// call recursive method
		state = recursiveGetState(this.root.getDown(), state, "");
		return state;
	}

	private ArrayList<String> recursiveGetState(WeightedDLBNode curr, ArrayList<String> list, String prefix) {
		// base case: dead end
		if (curr == null)
			return list;
		// otherwise, consider this node
		// if this is the end of a word, add that word to the list in the index of it's weight
		if (curr.getLet() == terminator) {
			for (int i = 0; i < curr.getWeight(); ++i)
				list.add(prefix);
		}
		// now examine the nodes downward and to the right and modify the list accordingly
		list = recursiveGetState(curr.getDown(), list, (prefix + curr.getLet()));
		list = recursiveGetState(curr.getRight(), list, prefix);
		// once we're done, return the list
		return list;
	}
    
}