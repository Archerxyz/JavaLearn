package DataStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BTree_search {
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {

        // 非递归！
        if (root == null)
            return new ArrayList<Integer>();

        List<Integer> result = new ArrayList<Integer>();
        Stack<TreeNode> tempStack = new Stack<TreeNode>();

        while (true) {
            while (root != null) {
                tempStack.push(root);
                root = root.left;
            }

            if (tempStack.isEmpty()) {
                return result;
            }

            TreeNode temp = tempStack.pop();
            result.add(temp.val);

            root = temp.right;
        }
    }

    // 递归的

    // 递归的
    public void recursion(List<Integer> result, TreeNode root) {
        if (root == null)
            return;
        recursion(result, root.left);
        result.add(root.val);
        recursion(result, root.right);
        Math.abs(1);
    }


}