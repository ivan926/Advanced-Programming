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

		if(args.length < 1)
		{
			System.out.println("error insufficient command line arguments");

		}
		Trie test = new Trie();
		Scanner scanner = new Scanner(input);

		while(scanner.hasNext())
		{
			test.add(scanner.next());
		}



		ISpellCorrector corrector = new SpellCorrector();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		
		System.out.println("Suggestion is: " + suggestion);

	}

}
