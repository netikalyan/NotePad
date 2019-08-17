package com.netikalyan.notepad;

public class Note {
    private String mContent;

    Note() {
        mContent = new String();
    }

    public void setText(String text) {
        mContent = text;
    }

    public String getText() {
        return mContent;
    }
}
