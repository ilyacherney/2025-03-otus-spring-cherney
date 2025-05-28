package ru.otus.hw.service;

import ru.otus.hw.config.TestFileNameProvider;

import java.io.InputStream;

public class ResourcesTestDataProvider implements TestDataProvider {

    private final TestFileNameProvider fileNameProvider;

    public ResourcesTestDataProvider(TestFileNameProvider fileNameProvider) {
        this.fileNameProvider = fileNameProvider;
    }

    @Override
    public InputStream provideTestData() {
        String fileName = fileNameProvider.getTestFileName();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

}
