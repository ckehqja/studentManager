package model;

public enum Grade {
    A('A'), B('B'), C('C'), D('D'), F('F'), N('N');
    char ch;

    Grade(char ch) {
        this.ch = ch;
    }
    public char getGrade() {
        return ch;
    }
}
