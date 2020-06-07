package com.tree.binary;

public class InvertBinaryTree {

    public TreeNode invertBTree(TreeNode node){

        if(null==node){
            return null;
        }
        if(null==node.getLeft() && null==node.getRight()){
            return node;
        }
        TreeNode temp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(temp);

        invertBTree(node.getLeft());
        invertBTree(node.getRight());
        return node;
    }

    public static void main(String[] args) {
        InvertBinaryTree obj = new InvertBinaryTree();

        TreeNode root = BTree.prepareBTree();
        System.out.println("Original BTree");
        BTreeTraversal.traverseBTreePreOrder(root);

        System.out.println();
        System.out.println("After inversion");
        TreeNode invertBTreeRoot = obj.invertBTree(root);

        System.out.println();
        System.out.println("PreOrder Traversal");
        BTreeTraversal.traverseBTreePreOrder(invertBTreeRoot);

        System.out.println();
        System.out.println("InOrder Traversal");
        BTreeTraversal.traverseBTreeInOrder(invertBTreeRoot);

        System.out.println();
        System.out.println("PostOrder Traversal");
        BTreeTraversal.traverseBTreePostOrder(invertBTreeRoot);
    }
}
