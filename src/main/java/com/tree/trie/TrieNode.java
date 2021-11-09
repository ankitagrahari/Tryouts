package com.tree.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrieNode {

    private static final int CHAR_SIZE = 26;
    private boolean endOfWord;
    private char value;
    private List<TrieNode> children;

    public TrieNode(){
        //Create a new list with 26 null trienode object added
        children = new ArrayList<>(Collections.nCopies(CHAR_SIZE, null));
        endOfWord = false;
    }

//    public TrieNode(char c){
//        //Create a new list with 26 null trienode object added
//        children = new ArrayList<>(Collections.nCopies(CHAR_SIZE, null));
//        endOfWord = false;
//        value = c;
//    }

    public List<TrieNode> getChildren(){
        return children;
    }

    public char getValue(){
        return value;
    }

    public void setValue(char c){
        value = c;
    }

    public void insert(String key){
        //Get the Object Reference from where insert will be called
        TrieNode current = this;
        for(Character c: key.toCharArray()){
            if(null==current.children.get(c-'a')){
                TrieNode childNode = new TrieNode();
                childNode.setValue(c);
                current.children.set(c-'a', childNode);
            }
            //Next node
            current = current.children.get(c - 'a');
        }
        current.endOfWord = true;
    }

    public boolean search(String key){
        TrieNode current = this;

        for(Character c: key.toCharArray()){
            if (current.children.get(c - 'a') == null){
                return false;
            }
            current = current.children.get(c-'a');
        }
        if(current.endOfWord)
            return true;

        return false;
    }

    public List<String> searchPartial(String key){
        List<String> possibleMatches = new ArrayList<>();
        TrieNode current = this;
        StringBuilder found = new StringBuilder("");
        for(Character c: key.toCharArray()){
            if (current.children.get(c - 'a') != null) {
                current = current.children.get(c - 'a');
            }
        }
        found.append(key);
        for(TrieNode node : current.getChildren()){
            if (null!=node && node.endOfWord)
                possibleMatches.add(found.toString());
        }

        return possibleMatches;
    }

    //TODO: Only Picking last word...check the logic.
    public TrieNode iterateTrie(List<TrieNode> nodes){
        for(TrieNode node: nodes){
            if (null!=node){
                System.out.println(node.value);
                iterateTrie(node.children);
                return node;
            }
        }
        return null;
    }
}
