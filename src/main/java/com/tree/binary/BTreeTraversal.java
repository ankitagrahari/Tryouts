package com.tree.binary;

public class BTreeTraversal {

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
        BTreeTraversal obj = new BTreeTraversal();
        BTree btree = new BTree();
        TreeNode root = btree.prepareBTree();

        System.out.println("Depth First Traversal - Inorder");
        obj.traverseBTreeInOrder(root);

        System.out.println();
        System.out.println("Depth First Traversal - Preorder");
        obj.traverseBTreePreOrder(root);

        System.out.println();
        System.out.println("Depth First Traversal - Postorder");
        obj.traverseBTreePostOrder(root);

    }
}
