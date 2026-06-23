package ais.model;

public interface CursorEditableModel {
    void moveRowUp();

    void moveRowDown();

    void incrementValue();

    void decrementValue();

    void enter();

    boolean onClr();

    void reset();
}
