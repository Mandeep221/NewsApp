package com.msarangal.lib;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Hamburger {

    private boolean cheese;
    private boolean onions;
    private boolean pickles;

    public boolean hasCheese() {
        return cheese;
    }

    public boolean hasOnions() {
        return onions;
    }

    public static void main(String[] args) {
//        String word = "ABA";
//        System.out.println("Is " + word + " a Palindrome? " + isPalindrome(word));

        String wordOne = "level";
        String wordTwo = "vell";

        System.out.println("Are words " + wordOne + " and " + wordTwo +" anagrams? " + isAnagram(wordOne, wordTwo));
    }

    private static boolean isPalindrome(String word) {
        try {
            int startIndex = 0;
            int endIndex = word.length() - 1;

            while (endIndex > startIndex) {
                if (Character.toLowerCase(word.charAt(startIndex)) != Character.toLowerCase(word.charAt(endIndex))) {
                    return false;
                }
                endIndex--;
                startIndex++;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Exception is = " + e.getLocalizedMessage());
            return false;
        }
    }

    private static boolean isAnagram(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }

        HashMap<Character, Integer> mapWordOne = new HashMap<>();
        HashMap<Character, Integer> mapWordTwo = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            mapWordOne.put(s.charAt(i), countChar(s.charAt(i), s));
            mapWordTwo.put(t.charAt(i), countChar(t.charAt(i), t));
        }

        if (mapWordOne.keySet().size() != mapWordTwo.keySet().size()) {
            return false;
        }

        for (Map.Entry<Character, Integer> characterIntegerEntry : mapWordOne.entrySet()) {
            Character key = characterIntegerEntry.getKey();

            if (!Objects.equals(mapWordTwo.get(key), mapWordOne.get(key))) {
                return false;
            }
        }
        return true;
    }

    private static int countChar(char a, String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == a) {
                count++;
            }
        }
        return count;
    }

    public boolean hasPickles() {
        return pickles;
    }

    private Hamburger(boolean cheese, boolean onions, boolean pickles) {
        this.cheese = cheese;
        this.onions = onions;
        this.pickles = pickles;
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private boolean cheese;
        private boolean onions;
        private boolean pickles;

        public Builder cheese(boolean putCheese) {
            cheese = putCheese;
            return this;
        }

        public Builder onions(boolean putOnions) {
            onions = putOnions;
            return this;
        }

        public Builder pickles(boolean putPickles) {
            pickles = putPickles;
            return this;
        }

        public Hamburger build() {
            return new Hamburger(cheese, onions, pickles);
        }
    }
}
