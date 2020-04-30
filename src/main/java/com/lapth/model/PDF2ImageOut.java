package com.lapth.model;

public class PDF2ImageOut {
    /**
     * The image file name
     */
    private String fileName;

    /**
     * The number of image file generated from given PDF file
     */
    private Integer count;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
