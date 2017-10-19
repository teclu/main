package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddTagCommand.
 */

public class AddTagCommandTest {
    
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    
    
    /*
     * Returns an {@code AddTagCommand}.
     */
    
    
}
