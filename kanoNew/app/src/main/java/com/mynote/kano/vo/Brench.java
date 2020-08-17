package com.mynote.kano.vo;

public class Brench {
    private String name;
    private String author;
    private String oid;

    public void  Branch(String name, String author, String oid) {

        this.name = name;
        this.author = author;
        this.oid = oid;
    }


    public void Branch() {}


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getOid() {
        return oid;
    }
    public void setOid(String oid) {
        this.oid = oid;
    }
    @Override
    public String toString() {
        return "Branch [name=" + name + ", author=" + author + ", oid=" + oid + "]";
    }


}
