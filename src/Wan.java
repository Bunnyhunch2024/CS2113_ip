import java.util.Scanner;

public class Wan {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    public static void main(String[] args) {
        printLine();
        System.out.println("     Hello! I'm Wan.");
        System.out.println("     What can I do for you?");
        printLine();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                break;
            }

            handleCommand(input);
        }

        scanner.close();
    }

    private static void handleCommand(String input) {
        printLine();

        if (input.equals("list")) {
                listTasks();
        } else if (input.startsWith("mark ")) {
                markTask(input, true);
        } else if (input.startsWith("unmark ")) {
                markTask(input, false);
        } else if (input.startsWith("todo ")) {
                addTodo(input);
        } else if (input.startsWith("deadline ")) {
                addDeadline(input);
        } else if (input.startsWith("event ")) {
                addEvent(input);
        } else {
                System.out.println("    Wrong input");
        }

        printLine();
    }

    private static void addTodo(String input) {
        String description = input.substring(5).trim();

        Task newTask = new Todo(description);
        tasks[taskCount++] = newTask;

        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + newTask);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
    }

    private static void addDeadline(String input) {
        String content = input.substring(9).trim();
        String[] parts = content.split(" /by ", 2);

        if (parts.length < 2) {
            System.out.println("     Please specify the deadline with /by!");
            return;
        }

        String description = parts[0].trim();
        String by = parts[1].trim();
        Task newTask = new Deadline(description, by);
        tasks[taskCount++] = newTask;

        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + newTask);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
    }

    private static void addEvent(String input) {
        String content = input.substring(6).trim();
        String[] parts = content.split(" /from ", 2);

        if (parts.length < 2) {
            System.out.println("     Please specify the event time with /from and /to!");
            return;
        }

        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ", 2);
        String from = timeParts[0].trim();
        String to = timeParts[1].trim();
        Task newTask = new Event(description, from, to);
        tasks[taskCount++] = newTask;

        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + newTask);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
    }

    private static void listTasks() {
        if (taskCount == 0) {
            System.out.println("     You have no tasks in your list.");
        } else {
            System.out.println("     Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println("     " + (i + 1) + "." + tasks[i]);
            }
        }
    }

    private static void markTask(String input, boolean isDone) {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("     Please specify a task number!");
            return;
        }

        int index = Integer.parseInt(parts[1]) - 1;
        if (index < 0 || index >= taskCount) {
            System.out.println("     Task number out of range!");
            return;
        }

        if (isDone) {
            tasks[index].markAsDone();
            System.out.println("     Nice! I've marked this task as done:");
        } else {
            tasks[index].markAsNotDone();
            System.out.println("     OK, I've marked this task as not done yet:");
        }
        System.out.println("       " + tasks[index]);
    }

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }
}