package seedu.address.logic.parser;

public class SortCommandParser {

    /**
     * Checks if the given string prefix is a sortable prefix (n/, p/, e/, a/, b/).
     */
    public boolean isSortablePrefix(String prefixString) {
        return prefixString.equals(PREFIX_NAME.getPrefix()) || prefixString.equals(PREFIX_PHONE.getPrefix())
                || prefixString.equals(PREFIX_ADDRESS.getPrefix()) || prefixString.equals(PREFIX_EMAIL.getPrefix())
                || prefixString.equals(PREFIX_BIRTHDAY.getPrefix());
    }

    /**
     * Checks if the given string is a sort order argument (asc, des)
     */
    public boolean isSortArgument(String sortOrder) {
        return sortOrder.equals(ARGUMENT_ASCENDING_WORD)
                || sortOrder.equals(ARGUMENT_DESCENDING_WORD);
    }
}
