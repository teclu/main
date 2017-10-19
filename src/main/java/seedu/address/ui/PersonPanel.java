package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

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
    private Label birthday;
    @FXML
    private FlowPane tags;

    public PersonPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the person's contact details.
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText("Phone: " + person.getPhone().toString());
        address.setText("Address: " + person.getAddress().toString());
        email.setText("Email: " + person.getEmail().toString());
        birthday.setText("Birthday: " + person.getBirthday().toString());
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + tag.tagColour);
            tags.getChildren().add(tagLabel);
        });
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
