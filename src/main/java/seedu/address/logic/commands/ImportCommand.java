package seedu.address.logic.commands;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.DataConversionException;
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

    public static final String MESSAGE_IMPORT_SUCCESS = "Import successful! Data imported from %1$s";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found at %1$s Import Failed!";
    public static final String MESSAGE_FILE_UNKNOWN = "File is in unknown format or is corrupt. Import failed!";
    public static final String MESSAGE_ILLEGAL_VALUE = "File contains illegal values. "
            + "Please check integrity of data. Import failed!";

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
        } catch (NoSuchElementException ne) {
            return new CommandResult(String.format(MESSAGE_FILE_NOT_FOUND, filePathToImport));
        } catch (FileNotFoundException fnf) {
            return new CommandResult(String.format(MESSAGE_FILE_NOT_FOUND, filePathToImport));
        } catch (DataConversionException dc) {
            return new CommandResult(MESSAGE_FILE_UNKNOWN);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_ILLEGAL_VALUE);
        }

        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, filePathToImport));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ImportCommand
                && this.filePathToImport.equals(((ImportCommand) other).filePathToImport));
    }
}
