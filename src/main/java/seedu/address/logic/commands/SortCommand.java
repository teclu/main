package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sorts, depending on given option, either the current list or whole address book according to the given arguments.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String OPTION_ALL_WORD = "-a";
    public static final String ARGUMENT_ASCENDING_WORD = "asc";
    public static final String ARGUMENT_DESCENDING_WORD = "des";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts either the current list according to"
            + "given arguments. No prefix results in default order.\n"
            + "Parameters: [" + OPTION_ALL_WORD + "] [PREFIX] [ORDER]\n"
            + "Example : sort n/ asc";
    public static final String MESSAGE_SORT_SUCCESS = "Sorted current list by %1$s in %2$s order.";
    public static final String MESSAGE_SORT_ALL_SUCCESS = "Set default sort order to sort by %1$s in %2$s order.";

    public final boolean sortAll;
    public final String prefix;
    public final String order;

    public SortCommand(boolean sortAll, String prefix, String order) {
        this.sortAll = sortAll;
        this.prefix = prefix;
        this.order = order;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Comparator<ReadOnlyPerson> sortOrder = createAscComparator(prefix);
        if (ARGUMENT_DESCENDING_WORD.equals(order)) {
            sortOrder = sortOrder.reversed();
        }
        String prefix_string = prefix;
        String order_string = order;
        if (isNull(prefix)) {
            prefix_string = "default";
        }
        if (isNull(order)) {
            order_string = "asc";
        }
        if (sortAll) {
            model.setUserPrefsDefaultSortOrder(sortOrder);
            return new CommandResult(String.format(MESSAGE_SORT_ALL_SUCCESS, prefix_string, order_string));
        } else {
            model.updateSortedFilteredPersonList(sortOrder);
            return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, prefix_string, order_string));
        }
    }

    /**
     *
     */
    public Comparator<ReadOnlyPerson> createAscComparator(String prefix) {
        if (isNull(prefix)) {
            return (person1, person2) -> (-1);
        }

        if (prefix.equals(PREFIX_NAME.getPrefix())) {
            return (person1, person2) -> (person1.getName().fullName.compareToIgnoreCase(person2.getName().fullName));
        } else if (prefix.equals(PREFIX_PHONE.getPrefix())) {
            return (person1, person2) -> (person1.getPhone().value.compareToIgnoreCase(person2.getPhone().value));
        } else if (prefix.equals(PREFIX_EMAIL.getPrefix())) {
            return (person1, person2) -> (person1.getEmail().value.compareToIgnoreCase(person2.getPhone().value));
        } else if (prefix.equals(PREFIX_ADDRESS.getPrefix())) {
            return (person1, person2) -> (person1.getAddress().value.compareToIgnoreCase(person2.getAddress().value));
        } else if (prefix.equals(PREFIX_BIRTHDAY.getPrefix())) {
            return (person1, person2) -> (person1.getBirthday().value.compareToIgnoreCase(person2.getBirthday().value));
        }

        return null;
    }
}
