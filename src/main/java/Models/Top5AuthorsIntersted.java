/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import javafx.beans.property.*;

/**
 *
 * @author PC
 */
public class Top5AuthorsIntersted {

    private final IntegerProperty id;
    private final IntegerProperty total;

    public Top5AuthorsIntersted() {
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
