/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("WELCOME TO COFFEE MAKER. WHAT WOULD YOU LIKE TO HAVE");

        String logo = "                                                                                     \n" +
                "                         ad88    ad88                       \n" +
                "                        d8\"     d8\"                         \n" +
                "                        88      88                          \n" +
                " ,adPPYba,  ,adPPYba, MM88MMM MM88MMM ,adPPYba,  ,adPPYba,  \n" +
                "a8\"     \"\" a8\"     \"8a  88      88   a8P_____88 a8P_____88  \n" +
                "8b         8b       d8  88      88   8PP\"\"\"\"\"\"\" 8PP\"\"\"\"\"\"\"  \n" +
                "\"8a,   ,aa \"8a,   ,a8\"  88      88   \"8b,   ,aa \"8b,   ,aa  \n" +
                " `\"Ybbd8\"'  `\"YbbdP\"'   88      88    `\"Ybbd8\"'  `\"Ybbd8\"'  \n" +
                "                                                             \n" +
                "                                                             \n";

        System.out.println(logo);

        Map<String, Map<String, Integer>> MENU = new HashMap<>();
        Map<String, Integer> espressoIngredients = new HashMap<>();
        espressoIngredients.put("water", 50);
        espressoIngredients.put("coffee", 18);
        MENU.put("espresso", espressoIngredients);

        Map<String, Integer> latteIngredients = new HashMap<>();
        latteIngredients.put("water", 200);
        latteIngredients.put("milk", 150);
        latteIngredients.put("coffee", 24);
        MENU.put("latte", latteIngredients);

        Map<String, Integer> cappuccinoIngredients = new HashMap<>();
        cappuccinoIngredients.put("water", 250);
        cappuccinoIngredients.put("coffee", 24);
        cappuccinoIngredients.put("milk", 100);
        MENU.put("cappuccino", cappuccinoIngredients);

        Map<String, Integer> resources = new HashMap<>();
        resources.put("water", 300);
        resources.put("milk", 200);
        resources.put("coffee", 100);

        double profit = 0;

        boolean isOn = true;
        Scanner scanner = new Scanner(System.in);

        while (isOn) {
            System.out.print("What would you like to have? (espresso/latte/cappuccino/off): ");
            String choice = scanner.nextLine();

            if (choice.equals("off")) {
                isOn = false;
            } else if (choice.equals("report")) {
                System.out.println("Water: " + resources.get("water") + " ml");
                System.out.println("Milk: " + resources.get("milk") + " ml");
                System.out.println("Coffee: " + resources.get("coffee") + " g");
                System.out.println("Profit: $" + profit);
            } else if (choice.equals("espresso") || choice.equals("latte") || choice.equals("cappuccino")) {
                if (isResourcesSufficient(choice, MENU, resources)) {
                    double payment = processCoins(scanner);
                    if (isTransactionSuccessful(choice, MENU, resources, payment, profit)) {
                        makeCoffee(choice, MENU, resources);
                    }
                }
            } else {
                System.out.println("Invalid choice. Please choose from the available options.");
            }
        }
    }

    private static boolean isResourcesSufficient(String choice, Map<String, Map<String, Integer>> MENU, Map<String, Integer> resources) {
        Map<String, Integer> orderIngredients = MENU.get(choice);
        if (orderIngredients == null) {
            System.out.println("Invalid choice.");
            return false;
        }

        for (String item : orderIngredients.keySet()) {
            if (orderIngredients.get(item) > resources.getOrDefault(item, 0)) {
                System.out.println("Sorry, there is not enough " + item);
                return false;
            }
        }
        return true;
    }

    private static double processCoins(Scanner scanner) {
        System.out.println("Please insert coins:");
        System.out.print("How many Quarters? ");
        int quarters = Integer.parseInt(scanner.nextLine());
        System.out.print("How many Dimes? ");
        int dimes = Integer.parseInt(scanner.nextLine());
        System.out.print("How many Nickels? ");
        int nickels = Integer.parseInt(scanner.nextLine());
        System.out.print("How many Pennies? ");
        int pennies = Integer.parseInt(scanner.nextLine());

        return quarters * 0.25 + dimes * 0.1 + nickels * 0.05 + pennies * 0.01;
    }

    private static boolean isTransactionSuccessful(String choice, Map<String, Map<String, Integer>> MENU, Map<String, Integer> resources, double payment, double profit) {
        double cost = getCost(choice, MENU);
        if (payment >= cost) {
            double change = payment - cost;
            System.out.println("Here is $" + String.format("%.2f", change) + " in change.");
            profit += cost;

            for (String item : MENU.get(choice).keySet()) {
                resources.put(item, resources.get(item) - MENU.get(choice).get(item));
            }

            return true;
        } else {
            System.out.println("Sorry, not enough money. Money refunded.");
            return false;
        }
    }

    private static double getCost(String choice, Map<String, Map<String, Integer>> MENU) {
        return MENU.get(choice).entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * getItemCost(entry.getKey()))
                .sum();
    }

    private static double getItemCost(String item) {
        switch (item) {
            case "water":
                return 0.1;
            case "milk":
                return 0.15;
            case "coffee":
                return 0.2;
            default:
                return 0.0;
        }
    }

    private static void makeCoffee(String choice, Map<String, Map<String, Integer>> MENU, Map<String, Integer> resources) {
        System.out.println("Dispensing your " + choice + "...");
    }
}
