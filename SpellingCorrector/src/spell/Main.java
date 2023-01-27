package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];

		File input = new File("test.txt");
		File input2 = new File("test1.txt");

		if(args.length < 1)
		{
			System.out.println("error insufficient command line arguments");

		}
		Trie test = new Trie();
		Trie obj = new Trie();
		Scanner scanner = new Scanner(input);
		Scanner scanner2 = new Scanner(input2);

		while(scanner.hasNext())
		{
			test.add(scanner.next());
		}

		while(scanner2.hasNext())
		{
			obj.add(scanner2.next());
		}



		//System.out.println(test.getNodeCount());

//		System.out.println(test.find("hel"));
//
//		System.out.println("\n\n");
//
//		System.out.println(test.find("zebra"));
//
//		System.out.println(test.getWordCount());
//
//		System.out.println(obj.getWordCount());
//
//
//		System.out.println(test.getNodeCount());
//
//		System.out.println(obj.getNodeCount());
////
////		System.out.println(test.toString());
//

	//test case is not correct a-z not equals to each other with other object?
		//it returns true, trie node test says it is false
		//System.out.println(test.equals(obj));



//		test.add("he");
//
//
//		test.add("he");
//
//		test.add("hello");
//
//		test.add("hello");
//
//		test.add("hello");

//        Create an instance of your corrector here



//		System.out.println(tes2.suggestSimilarWord("zupem"));
//		String wird = (String)tes2.suggestSimilarWord("zupem");
//
//		System.out.println(tes2.suggestSimilarWord("cooool"));


		ISpellCorrector corrector = new SpellCorrector();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		
		System.out.println("Suggestion is: " + suggestion);

	}

}
