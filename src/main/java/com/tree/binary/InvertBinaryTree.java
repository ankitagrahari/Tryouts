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
        BTreeTraversal trvObj = new BTreeTraversal();
        BTree bTree = new BTree();

        TreeNode root = bTree.prepareBTree();
        System.out.println("Original BTree");
        trvObj.traverseBTreePreOrder(root);

        System.out.println();
        System.out.println("After inversion");
        TreeNode invertBTreeRoot = obj.invertBTree(root);

        System.out.println();
        System.out.println("PreOrder Traversal");
        trvObj.traverseBTreePreOrder(invertBTreeRoot);

        System.out.println();
        System.out.println("InOrder Traversal");
        trvObj.traverseBTreeInOrder(invertBTreeRoot);

        System.out.println();
        System.out.println("PostOrder Traversal");
        trvObj.traverseBTreePostOrder(invertBTreeRoot);
    }
}
