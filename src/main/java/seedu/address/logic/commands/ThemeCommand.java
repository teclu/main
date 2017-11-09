package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author teclu
/**
 * Changes the theme of the Address Book.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme of the address book.\n"
            + "Parameters: THEME (must be either Light, Dark, Red, Blue or Green; case-insensitive)\n"
            + "Examples: theme Light, theme dark";

    public static final String MESSAGE_THEME_SUCCESS = "Theme updated to: %1$s";

    private final String theme;

    public ThemeCommand(String theme) {
        this.theme = (theme.trim()).toLowerCase();;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!isValidTheme(this.theme)) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME);
        }

        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(this.theme));
        return new CommandResult(String.format(MESSAGE_THEME_SUCCESS, this.theme));
    }

    private boolean isValidTheme(String theme) {
        return theme.equals("light") || theme.equals("dark") || theme.equals("red")
                || theme.equals("blue") || theme.equals("green");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.theme.equals(((ThemeCommand) other).theme)); // state check
    }

}
