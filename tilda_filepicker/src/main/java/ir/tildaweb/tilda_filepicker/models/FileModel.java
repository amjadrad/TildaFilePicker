package ir.tildaweb.tilda_filepicker.models;

import android.content.Intent;

import ir.tildaweb.tilda_filepicker.enums.FileMimeType;

public class FileModel {

    private Integer id;
    private Intent extraData;
    private String path;
    private String title;
    private FileMimeType fileMimeType;
    private boolean selected;
    private int selectionNumber;

    public Intent getExtraData() {
        return extraData;
    }

    public void setExtraData(Intent extraData) {
        this.extraData = extraData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
