package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_USAGE;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author k-l-a
/**
 * Parses input arguments and create a ExportCommand Object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    public static final String EXPORT_FILE_EXTENSION = ".xml";

    /**
     * Parses the given (@code String) in the context of a ExportCommand.
     * @param args
     * @return ExportCommand Object for execution
     */
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (!trimmedArgs.endsWith(EXPORT_FILE_EXTENSION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        return new ExportCommand(trimmedArgs);
    }
}
