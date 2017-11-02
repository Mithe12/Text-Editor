package spelling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}


	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		//System.out.println();
		//System.out.println("word: " + word);
		boolean flag = false;
		word = word.toLowerCase();
		TrieNode current = root;
		TrieNode child;

		for(int i=0; i<word.length(); i++) {
			char ch = word.charAt(i);
			//System.out.println("char at " + i + ": " + ch);
			child = current.getChild( ch );//null initially

			if( child != null ) {
				current = child;
			}
			else {
				//System.out.println("inserting " + ch);
				current.insert( ch );
				current = current.getChild(ch);
				flag = true;
			}
		}
		/*
		current.setEndsWord(true);
		if(flag) size++;
		*/
		if( !current.endsWord() ) {
			current.setEndsWord(true);
			size++;
		}
		//System.out.println("word: " + word);
		//printTree();
	    return flag;
	}

	/**
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}


	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s)
	{
	    // TODO: Implement this method
		s = s.toLowerCase();
		TrieNode current = root;
		TrieNode child;

		int i=0;
		while( true ) { //earlier1: current!=null
			if( i >= s.length() ) {
				return false;
			}
			child = current.getChild(s.charAt(i++));

			if(child == null) {
				return false;
			}

			if(child.endsWord() && child.getText().equalsIgnoreCase(s)){
				return true;
			}
			current = child;
		}
		//return false; //earlier1
	}

	/**
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions)
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 TrieNode stem = root;
    	 //TrieNode child;
    	 List<String> predictions = new ArrayList<>();

    	 prefix = prefix.toLowerCase();

    	 for(int i=0; i<prefix.length(); i++) {
    		 //char ch = prefix.charAt(i);
    		 stem = stem.getChild(prefix.charAt(i));
    		 if(stem == null) {
    			 return predictions; //try returning predictions which is empty at this point
    		 }
    	 }

    	 Queue<TrieNode> q = new LinkedList<>();
    	 q.add(stem);

    	 while( !q.isEmpty() && predictions.size()<numCompletions ) {
    		 TrieNode current = q.remove();
    		 if( current.endsWord() ) {
    			 predictions.add(current.getText());
    		 }

    		 //TrieNode next = null;
    		 for(Character c : current.getValidNextCharacters() ) {
    			 q.add(current.getChild(c));
    		 }
    	 }
         return predictions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 		System.out.println();
 	}

 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null)
 			return;

 		System.out.println(curr.getText());

 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			//System.out.println("char is: " + c);
 			next = curr.getChild(c);
 			printNode(next);
 			//System.out.println("returning from char " + c );
 		}
 	}



}