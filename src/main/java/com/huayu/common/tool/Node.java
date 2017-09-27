package com.huayu.common.tool;

import java.util.ArrayList;
import java.util.List;

public class Node<T>{

    private String key;

    private T value;

    private List<Node<T>> children;

    public Node(String key, T value) {
        this.key = key;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
            this.children = children;
        }

    public String authoritiesRegexp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append(this.value.toString());
        for (Node entity : children) {
            stringBuilder.append("|").append(entity.value.toString());
        }
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    public String authorityRegexp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append(value.toString());
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}