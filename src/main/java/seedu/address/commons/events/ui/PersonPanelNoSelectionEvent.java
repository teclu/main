package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.ui.PersonCard;

import java.util.Set;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelNoSelectionEvent extends BaseEvent {

    public PersonPanelNoSelectionEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
