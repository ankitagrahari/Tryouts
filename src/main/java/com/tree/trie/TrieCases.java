package com.tree.trie;

import java.util.Arrays;

public class TrieCases {

    public static void main(String[] args) {
        String[] words = {"the", "there", "their", "here", "then", "therefore", "them", "can", "canned"};
        TrieNode root = new TrieNode();
        Arrays.asList(words).forEach(w -> {
            System.out.println("Inserting:"+w);
            root.insert(w);
        });

        System.out.println(root.getValue());
//        root.iterateTrie(root.getChildren());
        root.searchPartial("the").forEach(System.out::println);
//        System.out.println(root.search("can"));
    }
}
