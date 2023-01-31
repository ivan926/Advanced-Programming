package hangman;

public class EmptyDictionaryException extends Exception {
    public EmptyDictionaryException(String message) {
        System.out.println(message);
    }
    //Thrown when dictionary file is empty or no words in dictionary match the length asked for
}
