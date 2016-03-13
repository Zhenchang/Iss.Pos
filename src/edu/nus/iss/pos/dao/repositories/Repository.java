/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.dao.repositories;

import edu.nus.iss.pos.core.IEntity;
import edu.nus.iss.pos.core.dao.IFileFormatter;
import edu.nus.iss.pos.core.dao.IRepository;
import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.format.FileFormatterFactory;
import edu.nus.iss.pos.dao.format.FileType;
import java.io.*;
import java.util.Collection;
import java.util.HashSet;
/**
 *
 * @author zz
 * @param <T>
 */
public class Repository<T extends IEntity> implements IRepository<T>{
    
    private FileType fileType;
    private String fileName;
    private final IFileFormatter formatter;
    private final IUnitOfWork unitOfWork;
    
    public Repository(IUnitOfWork unitOfWork, FileType fileType, String filename) throws Exception{
        this.setFileType(fileType);
        this.setFileName(filename);
        this.formatter = FileFormatterFactory.getFormatter(fileType, unitOfWork);
        this.unitOfWork = unitOfWork;
    }
    
    private void setFileType(FileType fileType){
        this.fileType = fileType;
    }
    
    @Override
    public final void setFileName(String fileName) throws IOException{
        if(fileName.length() != 0){
            new File(fileName).createNewFile();
            this.fileName = fileName;
        }
    }

    @Override
    public void add(T entity) throws Exception {
        try(FileWriter fileWriter = new FileWriter(fileName, true)){
            fileWriter.append(this.formatter.format(entity));
        }
    }
    
    private int indexOfKey(String key) throws Exception{
        try(FileReader fileReader = new FileReader(this.fileName)){
            try(BufferedReader bufferedReader = new BufferedReader(fileReader)){
                int linePosition = -1;
                while(bufferedReader.ready()){
                    String lineKey = this.formatter.getKey(bufferedReader.readLine());
                    linePosition++;
                    if(key.equals(lineKey)) {
                        return linePosition;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public void delete(T entity) throws Exception {
        int linePosition = indexOfKey(entity.getKey());
        if(linePosition != -1){
            removeNthLine(this.fileName, linePosition);
        }
    }
    
   

    @Override
    public void update(String oldKey, T entity) throws Exception {
        int linePosition = indexOfKey(oldKey);
        if(linePosition != -1){
            removeNthLine(this.fileName, linePosition);
        }
        this.add(entity);
    }

    @Override
    public T getByKey(String key) throws Exception {
        
        try(FileReader fileReader = new FileReader(this.fileName)){
            try(BufferedReader bufferedReader = new BufferedReader(fileReader)){
                String lines = "";
                while(bufferedReader.ready()){
                    String line = bufferedReader.readLine();
                    String lineKey = this.formatter.getKey(line);
                    if(key.equals(lineKey)) {
                        lines += line + "\n";
                    }
                    else if(lines.length() != 0){
                        /*assumes that the trasctions are sequential*/
                        break;
                    }
                    
                }
                return (T) this.formatter.readEntity(lines);
            }
        }
    }

    @Override
    public Iterable<T> getAll() throws Exception {
        Collection<T> list = new HashSet();
        try(FileReader fileReader = new FileReader(this.fileName)){
            try(BufferedReader bufferedReader = new BufferedReader(fileReader)){
                String lines = "";
                T currentEntity = null;
                while(bufferedReader.ready()){
                    String line = bufferedReader.readLine();
                    if(currentEntity == null) {
                        // 1st line
                        currentEntity = (T) this.formatter.readEntity(line);
                        lines += line + "\n";
                    }else{
                        // 2nd line
                        String lineKey = this.formatter.getKey(line);
                        if(currentEntity.getKey().equals(lineKey)) {
                            lines += line + "\n";
                        } else if(lines.length() != 0){
                            /*assumes that the trasctions are sequential*/
                            list.add( (T) this.formatter.readEntity(lines));
                            lines = "";
                            currentEntity = (T) this.formatter.readEntity(line);
                        } else{
                             list.add(currentEntity);
                            currentEntity = (T) this.formatter.readEntity(line);
                        }
                    }
                }
                if(currentEntity != null){
                    list.add(currentEntity);
                }
            }
        }
        return list;
    }

    @Override
    public FileType getFileType() {
        return this.fileType;
    }
    
    private static void removeNthLine(String f, int toRemove) throws IOException {
        // Leave the n first lines unchanged.
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            // Leave the n first lines unchanged.
            for (int i = 0; i < toRemove; i++)
                raf.readLine();
            
            // Shift remaining lines upwards.
            long writePos = raf.getFilePointer();
            raf.readLine();
            long readPos = raf.getFilePointer();
            
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = raf.read(buf))) {
                raf.seek(writePos);
                raf.write(buf, 0, n);
                readPos += n;
                writePos += n;
                raf.seek(readPos);
            }
            
            raf.setLength(writePos);
        }
    }

    @Override
    public IUnitOfWork getUnitOfWork() {
        return this.unitOfWork;
    }
}
