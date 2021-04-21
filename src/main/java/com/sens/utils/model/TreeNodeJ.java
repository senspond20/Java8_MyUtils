package com.sens.utils.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 트리 자료구조
 * @param <T>
 */
public class TreeNodeJ<T> implements Iterable<TreeNodeJ<T>>{
    private T data;
    private TreeNodeJ<T> parent;
    private List<TreeNodeJ<T>> children;
 
    public TreeNodeJ(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNodeJ<T>>();
    }
 
    public TreeNodeJ<T> addChild(T child) {
        TreeNodeJ<T> childNode = new TreeNodeJ<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    public T getData() {
        return this.data;
    }
    public TreeNodeJ<T> getParent() {
        return this.parent;
    }

    @Override
    public Iterator<TreeNodeJ<T>> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
