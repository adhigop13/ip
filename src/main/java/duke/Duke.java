package duke;
import dukeUiElements.Ui;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.image.Image;

public class Duke {
    private Ui ui;
    public static Path pathOfDirectory = Paths.get("./data/duke.txt");
    private Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    public Duke() {
        ui = new Ui();
        try {
            Storage.readFromDisk(pathOfDirectory, TaskList.getStoreTask());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getResponse(String input) {
        try {
            String dukeResponse = Parser.parse(input);
            if (dukeResponse.equals("bye")) {
                return "Bye. Hope to see you again soon!";
            } else {
                return dukeResponse;
            }
        } catch (DukeException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return Ui.indent + "Invalid character input";
        } catch (IndexOutOfBoundsException e) {
            return Ui.indent + "Invalid entry / Task not in list... Please try again...";
        } catch (IllegalArgumentException e) {
            return "OOPS!!! Sorry, but i do not know what that means :-(";
        }

    }

}