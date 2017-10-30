package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagListContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        // use of prefix to indicate the property to match (name, tag, phone, and email)
        FindCommand expectedFindCommandForTags =
                new FindCommand(new TagListContainsKeywordsPredicate(Arrays.asList("Friends", "Family")));
        FindCommand expectedFindCommandForPhone =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("85355251")));
        FindCommand expectedFindCommandForEmail =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("asdf@example.com")));
        FindCommand expectedFindCommandForAddress =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("clementi")));
        assertParseSuccess(parser, "n/ Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, "t/ Friends Family", expectedFindCommandForTags);
        assertParseSuccess(parser, "p/ 85355251", expectedFindCommandForPhone);
        assertParseSuccess(parser, "e/ asdf@example.com", expectedFindCommandForEmail);
        assertParseSuccess(parser, "a/ clementi", expectedFindCommandForAddress);
    }

}
