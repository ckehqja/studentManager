package model;

public enum StudentStatus {
    GREEN("green"), RED("red"), YELLOW("yellow");
    private String value;

    StudentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
