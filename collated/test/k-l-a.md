# k-l-a
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
    //---------------- Tests for containsWordPartialIgnoreCase --------------------------------------
    @Test
    public void containsWordPartialIgnoreCase_nullWord_throwsNullPointerException() {
        assertOtherExceptionThrown(NullPointerException.class, "typical sentence", null,
                Optional.empty());
    }

    private void assertOtherExceptionThrown(Class<? extends Throwable> exceptionClass, String sentence,
            String word, Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsWordPartialIgnoreCase(sentence, word);
    }

    @Test
    public void containsWordPartialIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertOtherExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordPartialIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertOtherExceptionThrown(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordPartialIgnoreCase_nullSentence_throwsNullPointerException() {
        assertOtherExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *   - a query word matches part of a sentence word
     *   - a query word matches part of multiple sentence word
     *
     * Possible scenarios returning false:
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordPartialIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordPartialIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordPartialIgnoreCase("    ", "123"));

        // Sentence partially matches query
        assertFalse(StringUtil.containsWordPartialIgnoreCase("aaa abb ccc", "bbbb"));

        // No Match
        assertFalse(StringUtil.containsWordPartialIgnoreCase("aaa abb ccc", "ddd"));

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordPartialIgnoreCase("aaa bBb ccc", "Bbb"));
        assertTrue(StringUtil.containsWordPartialIgnoreCase("aaa bBb ccc@1", "CCc@1"));
        assertTrue(StringUtil.containsWordPartialIgnoreCase("  AAA   bBb   ccc  ", "aaa"));
        assertTrue(StringUtil.containsWordPartialIgnoreCase("Aaa", "aaa"));
        assertTrue(StringUtil.containsWordPartialIgnoreCase("aaa bbb ccc", "  ccc  "));

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordPartialIgnoreCase("AAA bBb ccc  bbb", "bbB"));

        // Query partially matches sentence
        assertTrue(StringUtil.containsWordPartialIgnoreCase("AAa bbb cCc abc", "ab"));
        assertTrue(StringUtil.containsWordPartialIgnoreCase("AAa bcb cbc abc", "bc")); //multiple partial matches
    }
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    /**
     * Parses {@code userInput} into a {@code FindCommand} for tag prefix.
     */
    private FindCommand prepareCommandForTags(String userInput) {
        FindCommand command =
                new FindCommand(new TagListContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with Model) and unit tests for SortCommand
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String validPrefix = PREFIX_NAME.getPrefix();
        String validOrder = ARGUMENT_ASCENDING_WORD;
        final SortCommand typicalCommand = new SortCommand(validPrefix, validOrder);

        //same object -> returns true
        assertTrue(typicalCommand.equals(typicalCommand));

        //same values -> returns true
        SortCommand commandWithSameValues = new SortCommand(PREFIX_NAME.getPrefix(), ARGUMENT_ASCENDING_WORD);
        assertTrue(typicalCommand.equals(commandWithSameValues));

        //null -> returns false
        assertFalse(typicalCommand.equals(null));

        //different type -> returns false
        assertFalse(typicalCommand.equals(new ClearCommand()));

        //different prefix -> returns false
        assertFalse(typicalCommand.equals(new SortCommand(PREFIX_EMAIL.getPrefix(), validOrder)));

        //different order -> returns false
        assertFalse(typicalCommand.equals(new SortCommand(validPrefix, ARGUMENT_DESCENDING_WORD)));
    }

    @Test
    public void execute_defaultOrder_success() {
        String expectedMessageA = String.format(MESSAGE_SORT_SUCCESS, ARGUMENT_DEFAULT_ORDER, ARGUMENT_ASCENDING_WORD);
        String expectedMessageD = String.format(MESSAGE_SORT_SUCCESS, ARGUMENT_DEFAULT_ORDER, ARGUMENT_DESCENDING_WORD);

        SortCommand commandA = prepareCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_ASCENDING_WORD);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (-1));
        assertCommandSuccess(commandA, model, expectedMessageA, expectedModel);

        SortCommand commandD = prepareCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_DESCENDING_WORD);
        expectedModel.updateSortedFilteredPersonList(null); //reset
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (1));
        assertCommandSuccess(commandD, model, expectedMessageD, expectedModel);
    }

    @Test
    public void execute_validPrefix_success() {
        String expectedMessageA = String
                .format(MESSAGE_SORT_SUCCESS, PREFIX_NAME.getPrefix(), ARGUMENT_ASCENDING_WORD);
        String expectedMessageD = String
                .format(MESSAGE_SORT_SUCCESS, PREFIX_PHONE.getPrefix(), ARGUMENT_DESCENDING_WORD);

        //ascending name (alphanumeric)
        String testedPrefix = PREFIX_NAME.getPrefix();
        SortCommand commandA = prepareCommand(testedPrefix, ARGUMENT_ASCENDING_WORD);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (person1.getName()
                .fullName.compareToIgnoreCase(person2.getName().fullName)));
        assertCommandSuccess(commandA, model, expectedMessageA, expectedModel);

        //descending phone (digits)
        testedPrefix = PREFIX_PHONE.getPrefix();
        SortCommand commandD = prepareCommand(testedPrefix, ARGUMENT_DESCENDING_WORD);
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (person2.getPhone()
                .value.compareToIgnoreCase(person1.getPhone().value)));
        assertCommandSuccess(commandD, model, expectedMessageD, expectedModel);

        //ascending address
        testedPrefix = PREFIX_ADDRESS.getPrefix();
        expectedMessageA = String.format(MESSAGE_SORT_SUCCESS, testedPrefix, ARGUMENT_ASCENDING_WORD);
        commandA = prepareCommand(testedPrefix, ARGUMENT_ASCENDING_WORD);
        expectedModel.updateSortedFilteredPersonList((person1, person2) -> (person1.getAddress()
                .value.compareToIgnoreCase(person2.getAddress().value)));
        assertCommandSuccess(commandA, model, expectedMessageA, expectedModel);
    }

    /**
     * Parses the prefix and order string and returns a SortCommand.
     */
    public SortCommand prepareCommand(String prefix, String order) {
        SortCommand sortCommand = new SortCommand(prefix, order);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String
            .format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidPrefix_failure() {
        //not a prefix
        assertParseFailure(parser, "notAPrefix", MESSAGE_INVALID_FORMAT);

        //a prefix, but not a sortable one
        assertParseFailure(parser, PREFIX_AVATAR.getPrefix(), MESSAGE_INVALID_FORMAT);

        //valid order but bad prefix
        assertParseFailure(parser, "notAPrefix " + ARGUMENT_ASCENDING_WORD, MESSAGE_INVALID_FORMAT);

        //no space
        assertParseFailure(parser, "n/asc", MESSAGE_INVALID_FORMAT);

        //prefix and order are in the wrong order
        assertParseFailure(parser, "asc n/", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidOrder_failure() {
        //not an order
        assertParseFailure(parser, "n/ notOrder", MESSAGE_INVALID_FORMAT);

        //two valid prefixes
        assertParseFailure(parser, PREFIX_NAME.getPrefix()
                + " " + PREFIX_PHONE.getPrefix(), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_success() {
        //no arguments
        assertParseSuccess(parser, "", new SortCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_ASCENDING_WORD));

        //just order
        assertParseSuccess(parser, ARGUMENT_DESCENDING_WORD,
                new SortCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_DESCENDING_WORD));

        //just prefix
        assertParseSuccess(parser, PREFIX_EMAIL.getPrefix(),
                new SortCommand(PREFIX_EMAIL.getPrefix(), ARGUMENT_ASCENDING_WORD));

        //valid prefix and order
        assertParseSuccess(parser, PREFIX_NAME.getPrefix() + " " + ARGUMENT_ASCENDING_WORD,
                new SortCommand(PREFIX_NAME.getPrefix(), ARGUMENT_ASCENDING_WORD));

        //more than 2 arguments, only take the first 2 and ignore the rest
        assertParseSuccess(parser, PREFIX_PHONE.getPrefix() + " " + ARGUMENT_DESCENDING_WORD
                + " some other uselessarguments", new SortCommand(PREFIX_PHONE.getPrefix(), ARGUMENT_DESCENDING_WORD));
    }
}
```
###### \java\seedu\address\model\person\TagListContainsKeywordsPredicateTest.java
``` java
public class TagListContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagListContainsKeywordsPredicate firstPredicate =
                new TagListContainsKeywordsPredicate(firstPredicateKeywordList);
        TagListContainsKeywordsPredicate  secondPredicate =
                new TagListContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagListContainsKeywordsPredicate firstPredicateCopy =
                new TagListContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagListContainsKeywords_returnsTrue() {
        // One keyword
        TagListContainsKeywordsPredicate predicate =
                new TagListContainsKeywordsPredicate(Collections.singletonList("Friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend", "Family").build()));

        // Multiple keywords
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Friend", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend", "Family").build()));

        // Only one matching keyword
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Family", "Wife"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Family", "Uncle").build()));

        // Mixed-case keywords
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("faMIly", "FAmiLy"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Family").build()));

        // Partial Match (one of the words starts with one of the keywords)
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Frien", "Fam"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend", "Family").build()));
    }

    @Test
    public void test_tagListDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagListContainsKeywordsPredicate predicate = new TagListContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // Non-matching keyword
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Friends", "Teammates").build()));

        // Keywords match name, phone, email and address, but does not match tags
        predicate = new TagListContainsKeywordsPredicate(Arrays.asList("Ben", "123", "al@mail.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Ben").withPhone("123")
                .withEmail("al@mail.com").withAddress("Main Street").withTags("Friends").build()));
    }
}
```