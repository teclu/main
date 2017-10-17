package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a Person's Tag List contains at least one tag whose string matches any of the keywords given.
 */
public class TagListContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagListContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
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
                .anyMatch(keyword -> StringUtil.containsWordStartingWithIgnoreCase(tag.tagName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagListContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagListContainsKeywordsPredicate) other).keywords)); // state check
    }

}
