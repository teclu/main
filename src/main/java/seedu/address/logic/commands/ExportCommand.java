package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlAddressBookStorage;

//@@author k-l-a
/**
 * Exports the contents of the address book to the data folder with the given filename.
 */
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

    public final String filePathToExport;

    public ExportCommand(String fileName) {
        this.filePathToExport = EXPORT_FILEPATH + fileName;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            ReadOnlyAddressBook addressBook = model.getAddressBook();
            XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(filePathToExport);
            addressBookStorage.saveAddressBook(addressBook);
        } catch (Exception e) {
            return new CommandResult(String.format(MESSAGE_EXPORT_FAILURE, filePathToExport));
        }

        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePathToExport));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExportCommand
                && this.filePathToExport.equals(((ExportCommand) other).filePathToExport));
    }
}
