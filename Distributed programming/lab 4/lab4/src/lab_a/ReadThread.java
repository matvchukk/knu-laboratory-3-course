package lab_a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ReadThread extends Thread{
    private final Locker lock;
    private final String fileName;
    //private final String task;

    private String records = null;

    private UserCommands commands;

    private String searchingKey;


    public ReadThread(String fileName, Locker lock) {
        this.fileName = fileName;
        this.lock = new Locker();
        //this.task = task;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            lock.setReadLock();
            String line;
            while ((line = reader.readLine()) != null) {
                int infoSeparator = line.indexOf(':');
                String name = line.substring(0, infoSeparator - 1);
                String phone = line.substring(infoSeparator + 1);
                System.out.println(line);
                if (recordFormatCheck(name, phone)) {
                    doTask(name, phone);
                    lock.setReadUnlock();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.setReadUnlock();
        }
    }

    public String recordSearch(UserCommands commands, String searchingKey){
        records = null;
        this.commands = commands;
        this.searchingKey = searchingKey;
        Thread thread = new Thread(this);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return records;
    }


    private boolean recordFormatCheck(String name, String phone) {
        if (!name.isEmpty() && !phone.isEmpty() && phone.matches("[0-9+]+")) {
            return true;
        }
        return false;
    }
    private void doTask(String name, String phone){
        switch (commands){
            case FIND_NAME_BY_NUMBER: {
                if(phone.equals(searchingKey)) {
                    records = name;
//                    return true;
                }
                break;
            }
            case FIND_NUMBER_BY_NAME: {
                if(name.equals(searchingKey)) {
                    records = phone;
//                    return true;
                }
                break;
            }
        }
//        return false;
    };
}
