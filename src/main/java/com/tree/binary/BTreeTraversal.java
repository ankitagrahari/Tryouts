package com.tree.binary;

public class BTreeTraversal {

    public static void traverseBTreePreOrder(TreeNode root){
        if(null==root){
            return;
        }
        if(null!=root.getValue()) {
            System.out.print(root.getValue() + "-->");
        } else {
            System.out.print(root.getIntValue() + "-->");
        }
        traverseBTreePreOrder(root.getLeft());
        traverseBTreePreOrder(root.getRight());
    }

    public static void traverseBTreeInOrder(TreeNode root){
        if(null==root){
            return;
        }
        traverseBTreeInOrder(root.getLeft());
        if(null!=root.getValue()) {
            System.out.print(root.getValue() + "-->");
        } else {
            System.out.print(root.getIntValue() + "-->");
        }
        traverseBTreeInOrder(root.getRight());
    }

    public static void traverseBTreePostOrder(TreeNode root){
        if(null==root){
            return;
        }
        traverseBTreePostOrder(root.getLeft());
        traverseBTreePostOrder(root.getRight());
        if(null!=root.getValue()) {
            System.out.print(root.getValue() + "-->");
        } else {
            System.out.print(root.getIntValue() + "-->");
        }
    }

    public static void main(String[] args) {

        TreeNode root = BTree.prepareBTree();

        System.out.println("Depth First Traversal - Inorder");
        BTreeTraversal.traverseBTreeInOrder(root);

        System.out.println();
        System.out.println("Depth First Traversal - Preorder");
        BTreeTraversal.traverseBTreePreOrder(root);

        System.out.println();
        System.out.println("Depth First Traversal - Postorder");
        BTreeTraversal.traverseBTreePostOrder(root);

    }
}
