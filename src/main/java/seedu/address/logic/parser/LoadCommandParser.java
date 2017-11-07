package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.LoadCommand.MESSAGE_USAGE;

import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author k-l-a
/**
 * Parses input arguments and create a new LoadCommand Object.
 */
public class LoadCommandParser implements Parser<LoadCommand> {

    /**
     * Parses the given (@code String) in the context of a LoadCommand.
     * @param args
     * @return LoadCommand Object for execution
     */
    public LoadCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        return new LoadCommand(trimmedArgs);
    }
}
