package seedu.address.logic.commands;

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

}
