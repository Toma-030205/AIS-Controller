package ais.controller;

public interface ScreenInputHandler {

    default boolean onSub() {
        return false;
    }

    default boolean onClr() {
        return false;
    }

    default boolean onUp() {
        return false;
    }

    default boolean onDown() {
        return false;
    }

    default boolean onLeft() {
        return false;
    }

    default boolean onRight() {
        return false;
    }

    default boolean onDisp() {
        return false;
    }

    default boolean onEnter() {
        return false;
    }
}
