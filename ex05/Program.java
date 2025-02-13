package ex05;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Missing --profile argument");
            displayHelp();
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            Menu menu = new Menu(parseLaunchMode(args[0]), scanner);
            menu.launch();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void displayHelp() {
        System.out.println("You have only two launch modes (dev, production):");
        System.out.println("Run 'java Program --profile=dev' to launch in dev mode.");
        System.out.println("Run 'java Program --profile=production' to launch in production mode.");
    }

    private static String parseLaunchMode(String arg) {
        String[] argParts = arg.split("=");

        if (argParts.length != 2 || !argParts[0].equals("--profile") 
            || (!argParts[1].equals("dev") && !argParts[1].equals("production"))) {
            throw new IllegalArgumentException("Invalid launch mode argument: " + arg);
        }

        return argParts[1];
    }
}
