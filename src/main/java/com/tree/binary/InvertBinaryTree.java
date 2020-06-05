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

    public void traverseBTreePreOrder(TreeNode root){
        if(null==root){
            return;
        }
        System.out.print(root.getValue()+"-->");
        traverseBTreePreOrder(root.getLeft());
        traverseBTreePreOrder(root.getRight());
    }

    public void traverseBTreeInOrder(TreeNode root){
        if(null==root){
            return;
        }
        traverseBTreeInOrder(root.getLeft());
        System.out.print(root.getValue()+"-->");
        traverseBTreeInOrder(root.getRight());
    }

    public void traverseBTreePostOrder(TreeNode root){
        if(null==root){
            return;
        }
        traverseBTreePostOrder(root.getLeft());
        traverseBTreePostOrder(root.getRight());
        System.out.print(root.getValue()+"-->");
    }

    public static void main(String[] args) {
        InvertBinaryTree obj = new InvertBinaryTree();
        TreeNode root = obj.prepareBTree();
        System.out.println("Original BTree");
        obj.traverseBTreePreOrder(root);
        System.out.println();
        System.out.println("After inversion");
        TreeNode invertBTreeRoot = obj.invertBTree(root);
        System.out.println();
        System.out.println("PreOrder Traversal");
        obj.traverseBTreePreOrder(invertBTreeRoot);
        System.out.println();
        System.out.println("InOrder Traversal");
        obj.traverseBTreeInOrder(invertBTreeRoot);
        System.out.println();
        System.out.println("PostOrder Traversal");
        obj.traverseBTreePostOrder(invertBTreeRoot);
    }
}

class TreeNode{
    private TreeNode left;
    private TreeNode right;
    private String value;
    public TreeNode(String value){ this.value = value; }
    public TreeNode(TreeNode left, TreeNode right, String value){
        this.left = left;
        this.right = right;
        this.value = value;
    }
    public TreeNode getLeft() { return left; }
    public void setLeft(TreeNode left) { this.left = left; }
    public TreeNode getRight() { return right; }
    public void setRight(TreeNode right) { this.right = right; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
