package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlAddressBookStorage;

//@@author k-l-a
/**
 * Imports the contents of the address book file on the given filepath
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "i";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the contents of the address book on the given filepath, overwriting current data.\n"
            + "Parameters: FILEPATH\n"
            + "Example: import data/addressbook-backup.xml";

    public static final String MESSAGE_LOAD_SUCCESS = "Import successful! Data imported from %1$s";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found at %1$s";

    public final String filePathToImport;

    public ImportCommand(String filePath) {
        this.filePathToImport = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(filePathToImport);
            ReadOnlyAddressBook addressBook = addressBookStorage.readAddressBook().get();
            model.resetData(addressBook);
        } catch (Exception e) {
            // TODO : Improve error messages
            // currently any failure results in a FILE_NOT_FOUND message.
            return new CommandResult(String.format(MESSAGE_FILE_NOT_FOUND, filePathToImport));
        }

        return new CommandResult(String.format(MESSAGE_LOAD_SUCCESS, filePathToImport));
    }
}
