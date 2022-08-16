package com.efimchick.ifmo.io.filetree.data;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FileTreeManager {

    public long computeSizeOfDir(File directory, Collection<File> files) {

        return getChildren(directory, files).stream()
                .filter(File::isFile)
                .mapToLong(File::length)
                .sum();
    }

    private List<File> getChildren(File directory, Collection<File> files) {
        List<File> nestedFiles =  files.stream()
                .filter(file -> file.getParentFile().equals(directory))
                .collect(Collectors.toList());

        nestedFiles.addAll(nestedFiles.stream()
                .flatMap(file -> getChildren(file, files).stream())
                .collect(Collectors.toList()));

        return nestedFiles;
    }

}
