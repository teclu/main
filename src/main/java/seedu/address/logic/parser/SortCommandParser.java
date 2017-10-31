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

}
