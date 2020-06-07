package com.tree.binary;

public class TreeNode {

    private com.tree.binary.TreeNode left;
    private com.tree.binary.TreeNode right;
    private String value;
    private int intValue;
    public TreeNode(String value){ this.value = value; }
    public TreeNode(int value){ this.intValue = value; }
    public TreeNode(TreeNode left, TreeNode right, String value){
        this.left = left;
        this.right = right;
        this.value = value;
    }
    public TreeNode(TreeNode left, TreeNode right, int value){
        this.left = left;
        this.right = right;
        this.intValue = value;
    }
    public TreeNode getLeft() { return left; }
    public void setLeft(TreeNode left) { this.left = left; }
    public TreeNode getRight() { return right; }
    public void setRight(TreeNode right) { this.right = right; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public int getIntValue() { return intValue; }
    public void setIntValue(int value) { this.intValue = value; }
}
