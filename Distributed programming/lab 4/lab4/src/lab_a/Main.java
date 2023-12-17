package lab_a;

import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        final String fileName = "lab4/src/lab_a/info.txt";
        Locker lock = new Locker();
        ReadThread reader = new ReadThread(fileName, lock);
        WriteThread writer = new WriteThread(fileName, lock);

        String number = "11013";
        String name = "Name29";

        try {
            SecureRandom random = new SecureRandom();
//            System.out.println("Status of adding operation: " +
//                    writer.changeFile(UserCommands.ADD, "Name" + random.nextInt(100),
//                            "1" + (random.nextInt(100) + 1000)));
            System.out.println("Status of adding operation: " +
                    writer.changeFile(UserCommands.ADD, name, number));
            System.out.println("Name with number 26352673: " +
                    reader.recordSearch(UserCommands.FIND_NAME_BY_NUMBER, "26352673"));
            System.out.println("Number of Nikita: " +
                    reader.recordSearch(UserCommands.FIND_NUMBER_BY_NAME, name));
            System.out.println("Status of removing operation: " +
                    writer.changeFile(UserCommands.REMOVE, name,
                            number));
            System.out.println("Name of 1000: " +
                    reader.recordSearch(UserCommands.FIND_NAME_BY_NUMBER, number));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}