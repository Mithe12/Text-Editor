/**
 *
 */
package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author UC San Diego Intermediate MOOC team
 * @author Pratyushotpal Madhukar
 *
 */
public class NearbyWords implements SpellingSuggest {
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	// For use in the Optional Optimization in Part 2.
	private static final int THRESHOLD = 1000;

	Dictionary dict;

	public NearbyWords (Dictionary dict)
	{
		this.dict = dict;
	}


	/** Return the list of Strings that are one modification away
	 * from the input string.
	 * @param s The original String
	 * @param wordsOnly controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> retList = new ArrayList<String>();
		   insertions(s, retList, wordsOnly);
		   subsitution(s, retList, wordsOnly);
		   deletions(s, retList, wordsOnly);
		   return retList;
	}


	/** Add to the currentList Strings that are one character mutation away
	 * from the input string.
	 * @param s The original String
	 * @param currentList is the list of words to append modified words
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void subsitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the
				// letters in the String
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) &&
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}

	/** Add to the currentList Strings that are one character insertion away
	 * from the input string.
	 * @param s The original String
	 * @param currentList is the list of words to append modified words
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		// TODO: Implement this method
		for(int index=0; index <= s.length(); index++) {
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				StringBuilder sb = new StringBuilder(s);
				sb.insert(index, (char)charCode);

				if(!currentList.contains(sb.toString()) &&
						(!wordsOnly || dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.
	 * @param s The original String
	 * @param currentList is the list of words to append modified words
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		// TODO: Implement this method
		for(int index=0; index < s.length(); index++) {
			StringBuilder sb = new StringBuilder(s);
			sb.deleteCharAt(index);

			if(!currentList.contains(sb.toString()) &&
					(!wordsOnly || dict.isWord(sb.toString())) &&
					!s.equals(sb.toString())) {
				currentList.add(sb.toString());
			}

		}
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.
	 * @param word The misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {

		// initial variables
		List<String> queue = new LinkedList<String>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same
														   // string multiple times
		List<String> retList = new LinkedList<String>();   // words to return


		// insert first node
		queue.add(word);
		visited.add(word);

		//System.out.println("Starting while loop.");
		// TODO: Implement the remainder of this method, see assignment for algorithm
		//int counter = 0;
		while( !queue.isEmpty() && retList.size()<=numSuggestions && visited.size() <= THRESHOLD) {
			//System.out.println("queue: " + queue);

			String curr = queue.remove(0);
			//System.out.println("queue size after removal: " + queue.size());
			//System.out.println("visited: " + visited);

			//System.out.println("curr: " + curr + " retList.size(): " + retList.size());

			List<String> neighbors = distanceOne(curr, true);
			//System.out.println("size of neighbors list: " + neighbors.size());

			//counter = 0;
			for(String neighbor : neighbors) {
				//System.out.print(counter++ + ",");
				//System.out.println("neighbor: " + neighbor);

				if( !visited.contains(neighbor) ) {
					//System.out.println("Adding " + neighbor + " to visited queue." );

					visited.add(neighbor);

					//System.out.println("Appending " + neighbor + " to queue.");
					queue.add(queue.size(), neighbor);
					//System.out.println("queue size after adding: " + queue.size());
					//System.out.println("String at the end of queue: " + queue.get(queue.size()-1));
					if(retList.size() >= numSuggestions) {
						break;
					}
					if( dict.isWord(neighbor)) {
						//System.out.println(neighbor + " is a word. Adding to the list of words to return.");
						retList.add(neighbor);
					}
				}
			}
			//System.out.println();
			//System.out.println("words list: " + retList);
			//System.out.println();
		}

		return retList;

	}

   public static void main(String[] args) {
	   // basic testing code to get started
	   String word = "dag";
	   // Pass NearbyWords any Dictionary implementation you prefer

	   Dictionary d = new DictionaryHashSet();
	   DictionaryLoader.loadDictionary(d, "test_cases/dict2.txt");
	   NearbyWords w = new NearbyWords(d);

	   List<String> l = w.distanceOne(word, true);
	   System.out.println("One away word Strings for for \""+word+"\" are:");
	   System.out.println(l+"\n");

	   l = w.distanceOne(word, false);
	   System.out.println("boolean wordsOnly is false: ");
	   System.out.println("One away word Strings for for \""+word+"\" are:");
	   System.out.println(l+"\n");


	   word = "dag";
	   List<String> suggest = w.suggestions(word, 4);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest);


   }

}
