package com.janek.app;

import java.io.*;

class FileSerializer<T>{

    private String fileName;
    
    public FileSerializer(String fileName){
        this.fileName = fileName;
    }

    public void write(T data){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T read(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            T readObject = (T)ois.readObject();
            return readObject;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}