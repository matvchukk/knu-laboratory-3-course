package lab_a;

import java.io.*;

public class WriteThread extends Thread{
    private String name;
    private String number;
    private final Locker lock;
    private final File file;
    private volatile Boolean changed = null;
    private UserCommands commands;

    public WriteThread(String file, Locker lock) {
        this.lock = lock;
        this.file = new File(file);
    }

    @Override
    public void run() {
        try {
            lock.setWriteLock();
            switch (commands) {
                case ADD: {
                    addToFile(name, number);
                    break;
                }
                case REMOVE: {
                    removeFromFile(name, number);
                    break;
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            lock.setWriteUnlock();
        }
    }

    public boolean changeFile(UserCommands commands, String name, String number) throws InterruptedException {
        changed = null;
        this.commands = commands;
        this.name = name;
        this.number = number;
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
        return changed;
    }

    private void removeFromFile(String name, String number) throws IOException {
        File tempFile = new File("~tempfile.txt");
        String toDelete = name + ":" + number;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currRecord;
        while ((currRecord  = reader.readLine()) != null) {
            if (toDelete.equals(currRecord.trim())) {
                changed = true;
            } else {
                writer.write(currRecord + System.getProperty("line.separator"));
            }
        }
        writer.close();
        reader.close();
        if (changed == null) {
            changed = false;
        }

        if (!file.delete()) {
            changed = false;
            return;
        }
        if (!tempFile.renameTo(file)) {
            changed = false;
        }
    }

    private void addToFile(String name, String number) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(name + ":" + number + System.getProperty("line.separator"));
            changed = true;
        }
    }

}
