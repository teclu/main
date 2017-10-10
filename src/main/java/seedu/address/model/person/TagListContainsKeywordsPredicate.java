package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
    
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code UniqueTagList} has a {@code Tag} that matches any of the keywords given.
 */
public class TagListContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagListContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


}
