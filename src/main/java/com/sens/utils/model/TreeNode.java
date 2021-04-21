package com.sens.utils.model;

/**
 * 트리 자료구조
 * @param <T>
 */
public class TreeNode <T> {
    private TreeNode<T> left;
    private TreeNode<T> right;
    private T data;

    public TreeNode(T data) {
        this.data = data;
    }

    //자신과 왼쪽 자식 노드(sub)와 연결해주는 method
    public void makeLeftSubTree(TreeNode<T> sub) {
        if (this.left != null) this.left = null;
        this.left = sub;
    }

    //자신과 오른쪽 자식 노드(sub)와 연결해주는 method
    public void makeRightSubTree(TreeNode<T> sub) {
        if (this.right != null) this.right = null;
        this.right = sub;
    }

    //자신의 data를 반환하는 함수
    public T getData() {
        return this.data;
    }

    //자신의 왼쪽 자식노드를 반환하는 함수
    public TreeNode<T> getLeftSubTree() {
        return this.left;
    }

    //자신의 오른쪽 자식노드를 반환하는 함수
    public TreeNode<T> getRightSubTree() {
        return this.right;
    }


}
