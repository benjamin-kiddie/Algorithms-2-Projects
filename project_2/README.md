# Auto-complete Engine

## Project Overview
Assigned as project 2 of course CS 1501 (Algorithms and Data Structures 2). Requires the creation of an auto-completer that takes in a string 1 character at a time and prints reccomended words using a dictionary and user history. Implements the following methods:

1. `nextChar(char next)` - Produce up to 5 suggestions based on the current word the user has entered. These suggestions should be pulled first from the user history based on frequency, then the dictionary based on ASCIIbetical order,
2. `finishWord(String cur)` - Process the user havng selected the current word.
3. `saveUserHistory(String fname)` - Save the state of user history to a file.

To facilitate this, 2 different DLBs that act as dictionaries have also been created and implement the following methods:

1. `add(String key)` - Adds a new word to the dictionary.
2. `contains(String key)`- Checks if the dictionary contains a word.
3. `containsPrefix(String pre)` - Checks if the dictionary contains any words with `pre` as a prefix.
4. `searchByChar(char next)` - Searches for a word one character at a time. More details in comments.
5. `resetByChar()` - Resets the state of the search.
6. `suggest()` - Suggests up to 5 words based on the current state of the search.
7. `traverse()` - Lists all of the words currently contained in the dictionary.
8. `count()` - Returns the number of words currently in the dictionary.

## Contributors
Benjamin Kiddie - WeightedDLBNode, DLB, UserHistory, and AutoCompleter implementation.

Dr. Nicholas Farnan - Dict, DLBNode, AutoCompleter_Inter, and driver implementation. Project design and assignment.