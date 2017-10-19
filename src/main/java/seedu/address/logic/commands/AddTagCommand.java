package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to a person in the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to a person in the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "owesMoney";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tag: %2$s";

    private final Index index;
    private final Set<Tag> addedTag;

    /**
     * @param index of the person in the filtered person list to add tag
     * @param addedTag tag of the person
     */

    public AddTagCommand(Index index, Set<Tag> addedTag) {
        requireNonNull(index);
        requireNonNull(addedTag);

        this.index = index;
        this.addedTag = addedTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), addedTag));
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }
        
        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }
        
        // state check
        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index)
                && addedTag.equals(e.addedTag);
    }
}
