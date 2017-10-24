package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person's tag in the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a person's tag in the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[TAG] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "neighbours";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tag: %2$s";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag deleted!";
    public static final String MESSAGE_NOT_EXIST_TAG = "This tag does not exist for this person.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the address book.";

    private final Index index;
    private final Set<Tag> deletedTag;

    /**
     * @param index of the person in the filtered person list to delete tag
     * @param deletedTag tag of the person
     */

    public DeleteTagCommand(Index index, Set<Tag> deletedTag) {
        requireNonNull(index);
        requireNonNull(deletedTag);

        this.index = index;
        this.deletedTag = deletedTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, deletedTag);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, editedPerson));
    }
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             Set<Tag> deletedTag) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Birthday birthday = personToEdit.getBirthday();

        Set<Tag> initialTags = personToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();

        try {
            updatedTags = getUpdatedTags(initialTags, deletedTag);
        } catch (CommandException e) {
            throw new CommandException(e.getMessage());
        }

        return new Person(name, phone, email, address, birthday, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        // state check
        DeleteTagCommand e = (DeleteTagCommand) other;
        return index.equals(e.index)
                && deletedTag.equals(e.deletedTag);
    }

    public static HashSet<Tag> getUpdatedTags(Set<Tag> initialTag, Set<Tag> deletedTag) throws CommandException {
        HashSet<Tag> updatedTags = new HashSet<>(initialTag);
        for (Tag toDelete : deletedTag) {
            requireNonNull(toDelete);
            if (!initialTag.contains(toDelete)) {
                throw new CommandException(MESSAGE_NOT_EXIST_TAG);
            }
            updatedTags.remove(toDelete);
        }
        return updatedTags;
    }
}
