package com.efimchick.ifmo.io.filetree;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileTreeImpl implements FileTree {

    @Override
    public Optional<String> tree(Path path) {
        Optional<String> result;

        if (path == null || Files.notExists(path)) {
            result = Optional.empty();
        } else {
            result = Optional.of(new FileTreeController(path).getFileTree());
        }

        return result;
    }

}
