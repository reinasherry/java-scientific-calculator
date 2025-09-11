
import java.util.Scanner;
import java.util.ArrayList;

public class calculator {
    
    // ANSI color codes for better UI
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    
    private static float lastResult = 0;
    private static float memory = 0;
    private static boolean hasMemory = false;

    public static void displayWelcome() {
        System.out.println(PURPLE + "╔══════════════════════════════════════════╗");
        System.out.println("║" + CYAN + "             SCIENTIFIC CALCULATOR        " + PURPLE + "║");
        System.out.println("║" + YELLOW + "         With History & Memory Functions  " + PURPLE + "║");
        System.out.println("╚══════════════════════════════════════════╝" + RESET);
        System.out.println();
    }

    public static void displayMenu() {
        System.out.println(CYAN + "\n╔══════════════════════════════════════════╗");
        System.out.println("║               CALCULATOR MENU            ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ " + YELLOW + "   +  : Addition" + CYAN + "                         ║");
        System.out.println("║ " + YELLOW + "   -  : Subtraction" + CYAN + "                      ║");
        System.out.println("║ " + YELLOW + "   *  : Multiplication" + CYAN + "                   ║");
        System.out.println("║ " + YELLOW + "   /  : Division" + CYAN + "                         ║");
        System.out.println("║ " + YELLOW + "   %  : Modulus" + CYAN + "                          ║");
        System.out.println("║ " + YELLOW + "   ^  : Power (a^b)" + CYAN + "                      ║");
        System.out.println("║ " + YELLOW + "    sqrt : Square Root" + CYAN + "                   ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ " + GREEN + "   history : View calculation history" + CYAN + "    ║");
        System.out.println("║ " + GREEN + "   clear   : Clear current calculation" + CYAN + "   ║");
        System.out.println("║ " + GREEN + "   ms      : Store in memory" + CYAN + "             ║");
        System.out.println("║ " + GREEN + "   mr      : Recall from memory" + CYAN + "          ║");
        System.out.println("║ " + GREEN + "   =       : Finalize calculation" + CYAN + "        ║");
        System.out.println("║ " + RED + "   exit    : Exit calculator" + CYAN + "             ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║" + PURPLE + "Constants: pi (π), e, ans(previous result)" + CYAN + "║");
        System.out.println("╚══════════════════════════════════════════╝" + RESET);
    }

    public static void displayHistory(ArrayList<String> history) {
        if (history.isEmpty()) {
            System.out.println(YELLOW + "\n  History is empty" + RESET);
            return;
        }
        
        System.out.println(CYAN + "\n╔══════════════════════════════════════════╗");
        System.out.println("║               CALCULATION HISTORY              ║");
        System.out.println("╠══════════════════════════════════════════╣");
        
        for (int i = 0; i < history.size(); i++) {
            String entry = history.get(i);
            if (entry.startsWith("Final Result")) {
                System.out.println("║ " + GREEN + "  " + entry + CYAN + "                        ║");
            } else {
                System.out.println("║ " + YELLOW + (i + 1) + ". " + entry + 
                                 String.format("%" + (35 - entry.length()) + "s", "") + CYAN + "║");
            }
        }
        
        System.out.println("╚══════════════════════════════════════════╝" + RESET);
    }

    public static void displayResult(float result, String operation) {
        System.out.println(GREEN + "\n╔══════════════════════════════════════════╗");
        System.out.println("║                  RESULT                  ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║ %-15s: %-20.4f    ║\n", "Operation", result);
        System.out.println("╚══════════════════════════════════════════╝" + RESET);
    }

    public static float getNumberFromUser(Scanner scanner, String prompt) {
        System.out.print(CYAN + "  " + prompt + RESET);

        while (true) {
            if (scanner.hasNextFloat()) {
                float number = scanner.nextFloat();
                scanner.nextLine();
                return number;
            } else {
                String input = scanner.next().toLowerCase();
                
                switch (input) {
                    case "pi":
                        scanner.nextLine();
                        return (float) Math.PI;
                    case "e":
                        scanner.nextLine();
                        return (float) Math.E;
                    case "ans":
                        scanner.nextLine();
                        System.out.println(GREEN + "📋 Using previous result: " + lastResult + RESET);
                        return lastResult;
                    default:
                        System.out.println(RED + "❌ Error: '" + input + "' is not valid. Try numbers, 'pi', 'e', or 'ans'" + RESET);
                        System.out.print(CYAN + "🔢 " + prompt + RESET);
                }
            }
        }
    }

    public static String getOperationFromUser(Scanner scanner) {
        System.out.print(YELLOW + "\n⚡ Choose operation (or 'menu' for help): " + RESET);
        return scanner.next();
    }

    public static boolean isValidOperation(String operation) {
        String[] validOperations = {"+", "-", "*", "/", "%", "^", "sqrt", "=", 
                                   "history", "clear", "ms", "mr", "menu", "exit"};
        
        for (String validOp : validOperations) {
            if (validOp.equalsIgnoreCase(operation)) {
                return true;
            }
        }
        return false;
    }

    public static float calculate(float a, float b, String operation) {
        float result = 0;
        switch (operation) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) {
                    System.out.println(RED + "❌ Error: Division by zero!" + RESET);
                    result = Float.NaN;
                } else {
                    result = a / b;
                }
                break;
            case "%":
                result = a % b;
                break;
            case "^":
                result = (float) Math.pow(a, b);
                break;
            case "sqrt":
                if (a < 0) {
                    System.out.println(RED + "❌ Error: Cannot calculate square root of a negative number." + RESET);
                    result = Float.NaN;
                } else {
                    result = (float) Math.sqrt(a);
                }
                break;
            default:
                System.out.println(RED + "❌ Invalid operation!" + RESET);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        ArrayList<String> history = new ArrayList<>();
        
        displayWelcome();
        displayMenu();
        
        boolean continueCalculating = true;

        while (continueCalculating) {
            history.clear();
            float a = getNumberFromUser(myScanner, "Please enter a number: ");
            String operation = "";

            while (!operation.equals("=")) {
                operation = getOperationFromUser(myScanner);
                
                if (operation.equalsIgnoreCase("menu")) {
                    displayMenu();
                    continue;
                } else if (operation.equalsIgnoreCase("history")) {
                    displayHistory(history);
                    continue;
                } else if (operation.equalsIgnoreCase("clear")) {
                    System.out.println(YELLOW + "\n🧹 Calculation cleared!" + RESET);
                    history.clear();
                    a = getNumberFromUser(myScanner, "Please enter a new starting number: ");
                    continue;
                } else if (operation.equalsIgnoreCase("ms")) {
                    memory = a;
                    hasMemory = true;
                    System.out.println(GREEN + "💾 Value stored in memory: " + memory + RESET);
                    continue;
                } else if (operation.equalsIgnoreCase("mr")) {
                    if (hasMemory) {
                        a = memory;
                        System.out.println(GREEN + "📋 Recalled from memory: " + memory + RESET);
                    } else {
                        System.out.println(RED + "❌ No value stored in memory" + RESET);
                    }
                    continue;
                } else if (operation.equalsIgnoreCase("exit")) {
                    System.out.println(YELLOW + "\n👋 Thank you for using the calculator!" + RESET);
                    myScanner.close();
                    return;
                }
                
                if (!isValidOperation(operation)) {
                    System.out.println(RED + "❌ Invalid operation. Type 'menu' to see available options." + RESET);
                    continue;
                }
                
                if (operation.equals("=")) break;

                float b = 0;
                if (!operation.equals("sqrt")) {
                    b = getNumberFromUser(myScanner, "Please enter another number: ");
                }

                float result = calculate(a, b, operation);
                if (!Float.isNaN(result)) {
                    displayResult(result, operation);
                    lastResult = result;
                    
                    String historyEntry;
                    if (operation.equals("sqrt")) {
                        historyEntry = "sqrt(" + a + ") = " + result;
                    } else {
                        historyEntry = a + " " + operation + " " + b + " = " + result;
                    }
                    history.add(historyEntry);
                    
                    a = result;
                }
            }

            history.add("Final Result: " + a);
            System.out.println(GREEN + "\n🎯 Final Result = " + a + RESET);
            
            System.out.print(CYAN + "\n🔄 Do you want to calculate again? (yes/no): " + RESET);
            String response = myScanner.next().toLowerCase();
            continueCalculating = response.equals("yes") || response.equals("y");
            System.out.println();
        }
        
        myScanner.close();
        System.out.println(YELLOW + "\n👋 Okay bye! Thank you for using the calculator!" + RESET);
    }
}