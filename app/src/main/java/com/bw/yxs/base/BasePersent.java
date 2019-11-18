package com.bw.yxs.base;

public class BasePersent<V> {
    public V v;

    public void attach(V v){
        this.v=v;
    }

    public void unattach(){
        if (v!=null){
            v=null;
        }
    }
}
