package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a tag to a person in the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to a person in the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG"
            + "Example: " + COMMAND_WORD + " 1 "
            + "owesMoney";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "AddTag command not implemented yet";
    
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
