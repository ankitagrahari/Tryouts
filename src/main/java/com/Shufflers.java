package com;

import org.springframework.scripting.bsh.BshScriptUtils;

import java.util.ArrayList;
import java.util.List;

public class Shufflers {

    public static char diff(String s, String t){
        if(s.length()+1 == t.length()){
            int[] chars = new int[26];
            for(char c : t.toCharArray()){
                chars[c-97] = 1;
            }
            for(char c : s.toCharArray()){
                chars[c-97] = chars[c-97]+1;
            }

            for(int i=0; i<chars.length; i++){
                if(chars[i]==1)
                    return (char)(i+97);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        String s = "abcd";
        String t = "abecd";
        System.out.println(Shufflers.diff(s, t));


    }
}


