package com.huayu.common.tool;

import java.util.*;

public class NodeMap extends HashMap<String,Node>{

    private Set<String> keySet = new HashSet<>();

    private Node root;

    public Node put(Node node, String key,Object value) {
        Node child = new Node(key, value);
        if(isChild(node.getKey(),key)){
            node.getChildren().add(child);
        }else{
            Node parentNode = getParentNode(node, key);
            if (null == parentNode){
                return null;
            }
            parentNode.getChildren().add(child);
        }
        return child;
    }

    public Node put(String key, Object value) {
        if (null == this.root){
            this.keySet.add(key);
            this.root = new Node(key,value);
            return this.put(key,root);
        }else{
            if (this.keySet.contains(key)){
                return null;
            }
            Node node = this.put(root, key, value);
            if (null != node){
                this.keySet.add(key);
            }
            return this.put(key,node);
        }
    }

    public Node getParentNode(Node node, String key){
        if (isChild(node.getKey(),key)){
            return node;
        }else{
            List<Node> children = node.getChildren();
            for (Node entity : children) {
                Node parentNode = getParentNode(entity, key);
                if (null != parentNode){
                    return parentNode;
                }
            }
            return null;
        }
    }

    /**
     * 判断后一个key 是不是前一个key的 直接子节点
     * eg:
     * @param srcKey 2
     * @param targetKey 210
     * @return
     */
    private boolean isChild(String srcKey, String targetKey){
        if (srcKey.length()<targetKey.length()&&targetKey.length()%2!=0){
            String tail = targetKey.substring(srcKey.length(), targetKey.length());
            String head = targetKey.substring(0, srcKey.length());
            if (tail.length()==2&&head.equals(srcKey)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public Node getRoot() {
        return root;
    }

    public Node getNode(String key){
        if (null == this.get(key)){
            if (hasAuthority(key)){
                return new Node(key,"^"+key+"$");
            }
        }
        return this.get(key);
    }


    public boolean hasAuthority(Node node,String key){
        if (node.getKey().equals(key)){
            return true;
        }else{
            List<Node> children = node.getChildren();
            for (Node entity : children) {
                if (hasAuthority(entity,key)){
                    return true;
                }
            }
            return isChild(node.getKey(),key);
        }
    }

    public boolean hasAuthority(String key){
        for (Iterator<String> iterator=keySet.iterator();iterator.hasNext();){
            if (isChild(iterator.next(),key)){
                return true;
            }
        }
        return false;
    }
}