package seedu.address.logic.commands;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book whose name or tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or tags, depending "
            + "on an optional given prefix, contain any of the specified keywords (case-insensitive) and displays "
            + "them as a list with index numbers. Matches name by default.\n"
            + "Parameters: [PREFIX] KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/ alice bob charlie";

    private final Predicate<ReadOnlyPerson> predicate;

    private final Comparator<ReadOnlyPerson> sortOrderComparator;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;
        this.sortOrderComparator = null;
    }

    public FindCommand(Predicate<ReadOnlyPerson> predicate, String keyword) {
        this.predicate = predicate;
        this.sortOrderComparator = Comparator.comparingInt(person -> person.getName().fullName.toLowerCase()
                .indexOf(keyword));
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
