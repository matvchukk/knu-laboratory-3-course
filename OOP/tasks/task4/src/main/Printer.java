package main;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Printer {
    private final Class<?> classData;

    public Printer(String name) {
        try {
            CustomClassLoader loader = new CustomClassLoader();
            classData = loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(){
        printClassName();
        printClassFields();
        printClassConstructors();
        printClassMethods();
        printSuperClasses();
        printImplementedInterfaces();
        printPackages();
    }

    private void printClassName() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~`Name`~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(classData.getName());
    }

    private void printClassFields() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~`Fields`~~~~~~~~~~~~~~~~~~~~~");

        Field[] fields = classData.getDeclaredFields();

        for(Field field : fields) {
            System.out.println(field);
        }
    }

    private void printClassConstructors() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~`Constructors`~~~~~~~~~~~~~~~~~~~~~");

        Constructor<?>[] constructors = classData.getConstructors();

        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
    }

    private void printClassMethods() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~`Methods`~~~~~~~~~~~~~~~~~~~~~");

        Method[] methods = classData.getMethods();

        for (Method method : methods){
            System.out.println(method);
        }
    }

    private void printSuperClasses() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~`Super Classes`~~~~~~~~~~~~~~~~~~~~~");

        Class<?> superClass = classData.getSuperclass();

        while (superClass != null){
            System.out.println(superClass.getName());
            superClass = superClass.getSuperclass();
        }
    }

    private void printImplementedInterfaces() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~`Implemented Interfaces`~~~~~~~~~~~~~~~~~~~~~");

        Class<?>[] interfaces = classData.getInterfaces();

        for (Class<?> curInterface : interfaces){
            System.out.println(curInterface.getName());
        }
    }

    private void printPackages() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~`Packages`~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(classData.getPackage().getName());
    }
}