package seedu.address.model.person;

import org.junit.Test;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    }
}
