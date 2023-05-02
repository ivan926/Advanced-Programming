package hangman;

public class EmptyDictionaryException extends Exception {
    EmptyDictionaryException()
    {
        System.out.println("Empty dictionary");
    }

	//Thrown when dictionary file is empty or no words in dictionary match the length asked for
}
