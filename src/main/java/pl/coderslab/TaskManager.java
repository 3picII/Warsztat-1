package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.awt.desktop.FilesEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    public static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    public static final String SELECT = "Please select an option";
    static String[][] LIST;

    public static void welcomeText(String[] scv) {
        System.out.println(ConsoleColors.BLUE + SELECT);
        for (String i : scv) {
            System.out.println(ConsoleColors.RESET + i);
        }
    }

    public static void main(String[] args) {
        File listFile = new File("tasks.csv");
        try {
            LIST = dataToArray(FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        welcomeText(OPTIONS);
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String d = scan.nextLine();
            if (d.equals("add")) {
                add();
            } else if (d.equals("remove")) {
                remove(LIST, selectToRemove());
            } else if (d.equals("list")) {
                taskListed(LIST);
            } else if (d.equals("exit")) {
                exit("tasks.csv", LIST);
                System.out.println(ConsoleColors.RED + "Adiós");
                System.exit(0);
            } else {
                System.out.println(ConsoleColors.YELLOW + "Please enter right command");
            }

            welcomeText(OPTIONS);
        }
    }

    public static String[][] dataToArray(String textFile) throws IOException {
        Path load = Paths.get(textFile);
        if(!Files.exists(load)){
            System.out.println(ConsoleColors.YELLOW + "Nie znaleziono pliku");
        }
        String[][] tab = null;
        List<String> loadToList = Files.readAllLines(load);
        tab = new String[loadToList.size()][loadToList.get(0).split(",").length];
        for (int i = 0; i < loadToList.size(); i++) {
            String[] dividedLoad = loadToList.get(i).split(",");
            for (int j = 0; j < textFile.length() - 1; j++) {
                try {
                    tab[i][j] = dividedLoad[j];
                } catch (NullPointerException e) {
                }catch (ArrayIndexOutOfBoundsException z){
                }
            }
        }
        return tab;
    }

    public static void add() {
        System.out.println("Please enter task description");
        Scanner scanAdd = new Scanner(System.in);
        String taskDescription = scanAdd.nextLine();
        System.out.println("Please add task due date");
        String taskDueDate = scanAdd.nextLine();
        System.out.println("Is your task important: true/false");
        String taskImportancy = scanAdd.nextLine();
        try {
            LIST = Arrays.copyOf(LIST, LIST.length + 1);
            LIST[LIST.length - 1] = new String[3];
            LIST[LIST.length - 1][0] = taskDescription;
            LIST[LIST.length - 1][1] = taskDueDate;
            LIST[LIST.length - 1][2] = taskImportancy;
        } catch (NullPointerException e) {
        }
    }

    public static void remove(String[][] textArray, int remove) {
        Path load = Paths.get("tasks.csv");
        try {
            if (remove < textArray.length) {
                LIST = ArrayUtils.remove(textArray, remove);
                System.out.println("Value was successfully removed");
            } else {
                System.out.println(ConsoleColors.YELLOW + "Cannot find the number");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Cannot find the number");
        } catch (NullPointerException z) {
        }


    }


    public static void taskListed(String[][] mir) {
        Path load = Paths.get("tasks.csv");
        try{
        for(int i = 0; i < mir.length; i++){
            System.out.print(i + " : ");
            for(int j = 0; j < mir.length; j++) {
                System.out.print(mir[i][j] + " ");
            }
            System.out.println();
            }
        }catch (NullPointerException e) {
        }catch (ArrayIndexOutOfBoundsException z){}


    }

    public static void exit(String fileName, String[][] tab) {
        Path load = Paths.get(fileName);
        try {
            String[] lines = new String[LIST.length];
            for (int i = 0; i < tab.length; i++) {
                lines[i] = String.join(",", tab[i]);
            }
            Files.write(load, Arrays.asList(lines));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException z) {

        }

    }

    public static int selectToRemove() {
        int result = 0;
        System.out.println("Please select number to remove");
        Scanner scan = new Scanner(System.in);
        String number1 = scan.nextLine();
        result = Integer.parseInt(number1);
        return result;
    }
}
