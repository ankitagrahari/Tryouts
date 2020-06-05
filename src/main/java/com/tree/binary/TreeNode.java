package com.tree.binary;

public class TreeNode {

    private com.tree.binary.TreeNode left;
    private com.tree.binary.TreeNode right;
    private String value;
    public TreeNode(String value){ this.value = value; }
    public TreeNode(com.tree.binary.TreeNode left, com.tree.binary.TreeNode right, String value){
        this.left = left;
        this.right = right;
        this.value = value;
    }
    public com.tree.binary.TreeNode getLeft() { return left; }
    public void setLeft(com.tree.binary.TreeNode left) { this.left = left; }
    public com.tree.binary.TreeNode getRight() { return right; }
    public void setRight(com.tree.binary.TreeNode right) { this.right = right; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
