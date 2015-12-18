package by.belous.contacts.service;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String writeFile(InputStream fileIs) throws IOException;

    InputStream readFile(String fileName) throws IOException;
}
