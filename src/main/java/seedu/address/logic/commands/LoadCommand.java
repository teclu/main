package seedu.address.logic.commands;

public class LoadCommand {
/**
 * Loads the address book on the given filepath
 */
public class LoadCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the address book on the given filepath and overwrite the current address book.\n"
            + "Parameters: FILEPATH\n"
            + "Example: load data/addressbook-backup.xml";

    public static final String MESSAGE_LOAD_SUCCESS = "Load successful! File loaded from %1$s";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found at %1$s";

}
