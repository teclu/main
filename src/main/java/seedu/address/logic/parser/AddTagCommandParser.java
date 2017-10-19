package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.parseTags;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class AddTagCommandParser implements Parser<AddTagCommand>{
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Index index;

        if(trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split("\\s+");

        if (arguments.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(arguments[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        List<String> tagList = Arrays.asList(arguments[1]);
        Set<Tag> tagToAdd;

        try {
            tagToAdd = parseTagToAdd(tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        
        return new AddTagCommand(index, tagToAdd);
    }
    
    public Set<Tag> parseTagToAdd(List<String> tagToAdd) throws IllegalValueException {
        assert tagToAdd != null;
        return ParserUtil.parseTags(tagToAdd);
    }
    
}
