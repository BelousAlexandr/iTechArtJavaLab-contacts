package by.belous.contacts.service;

import java.util.UUID;

public class FileNameGeneratorImpl implements FileNameGenerator {

    @Override
    public String generateUniqueFileName() {
        return UUID.randomUUID().toString();
    }
}
