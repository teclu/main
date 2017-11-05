# k-l-a
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the sentence contains the word
     *  Ignores case, and a full word match is not required
     *   <br>examples:<pre>
     *       containsWordPartialIgnoreCase("ABc def", "abc") == true
     *       containsWordPartialIgnoreCase("ABc def", "DEF") == true
     *       containsWordPartialIgnoreCase("ABc def", "AB") == true //  query partially match sentence
     *       containsWordPartialIgnoreCase("ABc def", "AbCD") == false // sentence only partially match query
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordPartialIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        return sentence.toLowerCase().contains(preppedWord);
    }

    /**
     * Returns the earliest (starting) position in the sentence that matches with at least one of the keywords in words,
     * or -1 if there are no matches.
     * Ignores case, and a full word match is not required
     * example : Say listOfWords is a List that contains the strings "Mat" and "Sen"
     *      earliestIndexOf("tomatch", listOfWords) --> returns 2, matching "Mat"
     *      earliestIndexOf("sentenceMatch", listOfWords) --> returns 0, matches both but "Sen" is matched at the start
     *      earliestIndexOf("notCorrect", listOfWords) --> returns -1, no match
     * @param sentence
     * @param words
     * @return
     */
    public static int earliestIndexOf(String sentence, List<String> words) {
        requireNonNull(sentence);
        requireNonNull(words);

        String[] preppedWords = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            String preppedWord = words.get(i).trim();
            checkArgument(!preppedWord.isEmpty(), "Word parameter cannot contain an empty string");
            checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");
            preppedWords[i] = preppedWord;
        }

        int earliestIndex = -1;
        for (String keyword: preppedWords) {
            int index = sentence.toLowerCase().indexOf(keyword);
            if (index == 0) {
                return index;
            } else if (earliestIndex < 0 || (index >= 0 && index < earliestIndex)) {
                earliestIndex = index;
            }
        }

        return earliestIndex;
    }
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts the current list according to the given arguments.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String ARGUMENT_ASCENDING_WORD = "asc";
    public static final String ARGUMENT_DESCENDING_WORD = "des";
    public static final String ARGUMENT_DEFAULT_ORDER = "default";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the current list according to given prefix in the given order. "
            + "No prefix results in default order.\n"
            + "Parameters: [PREFIX] [ORDER]\n"
            + "Example : sort n/ asc";
    public static final String MESSAGE_SORT_SUCCESS = "Sorted current list by %1$s in %2$s order.";

    public final String prefix;
    public final String order;

    public SortCommand(String prefix, String order) {
        this.prefix = prefix;
        this.order = order;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Comparator<ReadOnlyPerson> sortOrder = createAscComparator(prefix, order);
        model.updateSortedFilteredPersonList(null); //reset any order first
        model.updateSortedFilteredPersonList(sortOrder);
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, prefix, order));
    }

    /**
     *  Creates a ReadOnlyPerson Comparator from the given prefix and ordering.
     */
    public Comparator<ReadOnlyPerson> createAscComparator(String prefix, String order) {
        if (prefix.equals(ARGUMENT_DEFAULT_ORDER)) {
            if (ARGUMENT_DESCENDING_WORD.equals(order)) {
                return (person1, person2) -> (1); //reverse order
            } else if (ARGUMENT_ASCENDING_WORD.equals(order)) {
                return (person1, person2) -> (-1); //default order
            }
        }

        Comparator<ReadOnlyPerson> sortOrderComp = null;
        if (prefix.equals(PREFIX_NAME.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getName()
                    .fullName.compareToIgnoreCase(person2.getName().fullName));
        } else if (prefix.equals(PREFIX_PHONE.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getPhone()
                    .value.compareToIgnoreCase(person2.getPhone().value));
        } else if (prefix.equals(PREFIX_EMAIL.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getEmail()
                    .value.compareToIgnoreCase(person2.getEmail().value));
        } else if (prefix.equals(PREFIX_ADDRESS.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getAddress()
                    .value.compareToIgnoreCase(person2.getAddress().value));
        } else if (prefix.equals(PREFIX_BIRTHDAY.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getBirthday()
                    .value.compareToIgnoreCase(person2.getBirthday().value));
        }

        if (ARGUMENT_DESCENDING_WORD.equals(order)) {
            requireNonNull(sortOrderComp);
            sortOrderComp = sortOrderComp.reversed();
        }
        return sortOrderComp;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SortCommand
                && this.prefix.equals(((SortCommand) other).prefix)
                && this.order.equals(((SortCommand) other).order));


    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || isSearchablePrefix(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        String toSearch = keywords[0];
        if (startsWithSearchablePrefix(toSearch)) {
            String extractedKeyword = toSearch.substring(2, toSearch.length());
            toSearch = toSearch.substring(0, 2);
            if (!extractedKeyword.isEmpty()) {
                keywords[0] = extractedKeyword;
            } else {
                keywords = Arrays.copyOfRange(keywords, 1, keywords.length);
            }
        }

        if (toSearch.equals(PREFIX_TAG.getPrefix())) {
            return new FindCommand(new TagListContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (toSearch.equals(PREFIX_PHONE.getPrefix())) {
            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (toSearch.equals(PREFIX_EMAIL.getPrefix())) {
            return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (toSearch.equals(PREFIX_ADDRESS.getPrefix())) {
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (toSearch.equals(PREFIX_BIRTHDAY.getPrefix())) {
            return new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else {
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
    }

    /**
     * Checks if the given string prefix is a searchable prefix (n/, t/, p/, e/, a/, or b/).
     */
    public boolean isSearchablePrefix(String prefixString) {
        return prefixString.equals(PREFIX_NAME.getPrefix()) || prefixString.equals(PREFIX_TAG.getPrefix())
                || prefixString.equals(PREFIX_PHONE.getPrefix()) || prefixString.equals(PREFIX_EMAIL.getPrefix())
                || prefixString.equals(PREFIX_ADDRESS.getPrefix()) || prefixString.equals(PREFIX_BIRTHDAY.getPrefix());
    }

    /**
     * Checks if the given string prefix stars with a searchable prefix (n/, t/, p/, e/, a/, or b/).
     */
    public boolean startsWithSearchablePrefix(String prefixString) {
        if (prefixString.length() < 2) {
            return false;
        }
        String prefix = prefixString.substring(0, 2);
        return isSearchablePrefix(prefix);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new SortCommand(ARGUMENT_DEFAULT_ORDER, ARGUMENT_ASCENDING_WORD);
        } else if (isSortArgument(trimmedArgs)) {
            return new SortCommand(ARGUMENT_DEFAULT_ORDER, trimmedArgs);
        }

        String[] splitArgs = trimmedArgs.split("\\s+");
        String prefix = splitArgs[0];
        String order = ARGUMENT_ASCENDING_WORD;
        if (splitArgs.length > 1) {
            order = splitArgs[1];
        }
        if (isSortablePrefix(prefix) && isSortArgument(order)) {
            return new SortCommand(prefix, order);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

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
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the comparator of the sorted filtered person list to sort by the given comparator.
     * @param comparator
     */
    void updateSortedFilteredPersonList(Comparator<ReadOnlyPerson> comparator);

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateSortedFilteredPersonList(Comparator<ReadOnlyPerson> comparator) {
        sortedFilteredPersons.setComparator(comparator);
    }
```
###### \java\seedu\address\model\person\FieldContainsKeywordsPredicate.java
``` java
/**
 *  Represents a predicate for a field of a ReadOnlyPerson with the ability to test an instance of a ReadOnlyPerson.
 */
public abstract class FieldContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    protected List<String> keywords;
    protected String fieldToSearch;

    /**
     * Returns an immutable List of keywords that is used to evaluate a ReadOnlyPerson.
     */
    public List<String> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }

    /**
     * Returns the string prefix of the field evaluated by this predicate.
     */
    public String getFieldToSearch() {
        return fieldToSearch;
    }

    /**
     * Creates and returns a comparator based on the keywords and field searched of this predicate.
     */
    public abstract Comparator<ReadOnlyPerson> sortOrderComparator();

}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return Comparator.comparingInt(person -> StringUtil
                .earliestIndexOf(person.getName().fullName, keywords));
    }

}
```
###### \java\seedu\address\model\person\TagListContainsKeywordsPredicate.java
``` java
/**
 * Tests that a Person's Tag List contains at least one tag whose string matches any of the keywords given.
 */
public class TagListContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public TagListContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_TAG.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getTags().stream().anyMatch(tag -> testTag(tag));
    }

    /**
     * Returns true if a particular tag's string matches any of the keywords. For use of the test method above
     */
    private boolean testTag(Tag tag) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordPartialIgnoreCase(tag.tagName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagListContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagListContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return (person1, person2) -> (0); //no sorting for tag
    }
}
```
