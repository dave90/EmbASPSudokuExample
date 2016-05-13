package it.unical.mat.frameworktesting;

import it.unical.mat.embasp.asp.Predicate;
import it.unical.mat.embasp.asp.Term;

@Predicate("cell")
public class Cell {
    @Term(0)
    private int row;
    @Term(1)
    private int column;
    @Term(2)
    private int value;

    public Cell(){
        row=column=value=0;
    }

    public Cell(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", column=" + column +
                ", value=" + value +
                '}';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
