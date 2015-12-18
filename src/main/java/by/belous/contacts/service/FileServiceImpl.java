package by.belous.contacts.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileServiceImpl implements FileService {

    private String basePath;
    private FileNameGenerator nameGenerator = new FileNameGeneratorImpl();

    public FileServiceImpl(String basePath) throws IOException {
        this.basePath = basePath;
        FileUtils.forceMkdir(new File(basePath));
    }

    @Override
    public String writeFile(InputStream fileIs) throws IOException {
        String fileName = nameGenerator.generateUniqueFileName();
        String filePath = buildFileFullPath(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        IOUtils.copy(fileIs, fileOutputStream);
        IOUtils.closeQuietly(fileOutputStream);
        IOUtils.closeQuietly(fileIs);
        return fileName;
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        String filePath = buildFileFullPath(fileName);
        return new FileInputStream(filePath);
    }

    private String buildFileFullPath(String fileName) {
        return basePath + File.separator + fileName;
    }
}
