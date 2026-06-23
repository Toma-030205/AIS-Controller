package ais.model;


public class DestinationInputModel {

    private static final int MAX_LENGTH = 20;

    private final StringBuilder buffer = new StringBuilder();

    public String getText() {
        return buffer.toString();
    }

    public int length() {
        return buffer.length();
    }

    public boolean canAppend() {
        return buffer.length() < MAX_LENGTH;
    }

    public void append(String ch) {
        if (canAppend()) {
            buffer.append(ch);
        }
    }

    public void backspace() {
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
    }

    public void clear() {
        buffer.setLength(0);
    }
}
