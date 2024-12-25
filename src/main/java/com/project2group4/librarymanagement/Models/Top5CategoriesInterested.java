package com.project2group4.librarymanagement.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Top5CategoriesInterested {

    private final IntegerProperty id;
    private final IntegerProperty total;

    public Top5CategoriesInterested() {
        id = new SimpleIntegerProperty(this, "id");
        total = new SimpleIntegerProperty(this, "total");
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty totalProperty() {
        return total;
    }

    public int getId() {
        return id.get();
    }

    public int getTotal() {
        return total.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setTotal(int total) {
        this.total.set(total);
    }
}
