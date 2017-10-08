package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;

/**
 * The Person Panel of the App, which displays the contact details of a selected person.
 */
public class PersonPanel extends UiPart<Region> {
    private static final String FXML = "PersonPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private VBox panel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText("Phone: " + person.getPhone().toString());
        address.setText("Address: " + person.getAddress().toString());
        email.setText("Email: " + person.getEmail().toString());
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
