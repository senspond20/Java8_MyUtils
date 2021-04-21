package com.sens.utils.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Hierarchical Data Structur
 * @param <T>
 */
public class MultiTreeNode<T> {

    private int level;  // 노드의 깊이 (ROOT 0)
    private int degree; // 한 노드가 가지는 서브 트리의 수(자식 노드의 수)
    private MultiTreeNode<T> children;
    private T data;

    /**
     * 생성자
     * @param data
     */
    public MultiTreeNode(T data) {
        this.level = 0;
        this.degree = 1;
        this.children = new MultiTreeNode<T>(data);
        this.data = data;
    }
    
    public void addChildren(int level, T data){
        this.level = level;
        this.degree++;
    }

    public int getLevel() {
        return level;
    }
    public T getDate() {
        return this.data;
    }
    public int getDegree() {
        return degree;
    }

}
