package com.efimchick.ifmo.io.filetree.data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileTreeWalker {

    public List<File> walk(Path path) {
        List<File> files = new ArrayList<>(List.of(path.toFile()));

        if (Files.isDirectory(path)) {
            files.addAll(getSubTree(path.toFile()));

            for (int i = 1; i < files.size(); i++) {
                files.addAll(i + 1, getSubTree(files.get(i)));
            }
        }

        return files;
    }

    private List<File> getSubTree(File file) {
        List<File> subTree;

        if (file.isFile()) {
            subTree = List.of();
        } else {
            subTree = Stream.of(Objects.requireNonNull(file.listFiles()))
                    .sorted(Comparator.comparing(File::isFile)
                            .thenComparing((f1, f2) -> Collator.getInstance(Locale.US)
                                    .compare(f1.getName().toLowerCase(Locale.US),
                                            f2.getName().toLowerCase(Locale.US))))
                    .collect(Collectors.toList());
        }

        return subTree;
    }
}
