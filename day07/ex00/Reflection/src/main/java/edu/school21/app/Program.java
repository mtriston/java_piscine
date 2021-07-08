package edu.school21.app;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    private static void printDelimiter() {
        System.out.println("---------------------");
    }

    private static Object askChoice() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println(
                "Classes:\n" +
                        "User\n" +
                        "Car\n");
        printDelimiter();
        System.out.println("Enter class name:");
        Object obj = Class.forName("edu.school21.classes." + scanner.next()).newInstance();
        printDelimiter();
        return obj;
    }

    private static void printFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        System.out.println("fields:");
        for (Field field : fields) {
            System.out.printf("    %s %s\n", field.getType().getSimpleName(), field.getName());
        }
    }

    private static void createObject(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        System.out.println("Let's create an object.");
        for (Field field : fields) {
            System.out.println(field.getName());
            field.setAccessible(true);
            String fieldType = field.getType().getSimpleName();
            scanAndSetField(obj, field);
        }
        System.out.printf("Object created: %s\n", obj);
        printDelimiter();
    }

    private static void changeField(Object obj) throws NoSuchFieldException, IllegalAccessException {
        System.out.println("Enter name of the field for changing:");
        String fieldName = scanner.next();
        Field field = obj.getClass().getDeclaredField(fieldName);
        String fieldType = field.getType().getSimpleName();
        System.out.printf("Enter %s value\n", fieldType);
        scanAndSetField(obj, field);
        System.out.printf("Object updated: %s\n", obj);
        printDelimiter();
    }

    private static void scanAndSetField(Object obj, Field field) throws IllegalAccessException {
        String fieldType = field.getType().getSimpleName();
        field.setAccessible(true);
        switch (fieldType) {
            case "String":
                field.set(obj, scanner.next());
                break;
            case "Integer":
            case "int":
                field.setInt(obj, scanner.nextInt());
                break;
            case "Double":
            case "double":
                field.setDouble(obj, scanner.nextDouble());
                break;
            case "Boolean":
            case "boolean":
                field.setBoolean(obj, scanner.nextBoolean());
                break;
            case "Long":
            case "long":
                field.setLong(obj, scanner.nextLong());
                break;
        }
    }

    private static void printMethods(Object obj) {
        Method[] methods = obj.getClass().getDeclaredMethods();
        System.out.println("methods:");
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase("toString"))
                continue;
            System.out.println(methodSignature(method));
        }
        printDelimiter();
    }

    private static String methodSignature(Method method) {
        StringBuilder name = new StringBuilder();
        name.append(String.format("%s(", method.getName()));
        Class<?>[] params = method.getParameterTypes();
        for (int i = 0; i < params.length; ++i) {
            name.append(params[i].getSimpleName());
            if (i != params.length - 1) {
                name.append(", ");
            }
        }
        name.append(")");
        return name.toString();
    }

    private static void callMethod(Object obj) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Enter name of the method for call:");
        scanner.nextLine();
        String methodName = scanner.nextLine();
        Method method = null;
        for (Method m : obj.getClass().getDeclaredMethods()) {
            if (methodSignature(m).equalsIgnoreCase(methodName)) {
                method = m;
                break;
            }
        }
        assert method != null;
        Object[] params = new Object[method.getParameterCount()];
        Class<?>[] types = method.getParameterTypes();
        for (int i = 0; i < params.length; ++i) {
            String paramType = types[i].getSimpleName();
            System.out.printf("Enter %s value:\n", paramType);
            switch (paramType) {
                case "String":
                    params[i] = scanner.next();
                    break;
                case "Integer":
                case "int":
                    params[i] = scanner.nextInt();
                    break;
                case "Double":
                case "double":
                    params[i] = scanner.nextDouble();
                    break;
                case "Boolean":
                case "boolean":
                    params[i] = scanner.nextBoolean();
                    break;
                case "Long":
                case "long":
                    params[i] = scanner.nextLong();
                    break;
            }
        }
        Object ret = method.invoke(obj, params);
        if (!method.getReturnType().getSimpleName().equalsIgnoreCase("void")) {
            System.out.println("Method returned:");
            System.out.println(ret);
        }
    }


    public static void main(String[] args) {
        while (true) {
            try {
                Object obj = askChoice();
                printFields(obj);
                printMethods(obj);
                createObject(obj);
                changeField(obj);
                callMethod(obj);
                break;
            } catch (Exception e) {
                System.err.println("Error! Try again.");
            }
        }
    }
}
