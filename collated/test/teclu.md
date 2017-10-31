# teclu
###### \java\guitests\guihandles\PersonPanelHandle.java
``` java
/**
 * Provides a handle to the Person Panel.
 */
public class PersonPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_PANEL_ID = "#personPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String TAGS_FIELD_ID = "#tags";

    private Label name;
    private Label phone;
    private Label address;
    private Label email;
    private Label birthday;
    private Label avatar;
    private List<Label> tagLabels;

    public PersonPanelHandle(Node personPanelNode) {
        super(personPanelNode);

        this.name = getChildNode(NAME_FIELD_ID);
        this.phone = getChildNode(PHONE_FIELD_ID);
        this.address = getChildNode(ADDRESS_FIELD_ID);
        this.email = getChildNode(EMAIL_FIELD_ID);
        this.birthday = getChildNode(BIRTHDAY_FIELD_ID);

        updateTags();
    }

    public String getName() {
        return name.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getBirthday() {
        return birthday.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Updates the tags of the person.
     */
    public void updateTags() {
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);

        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }
}
```
###### \java\seedu\address\model\person\AvatarTest.java
``` java
public class AvatarTest {

    @Test
    public void isValidUrl() {
        // Invalid Avatar URLs
        assertFalse(Avatar.isValidUrl("")); // Empty string
        assertFalse(Avatar.isValidUrl("i.imgur.com/jNNT4LE.jpg")); // Missing http(s)://
        assertFalse(Avatar.isValidUrl("C:/Users/Bob/AddressBook/data/-1552827182.png")); // Missing file:/

        // Valid Avatar URLs
        assertTrue(Avatar.isValidUrl("https://i.imgur.com/jNNT4LE.jpg"));
        assertTrue(Avatar.isValidUrl("file:/C:/Users/Bob/AddressBook/data/-1552827182.png"));
    }

    @Test
    public void isSavedInData() {
        // Invalid Avatar Saved URLs
        assertFalse(Avatar.isSavedInData("")); // Empty string
        assertFalse(Avatar.isSavedInData("https://i.imgur.com/jNNT4LE.jpg")); // Online URL
        assertFalse(Avatar.isSavedInData("C:/Users/Bob/pictures/Alice.png")); // Local URL
        assertFalse(Avatar.isSavedInData("file:/C:/Users/Bob/pictures/Alice.png")); // Not in Data Folder

        // Valid Avatar Saved URLs
        assertTrue(Avatar.isSavedInData("file:/C:/Users/Bob/AddressBook/data/-1552827182.png"));
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Avatar} of the {@code Person} that we are building.
     */
    public PersonBuilder withAvatar() {
        try {
            this.person.setAvatar(new Avatar());
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("avatar is expected to be valid.");
        }
        return this;
    }

    /**
     * Sets the {@code Avatar} of the {@code Person} that we are building.
     */
    public PersonBuilder withAvatar(String avatar) {
        try {
            this.person.setAvatar(new Avatar(avatar));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("avatar is expected to be valid.");
        }
        return this;
    }
```
###### \java\seedu\address\ui\PersonPanelTest.java
``` java
public class PersonPanelTest extends GuiUnitTest {

    private PersonPanel personPanel;
    private PersonPanelHandle personPanelHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        guiRobot.interact(() -> personPanel = new PersonPanel(logic));
        uiPartRule.setUiPart(personPanel);
        personPanelHandle = new PersonPanelHandle(personPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPersonIsDisplayed(ALICE, personPanelHandle);
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertPersonIsDisplayed(BOB, personPanelHandle);
    }

    /**
     * Asserts that {@code personPanelHandle} displays the details of {@code expectedPerson} correctly
     */
    private void assertPersonIsDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        guiRobot.pauseForHuman();
        assertEquals(expectedPerson.getName().toString(), personPanelHandle.getName());
        assertEquals("Phone: " + expectedPerson.getPhone().toString(), personPanelHandle.getPhone());
        assertEquals("Email: " + expectedPerson.getEmail().toString(), personPanelHandle.getEmail());
        assertEquals("Address: " + expectedPerson.getAddress().toString(), personPanelHandle.getAddress());
        assertEquals("Birthday: " + expectedPerson.getBirthday().toString(), personPanelHandle.getBirthday());
        personPanelHandle.updateTags();
        assertTagsAreDisplayed(expectedPerson, personPanelHandle);
    }

    /*
     * Asserts that {@code personPanelHandle} displays the tags of {@code expectedPerson} correctly
     */
    private void assertTagsAreDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                personPanelHandle.getTags());
    }
}
```
