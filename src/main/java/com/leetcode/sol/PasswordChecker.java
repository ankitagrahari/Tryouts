package com.leetcode.sol;

/**
 * Share
 * A password is considered strong if below conditions are all met:
 *
 * It has at least 6 characters and at most 20 characters.
 * It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
 * It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong, assuming other conditions are met).
 * Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change required to make s a strong password. If s is already strong, return 0.
 *
 * Insertion, deletion or replace of any one character are all considered as one change.
 */
public class PasswordChecker {

    private boolean lengthCheck(String password){
        if(password.length()>=6 && password.length()<=20){
            return true;
        }
        return false;
    }

    private String checkLeastLowerUpperCase(String password){
        if(password.length()>0) {
            StringBuilder missing = new StringBuilder("");

            for (char a : password.toCharArray()) {
                if (a >= 48 && a <= 57) {
                    if (missing.indexOf("DIGIT") != -1) {
                        continue;
                    } else {
                        missing.append("DIGIT");
                    }
                }

                if (a >= 65 && a <= 90) {
                    if (missing.indexOf("UPPER") != -1) {
                        continue;
                    } else {
                        missing.append("UPPER");
                    }
                }

                if (a >= 97 && a <= 122) {
                    if (missing.indexOf("LOWER") != -1) {
                        continue;
                    } else {
                        missing.append("LOWER");
                    }
                }
            }
            return missing.toString();
        }
        return null;
    }

    private int hasRepetitiveChar(String password){
        int count = 0;
        int index = 0;
        if(password.length()>2) {
            while (index < password.length()) {
                String sub = password.substring(index, Math.min(index + 3, password.length()));
                System.out.println(sub);
                if(sub.length()==3) {
                    if (sub.charAt(0) == sub.charAt(1) && sub.charAt(1) == sub.charAt(2)) {
                        count++;
                    }
                }
                index += 3;
            }
        }
        return count;
    }

    public int strongPasswordChecker(String password){
        int minimum = 0;
        int minChangeForLength = 0, minChangeForLower = 0, minChangeForUpper = 0, minChangeForDigit = 0, minChangeForRep = 0;
        boolean isDigit = false, isUpper = false, isLower = false;

        boolean isLengthOK = lengthCheck(password);
        String op = checkLeastLowerUpperCase(password);
        if(null!=op && op.length()>0) {
            isDigit = op.contains("DIGIT") ? true : false;
            isUpper = op.contains("UPPER") ? true : false;
            isLower = op.contains("LOWER") ? true : false;
        }

        int countRep = hasRepetitiveChar(password);
        boolean isRepetitive = (countRep==0) ? false : true;

        System.out.println(isLengthOK+"--"+isDigit+"--"+isUpper+"--"+isLower+"--"+isRepetitive);
        if(isLengthOK && isDigit && isUpper && isLower && !isRepetitive){
            System.out.println("Strong Password");
        } else {
            if(!isLengthOK){
                if(password.length()<6){
                    minChangeForLength += 6 - password.length();
                } else if (password.length()>20){
                    minChangeForLength += password.length() - 20;
                }
            }

            if(!isDigit){
                minChangeForDigit += 1;
            }

            if(!isLower){
                minChangeForLower += 1;
            }

            if(!isUpper){
                minChangeForUpper +=1;
            }

            if(isRepetitive){
                minChangeForRep += countRep;
            }

            System.out.println(minChangeForLength+"--"+ minChangeForDigit+"--"+minChangeForLower+"--"+minChangeForUpper+"--"+minChangeForRep);
            minimum = Math.max(minChangeForLength, minChangeForDigit);
            minimum = Math.max(minimum, minChangeForLower);
            minimum = Math.max(minimum, minChangeForUpper);
            minimum = Math.max(minimum, minChangeForRep);
            System.out.println(password +" requires "+ minimum +" changes to be considered as strong!");
        }
        return minimum;
    }

    public static void main(String[] args) {
        PasswordChecker obj = new PasswordChecker();
        obj.strongPasswordChecker("ABABABABABABABABABAB1");
    }
}
