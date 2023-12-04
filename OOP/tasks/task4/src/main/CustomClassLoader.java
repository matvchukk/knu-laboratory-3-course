package main;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class CustomClassLoader extends ClassLoader{
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("java.")){
            return super.loadClass(name);
        }

        byte[] data = loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name){
        try(InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(name.replace(".","/") + ".class");){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int current = 0;

            while((current = input.read()) != -1){
                output.write(current);
            }

            return output.toByteArray();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}