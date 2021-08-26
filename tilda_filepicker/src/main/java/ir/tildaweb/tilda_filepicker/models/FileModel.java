package ir.tildaweb.tilda_filepicker.models;

import ir.tildaweb.tilda_filepicker.enums.FileMimeType;

public class FileModel {

    private String path;
    private String title;
    private FileMimeType fileMimeType;
    private boolean selected;
    private int selectionNumber;

    public int getSelectionNumber() {
        return selectionNumber;
    }

    public void setSelectionNumber(int selectionNumber) {
        this.selectionNumber = selectionNumber;
    }

    public FileMimeType getFileMimeType() {
        return fileMimeType;
    }

    public void setFileMimeType(FileMimeType fileMimeType) {
        this.fileMimeType = fileMimeType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
