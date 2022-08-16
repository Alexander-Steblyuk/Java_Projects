package com.efimchick.ifmo.io.filetree.output;

import com.efimchick.ifmo.io.filetree.data.FileTreeDTO;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class FileTreeView {
    private static final String OUTPUT_FORMAT = "%s %d bytes\n";
    private static final String END_BRANCH_SYMBOL = "└─ ";
    private static final String MIDDLE_BRANCH_SYMBOL = "├─ ";
    private static final String TRUNK_OF_TREE_SYMBOL = "│  ";
    private static final String SPACE_SYMBOL = "   ";

    private final Deque<String> trunk;

    public FileTreeView() {
        trunk = new LinkedList<>();
    }

    public String writeFileStats(List<FileTreeDTO> fileTree) {
        StringBuilder stringBuilder = new StringBuilder();
        int nestingLevel;

        for (int i = 0; i < fileTree.size(); i++) {
            nestingLevel = fileTree.get(i).getNestingLevel();

            if (nestingLevel > 0) {
                stringBuilder.append(getTrunk());

                if (i + 1 < fileTree.size()
                        && checkNeighbour(fileTree.get(i), fileTree.subList(i + 1, fileTree.size()))) {
                    stringBuilder.append(MIDDLE_BRANCH_SYMBOL);
                } else {
                    stringBuilder.append(END_BRANCH_SYMBOL);
                }

                configTrunk(fileTree.get(i), fileTree.subList(i + 1, fileTree.size()));
            }

            stringBuilder.append(String.format(OUTPUT_FORMAT, fileTree.get(i).getName(), fileTree.get(i).getSize()));
        }

        return stringBuilder.toString();
    }

    private void configTrunk(FileTreeDTO currFile, List<FileTreeDTO> fileTree) {

        if (currFile.getFile().isDirectory()) {
            if (checkNeighbour(currFile, fileTree)) {
                trunk.offer(TRUNK_OF_TREE_SYMBOL);
            } else if (checkChild(currFile, fileTree.get(0))) {
                trunk.offer(SPACE_SYMBOL);
            } else {
                if (!fileTree.isEmpty()) {
                    removeNesting(currFile, fileTree.get(0));
                }
            }
        } else {
            if (!checkNeighbour(currFile, fileTree) && !fileTree.isEmpty()) {
                removeNesting(currFile, fileTree.get(0));
            }
        }

    }

    private boolean checkNeighbour(FileTreeDTO currFile, List<FileTreeDTO> fileTree) {
        boolean neighbourExist = false;

        for (FileTreeDTO file : fileTree) {
            if (currFile.getParent().equals(file.getParent())) {
                neighbourExist = true;
                break;
            }
        }

        return neighbourExist;
    }

    private boolean checkChild(FileTreeDTO file, FileTreeDTO nextFile) {
        return file.getFile().equals(nextFile.getParent());
    }

    private String getTrunk() {
        StringBuilder stringBuilder = new StringBuilder();

        trunk.forEach(stringBuilder::append);

        return stringBuilder.toString();
    }

    private void removeNesting(FileTreeDTO file, FileTreeDTO nextFile) {
        int count = file.getNestingLevel() - nextFile.getNestingLevel();

        while (!trunk.isEmpty() && count > 0) {
            trunk.removeLast();
            count--;
        }
    }
}
