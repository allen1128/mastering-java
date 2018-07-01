package org.spring.jmx;

import javafx.util.Pair;

import java.util.List;

public interface IFileService {
    List<Pair> getFiles();
    void addFile(String fileName, String filePath);
}
