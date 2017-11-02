package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}


	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		String pattern = "\\s+";//"[^a-zA-Z]+";
		String[] words = sourceText.split(pattern);

		/*
		System.out.println("words after splitting: ");
		for(int i=0; i<words.length; i++) {
			System.out.println(words[i]);
		}
		*/


		if(starter.isEmpty())
			starter = words[0];
		String prevWord = starter;
		boolean prevWordInList = false;

		for(int i=1; i<words.length;i++) {
			/*
			System.out.println("Start of loop.");
			System.out.println("wordList: " + wordList);
			System.out.println("prevWord: " + prevWord);
			System.out.println("current word: " + words[i]);
			*/

			prevWordInList = false;
			for(ListNode node : wordList) {
				//System.out.println("nodeWord: " + node.getWord());

				if( prevWord.equals(node.getWord()) ) {
					//System.out.println("prevWord found in wordList");

					//add current word to the list of next words
					node.addNextWord(words[i]);

					//System.out.println(node);

					prevWordInList = true;
					break;
				}
			}
			if( !prevWordInList ) {
				//System.out.println(prevWord + " not found in wordList.\nAdding new Entry");

				ListNode newNode = new ListNode(prevWord);

				//System.out.println("Adding " + words[i] + " to the nextWord list of " + prevWord);

				newNode.addNextWord(words[i]);
				wordList.add(newNode);

				//System.out.println(wordList);


			}
			prevWord = words[i];

			//System.out.println("End of loop\n");
		}

		//For last word
		prevWordInList = false;
		for(ListNode node : wordList) {
			if( words[words.length-1].equals(node.getWord()) ) {
				node.addNextWord(starter);
				prevWordInList = true;
				break;
			}
		}
		if( !prevWordInList ) {
			ListNode newNode = new ListNode(prevWord);
			newNode.addNextWord(starter);
			wordList.add(newNode);
		}


	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		String currWord = starter;
		String output = "";

		if( starter.isEmpty() || numWords <= 0 ) {
			return output;
		}

		output += currWord + " ";
		int counter = 1;

		while( counter < numWords ) {
			for(ListNode node : wordList ) {
				if( currWord.equals(node.getWord()) ) {
					String randomWord = node.getRandomNextWord(rnGenerator);
					output += randomWord + " ";
					currWord = randomWord;
					counter++;
					break;
				}
			}
		}
		return output;
	}


	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
	}

	// TODO: Add any private helper methods you need here.


	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.
	 * @param args
	 */
	public static void main(String[] args)
	{
		MarkovTextGeneratorLoL test = new MarkovTextGeneratorLoL(new Random(42));
		String testString = /*"hi. there hi hi hi hi Leo";*/"hi hi hi me hello";
		//System.out.println(textString.length());
		//System.out.println(testString);
		test.train(testString);
		System.out.println(test);
		System.out.println(test.generateText(4));

		System.out.println();

		test.train("This hi how me me hello me me");
		System.out.println(test);
		System.out.println(test.generateText(20));

		/*
		String sourceText = "Hi Hi hi This is a is random text";
		test.retrain(sourceText);
		System.out.println(test);
		System.out.println(test.generateText(0));
		System.out.println();
		*/

		/*
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//System.out.println(textString.length());
		//System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		System.out.println("\n\n");

		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		//System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		*/

	}

}

/** Links a word to the next words in the list
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from
	    // the MarkovTextGeneratorLoL class
		int range = nextWords.size();
		//randomInt is a value between 0(inclusive) and nextWords.size()(exclusive)
		int randomInt = generator.nextInt(range);
	    return nextWords.get(randomInt);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

}


