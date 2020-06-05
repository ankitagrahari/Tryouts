package com.tree.binary;

public class BTree {
    public TreeNode prepareBTree(){
        TreeNode leaf1 = new TreeNode("1");
        TreeNode leaf2 = new TreeNode("3");
        TreeNode leaf3 = new TreeNode("6");
        TreeNode leaf4 = new TreeNode("9");

        TreeNode nodeRight = new TreeNode(leaf3, leaf4, "7");
        TreeNode nodeLeft = new TreeNode(leaf1, leaf2, "2");

        TreeNode root = new TreeNode(nodeLeft, nodeRight, "4");
        return root;
    }
}
