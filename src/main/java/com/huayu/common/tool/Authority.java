package com.huayu.common.tool;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Iterator;

public class Authority extends HashMap<String, Authority>{

    private HashMap<String,Authority> authorities;

    private String key;

    private String value;


    public Authority() {
        this.authorities = new HashMap<>();
    }

    public Authority(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public HashMap<String,Authority> getAuthorities() {
        return authorities;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public Authority put(Authority node, String key, String value) {
        Authority child = new Authority(key, value);
        if(isChild(node.getKey(),key)){
            Authority authority = node.get(node.getKey());
            authority.put(key,child);
        }else{
            Authority parentNode = getParentAuthority(node, key);
            if (null == parentNode){
                return null;
            }
            parentNode.put(key,child);
        }
        return child;
    }

    public void put(String key, String value) {
        Authority authority;
        if (CollectionUtils.isEmpty(this)){
            this.key = key;
            this.value = value;
            authority = new Authority(key, value);
            this.put(key,authority);
            this.authorities.put(key,authority);
        }else{
            if (!this.authorities.containsKey(key)){
                authority = this.put(this, key, value);
                if (null!=authority){
                    this.authorities.put(key,authority);
                }
            }
        }
    }

    public Authority getParentAuthority(Authority node, String key){
        if (isChild(node.getKey(),key)){
            return node;
        }else{
            for (Iterator<Authority> it = node.values().iterator(); it.hasNext(); ) {
                Authority authority = it.next();
                Authority parentNode = getParentAuthority(authority, key);
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

    /**
     *
     * 是否是包含或者等于
     */
    public static boolean isPosterity(String srcKey, String targetKey, boolean ignoreMark){
        if (ignoreMark){
            srcKey = srcKey.replace("!","");
        }
        if (srcKey.equals(targetKey)){
            return true;
        }else{
            if (srcKey.length()<targetKey.length()&&targetKey.length()%2!=0){
                String tail = targetKey.substring(srcKey.length(), targetKey.length());
                String head = targetKey.substring(0, srcKey.length());
                if (tail.length()%2==0&&head.equals(srcKey)){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }
    public static boolean isPosterity(String srcKey, String targetKey){
        return isPosterity(srcKey,targetKey,false);
    }

    public static Authority getAuthority(Authority authority, String key){
        Authority target = authority.get(key);
        if (null == target){
            for (Iterator<Authority> it = authority.values().iterator(); it.hasNext(); ) {
                Authority entity = it.next();
                target = getAuthority(entity,key);
                if (null != target){
                    return target;
                }else if (isPosterity(entity.getKey(),key)){
                    return buildLastAuthority(entity.getKey(),entity.getValue(),key);
                }
            }
            return null;
        }else{
            return target;
        }
    }

    private static Authority buildLastAuthority(String srcKey, String srcValue, String key){
        if (srcValue.startsWith("^") && !srcValue.endsWith("$")){
            return new Authority(key,'^'+key);
        }else{
            return new Authority(srcKey,srcValue);
        }
    }

    public boolean hasAuthority(String key){
      return hasAuthority(key,false);
    }

    public boolean hasAuthority(String key,boolean ignoreMark){
        for (Iterator<Authority> iterator = this.authorities.values().iterator(); iterator.hasNext();){
            Authority authority = iterator.next();
            if (isPosterity(authority.key,key,ignoreMark)){
                return true;
            }
        }
        return false;
    }


    public String authoritiesRegexp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append(recursiveAuthor(this));
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    private String recursiveAuthor(Authority authority){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(authority.value.toString());
        for (Iterator<Authority> it = authority.values().iterator(); it.hasNext(); ) {
            Authority entity = it.next();
            String t = recursiveAuthor(entity);
            if (t.length()>0){
                stringBuilder.append("|").append(t);
            }
        }
        return stringBuilder.toString();
    }

    public String authorityRegexp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        String value = this.value;
        if (!value.endsWith("$")){
            value+="$";
        }
        stringBuilder.append(value);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }


    @Override
    public String toString() {
        return "Authority{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", children=" + values() +
                '}';
    }
}