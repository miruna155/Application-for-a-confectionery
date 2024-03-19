package com.example.Domain.Repository;

import Domain.Entity;
import Domain.IEntityConverter;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collection;

public class TextFileRepository<T extends Entity> extends MemoryRepository<T> {
    private String fileName;

    private IEntityConverter<T> converter;

    public TextFileRepository(String fileName, IEntityConverter<T> converter) {
        this.fileName = fileName;
        this.converter = converter;

        try {
            loadFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFile() throws IOException {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (T entity : entities) {
                fw.write(converter.toString(entity));
                fw.write("\r\n");
            }
        }
    }

    private void loadFile() throws IOException {
        entities.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                entities.add(converter.fromString(line));
                line = br.readLine();
            }
        }
    }


    @Override
    public void add(T entity) throws DuplicateEntityException, RepositoryException {
        super.add(entity);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("Error saving object", e);
        }
    }

    @Override
    public void update(int id, T newEntity) throws EntityNotFoundException, RepositoryException {
        super.update(id,newEntity);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("Error saving object", e);
        }
    }

    @Override
    public void remove(int id) throws EntityNotFoundException, RepositoryException {
        super.remove(id);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("Error deleting object", e);
        }
    }

    @Override
    public Collection getAll() throws RepositoryException {
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("Error deleting object", e);
        }
        return super.getAll();
    }
}
