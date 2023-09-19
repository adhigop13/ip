package duke;
import dukeuielements.Ui;
import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;
import java.util.ArrayList;
import java.util.ListIterator;
import java.time.format.DateTimeParseException;


/**
 * This class holds the ArrayList to load all the tasks. Contains important Task manipulation methods.
 */

public class TaskList {
    //stores all the tasks
    private static ArrayList<Task> storeTask = new ArrayList<>(1);
    /**
     * Displays the entire list of tasks to user.
     */
    public static String userListChoice() {
        ListIterator<Task> ls = storeTask.listIterator();
        return Ui.printList(ls);
    }

    /**
     * Marks or unmarks the task.
     *
     * @param taskNumber The task number to be marked/unmarked.
     * @param userMarkerChoice User choice mark/unmark.
     */
    public static String userMarkUnmark(String taskNumber, String userMarkerChoice) {
        Task taskItem = storeTask.get(Integer.parseInt(taskNumber) - 1);
        return taskItem.changeStatus(userMarkerChoice);
    }

    /**
     * Adds task with given description to ArrayList.
     *
     * @param userDescription Description attached to task.
     */
    public static String addToDo(String userDescription) {
        storeTask.add(new ToDo(userDescription));
        return Ui.printNumberOfEntries();
    }

    /**
     * Adds a new Deadline task to ArrayList.
     *
     * @param userDescription Description attached to Deadline.
     * @param deadlineBy Deadline given in yyyy-mm-dd HH:mm format.
     */
    public static String addDeadline(String userDescription, String deadlineBy) {
        try {
            storeTask.add(new Deadline(userDescription, deadlineBy));
            return Ui.printNumberOfEntries();
        } catch (DateTimeParseException e) {
            return Ui.invalidDateTimeEntry();
        }
    }

    /**
     * Adds a new Deadline task to ArrayList.
     *
     * @param userDescription Description attached to Event.
     * @param from From Date & Time given in yyyy-mm-dd HH:mm format.
     * @param to To Date & Time given in yyyy-mm-dd HH:mm format.
     */
    public static String addEvent(String userDescription, String from, String to) {
        try {
            storeTask.add(new Event(userDescription, from, to));
            return Ui.printNumberOfEntries();
        } catch (DateTimeParseException e) {
            return Ui.invalidDateTimeEntry();
        }
    }

    /**
     * Deletes task from ArrayList.
     *
     * @param delUserChoice The task number to be deleted (based on number on list).
     * @throws DukeException  If TaskList is empty or invalid selection by user.
     */
    public static String deleteTask(int delUserChoice) throws DukeException {
        if ((delUserChoice - 1) < 0) { //if number entered smaller than 1, array will go negative index.
            throw new DukeException("Invalid Task entered. Please try again...");
        } else if (storeTask.isEmpty()) {
            throw new DukeException("Task Scheduler is empty... Please try again!");
        } else {
            Task itemRemoved = storeTask.remove(delUserChoice - 1);
            return Ui.deleteTaskPrint(itemRemoved);
        }
    }
    public static String findTask(String findThis) {
        ArrayList<Task> filteredList = altFindFunctions(findThis);
        ListIterator<Task> iterFilteredList = filteredList.listIterator();
        if (filteredList.size() == 0) {
            return Ui.emptyList();
        } else {
            return Ui.findTaskPrint(iterFilteredList);
        }
    }
    private static ArrayList<Task> altFindFunctions(String findThis) {
        String[] breakDownFindFunction = findThis.split(" ");
        ListIterator<Task> ls = storeTask.listIterator();
        ArrayList<Task> filteredList = new ArrayList<>();
        switch (breakDownFindFunction.length) {
        case 1:
            return normalFind(findThis, ls, filteredList);
        case 2:
            return taskFind(breakDownFindFunction, ls, filteredList);

        default:
            return filteredList;
        }
    }

    private static ArrayList<Task> normalFind(String findThis, ListIterator<Task> ls, ArrayList<Task> filteredList) {
        while (ls.hasNext()) {
            Task current = ls.next();
            String currentDescription = current.getDescription();
            if (currentDescription.contains(findThis)) {
                filteredList.add(current);
            }
        }
        return filteredList;
    }
    private static ArrayList<Task> taskFind(String[] breakDownFindFunction, ListIterator<Task> ls, ArrayList<Task> filteredList) {
        String findFunctionMainType = breakDownFindFunction[0];
        String findFunctionSubType = breakDownFindFunction[1];
        if (findFunctionMainType.equals("all")) {
            return getTasks(ls, filteredList, findFunctionSubType);
        } else {
            return filteredList;
        }
    }

    private static ArrayList<Task> getTasks(ListIterator<Task> ls, ArrayList<Task> filteredList, String findFunctionSubType) {
        while (ls.hasNext()) {
            Task current = ls.next();
            String currentTaskType = current.getClass().getSimpleName();
            if (currentTaskType.contains(findFunctionSubType)) {
                filteredList.add(current);
            }
        }
        return filteredList;
    }

    public static ArrayList<Task> getStoreTask() {
        return storeTask;
    }
    public static int getTaskSize() {
        return storeTask.size();
    }
}
