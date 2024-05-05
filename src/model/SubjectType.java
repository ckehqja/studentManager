package model;

public enum SubjectType {

    REQUIRED("필수과목"), OPTION("선택과목");

   private final String type;

    SubjectType(String type) {
        this.type = type;
    }
}
