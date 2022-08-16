package com.efimchick.ifmo.io.filetree;

import com.efimchick.ifmo.io.filetree.data.FileTreeDTO;
import com.efimchick.ifmo.io.filetree.data.FileTreeManager;
import com.efimchick.ifmo.io.filetree.data.FileTreeWalker;
import com.efimchick.ifmo.io.filetree.output.FileTreeView;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileTreeController {
    private final Path pathToFile;
    private final FileTreeView fileTreeView;
    private final FileTreeWalker fileTreeWalker;
    private final FileTreeManager fileTreeManager;

    public FileTreeController(Path path) {
        pathToFile = path;
        fileTreeView = new FileTreeView();
        fileTreeWalker = new FileTreeWalker();
        fileTreeManager = new FileTreeManager();
    }

    public String getFileTree() {
        String fileTree;

        List<FileTreeDTO> list = wrapToDTO(fileTreeWalker.walk(pathToFile));
        fileTree = fileTreeView.writeFileStats(list);


        return fileTree;
    }

    private List<FileTreeDTO> wrapToDTO(List<File> files) {
        List<FileTreeDTO> fileDTOs = new ArrayList<>();
        File root = files.get(0);
        int nestingLevel;
        long sizeOfRoot = 0;

        if (root.isFile()) {
            sizeOfRoot = root.length();
        } else {

            for (int i = 1; i < files.size(); i++) {
                File currFile = files.get(i);

                if (!root.equals(currFile.getParentFile())) {
                    nestingLevel = computeNestingLevel(root, currFile);
                } else {
                    nestingLevel = 1;
                }

                if (currFile.isFile()) {
                    fileDTOs.add(new FileTreeDTO(currFile, currFile.length(), nestingLevel));

                    sizeOfRoot += currFile.length();
                } else {
                    long dirSize = fileTreeManager.computeSizeOfDir(currFile, files);

                    fileDTOs.add(new FileTreeDTO(currFile, dirSize, nestingLevel));
                }
            }
        }

        fileDTOs.add(0, new FileTreeDTO(root, sizeOfRoot, 0));

        return fileDTOs;
    }

    private int computeNestingLevel(File root, File directory) {
        int nestingLevel = 1;
        File currRoot = directory.getParentFile();

        while (!root.equals(currRoot)) {
            currRoot = currRoot.getParentFile();
            nestingLevel++;
        }

        return nestingLevel;
    }
}
