package com.tree.binary;

public class ConstructBtreeFromInPreOrder {

    //This will be used for get the root index in preOrder
    private static int preRootIndex = 0;

    public TreeNode constructBTree(String side, int inStart, int inEnd, int[] in, int[] pre){

        //1st Exit Criteria: Return null, when the indexes are crossing each other, means no value can be found in the array
        if(inStart > inEnd){
            System.out.println("Returning null for "+ preRootIndex + " inStart:"+ inStart+ " inEnd:"+ inEnd);
            return null;
        }

        int root = pre[preRootIndex++];
        TreeNode rootNode = new TreeNode(root);
//        System.out.println("Prepared " + side + " Node:" + root + " inStart:" + inStart + " inEnd:" + inEnd);

        //Second exit criteria: When the node is a leaf node, means no left or right node exists, then return the node
        if (inStart == inEnd) {
            return rootNode;
        }

        //Find the rootIndex in Inorder array
        int rootIndexInInorder = getNodeIndexInIn(root, in, inStart, inEnd);

        rootNode.setLeft(constructBTree("left", inStart, rootIndexInInorder - 1, in, pre));
        rootNode.setRight(constructBTree("right", rootIndexInInorder + 1, inEnd, in, pre));

        return rootNode;
    }

    private int getNodeIndexInIn(int node, int[] inOrder, int start, int end){
        int index = 0;
        for(int i=start; i<=end; i++){
            if(node==inOrder[i]){
                index = i;
                return index;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] preorder = {4,2,1,3,7,6,9};
        int[] inorder = {1,2,3,4,6,7,9};
        ConstructBtreeFromInPreOrder obj = new ConstructBtreeFromInPreOrder();
        TreeNode root = obj.constructBTree("main",0, inorder.length-1, inorder, preorder);
        BTreeTraversal.traverseBTreePostOrder(root);
        //1-->3-->2-->6-->9-->7-->4
    }
}
