package com.efimchick.ifmo.io.filetree.data;

import java.io.File;

public class FileTreeDTO {
    private final File file;
    private final long size;
    private final File parent;
    private final int nestingLevel;

    public FileTreeDTO(File file, long size, int nestingLevel) {
        this.file = file;
        this.size = size;
        this.parent = file.getParentFile();
        this.nestingLevel = nestingLevel;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return file.getName();
    }

    public long getSize() {
        return size;
    }

    public File getParent() {
        return parent;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }
}
