package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlAddressBookStorage;

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

    public final String filePathToLoad;

    public LoadCommand(String filePath) {
        this.filePathToLoad = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(filePathToLoad);
            ReadOnlyAddressBook addressBook = addressBookStorage.readAddressBook().get();
            model.resetData(addressBook);
        } catch (Exception e) {
            return new CommandResult(String.format(MESSAGE_FILE_NOT_FOUND, filePathToLoad));
        }

        return new CommandResult(String.format(MESSAGE_LOAD_SUCCESS, filePathToLoad));
    }
}
