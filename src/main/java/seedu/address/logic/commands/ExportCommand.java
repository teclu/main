package seedu.address.logic.commands;

public class ExportCommand {
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "ex";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the contents of the address book to the data folder with the given filename.\n"
            + "Parameters: FILENAME.xml\n"
            + "Example: export backup.xml";

    public static final String MESSAGE_EXPORT_SUCCESS = "Export successful! Data exported to %1$s";
    public static final String MESSAGE_EXPORT_FAILURE = "Error writing to file at %1$s";

    public static final String EXPORT_FILEPATH = "data/";

}
