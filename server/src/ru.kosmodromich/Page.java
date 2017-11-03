package ru.kosmodromich;

import java.io.Serializable;

public class Page implements Serializable{
    private long id;
    private long size;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
