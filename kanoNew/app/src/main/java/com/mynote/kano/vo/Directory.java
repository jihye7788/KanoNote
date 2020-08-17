package com.mynote.kano.vo;

public class Directory {

    private String oid;
    private String type;
    private String name;


    public Directory(String oid, String type, String name) {
        super();
        this.oid = oid;
        this.type = type;
        this.name = name;
    }


    public Directory() {
    }


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Directory [oid=" + oid + ", type=" + type + ", name=" + name + "]";
    }


}
