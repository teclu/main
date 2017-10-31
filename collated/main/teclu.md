# teclu
###### \java\seedu\address\commons\util\AvatarUtil.java
``` java
/**
 * A class to draw the default placeholder avatar.
 */
public class AvatarUtil {
    private static final BufferedImage placeholder =
            new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_INDEXED);

    public AvatarUtil() {
        drawPlaceholderAvatar();
    }

    /**
     * This method draws the default placeholder avatar.
     */
    private void drawPlaceholderAvatar() {
        Graphics2D g2d = placeholder.createGraphics();
        g2d.setColor(Color.lightGray); // Backdrop
        g2d.fillRect(1, 1, 198, 198);
        g2d.setColor(Color.gray); // Body
        g2d.fillOval(25, 135, 155, 150);
        g2d.setColor(Color.darkGray); // Head
        g2d.fillOval(55, 35, 95, 95);
        g2d.fillOval(55, 35, 95, 120);
        g2d.setColor(Color.white); // Border Frame
        g2d.drawRect(0, 0, 199, 199);
        g2d.dispose();
    }

    public BufferedImage getPlaceholderAvatar() {
        return placeholder;
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> avatar} into an {@code Optional<Avatar>} if {@code avatar} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Avatar> parseAvatar(Optional<String> avatar) throws IllegalValueException {
        requireNonNull(avatar);
        return avatar.isPresent() ? Optional.of(new Avatar(avatar.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\avatar\Avatar.java
``` java
/**
 * Represents a Person's avatar in the address book.
 * Guarantees: immutable.
 */
public class Avatar {
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar should be a valid online URL or local path.";
    public final String value;
    private URL url;
    private BufferedImage image;

    /**
     * Sets a default placeholder avatar for new contacts being added.
     */
    public Avatar() throws IllegalValueException {
        AvatarUtil placeholder = new AvatarUtil();
        image = placeholder.getPlaceholderAvatar();
        value = "";
    }

    /**
     * Validates given avatar URL string and saves it in the data directory.
     *
     * @throws IllegalValueException if given URL string is invalid.
     */
    public Avatar(String url) throws IllegalValueException {
        try {
            if (url.isEmpty()) {
                AvatarUtil placeholder = new AvatarUtil();
                image = placeholder.getPlaceholderAvatar();
                value = "";
            } else {
                File defaultAvatar = new File(url);

                if (isValidUrl(url)) {
                    this.url = new URL(url);
                } else {
                    this.url = defaultAvatar.toURI().toURL();
                }
                this.image = ImageIO.read(this.url);

                if (!isSavedInData(url)) {
                    String outputName = "/data/" + this.url.hashCode() + ".png";
                    File outputImage = new File(System.getProperty("user.dir") + outputName);

                    File parentDirectory = outputImage.getParentFile();
                    if (!parentDirectory.exists()) {
                        parentDirectory.mkdirs();
                    }

                    ImageIO.write(this.image, "png", outputImage);
                    this.url = outputImage.toURI().toURL();
                }
                this.value = this.url.toString();
            }
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid url.
     */
    public static boolean isValidUrl(String url) {
        return (url.contains("https://") || url.contains("http://") || url.contains("file:/"));
    }

    /**
     * Returns true if a given valid url is already found in data folder.
     */
    public static boolean isSavedInData(String url) {
        return (url.contains("file:/") && url.contains("data/"));
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setAvatar(Avatar avatar) {
        this.avatar.set(requireNonNull(avatar));
    }

    @Override
    public ObjectProperty<Avatar> avatarProperty() {
        return avatar;
    }

    @Override
    public Avatar getAvatar() {
        return avatar.get();
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
    /**
     * Assign a random colour to the tag.
     * @return A random colour hex code.
     */
    private String assignTagColour() {
        TagColours palette = new TagColours();
        String[] tagColours = palette.getTagColours();
        int randomIndex = colourSelect.nextInt(tagColours.length);

        return tagColours[randomIndex];
    }
```
###### \java\seedu\address\ui\AvatarWindow.java
``` java
/**
 * Controller for avatar page
 */
public class AvatarWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(AvatarWindow.class);
    private static final String ICON = "/images/avatarPlaceholder.png";
    private static final String FXML = "AvatarWindow.fxml";
    private static final String TITLE = "Avatar Options";

    @FXML
    private ImageView avatarW;
    private PersonCard selectedPersonCard;
    private String url;

    private final Logic logic;
    private final Stage dialogStage;

    public AvatarWindow(PersonCard selectedPersonCard, Logic logic) {
        super(FXML);

        this.logic = logic;
        this.selectedPersonCard = selectedPersonCard;

        Scene scene = new Scene(getRoot());

        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(false);
        dialogStage.setHeight(325);
        dialogStage.setWidth(250);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        avatarW.setImage(SwingFXUtils.toFXImage(selectedPersonCard.person.getAvatar().getImage(), null));
        show();
    }

    /**
     * Shows the avatar window.
     */
    public void show() {
        logger.fine("Showing avatar window.");
        dialogStage.showAndWait();
    }

    /**
     * Opens the file dialog window upon clicking the load button.
     */
    @FXML
    private void loadPrompt() throws IOException {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJpg = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJpg, extFilterPng);

        File file = fileChooser.showOpenDialog(null);

        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        avatarW.setImage(image);
        url = file.getPath();
    }

    /**
     * Saves the avatar upon clicking the save button by executing an edit command.
     */
    @FXML
    private void savePrompt() {
        int displayedIndex = selectedPersonCard.getDisplayedIndex();

        try {
            CommandResult commandResult = logic.execute("edit " + displayedIndex + " v/" + url);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
            dialogStage.close();
        } catch (CommandException | ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }
}
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
/**
 * The Person Panel of the App, which displays the contact details of a selected person.
 */
public class PersonPanel extends UiPart<Region> {
    private static final String FXML = "PersonPanel.fxml";
    private final Logic logic;
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private PersonCard selectedPersonCard;
    private ReadOnlyPerson selectedPerson;
    private boolean isBlankPage = true;

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
    @FXML
    private ImageView avatar;

    public PersonPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        registerAsAnEventHandler(this);
        loadBlankPersonPage();
    }

    /**
     * Loads a blank page when no contact is selected.
     */
    private void loadBlankPersonPage() {
        name.setText("No contact selected");
        phone.setText("Phone: -");
        address.setText("Address: -");
        email.setText("Email: -");
        birthday.setText("Birthday: -");
        avatar.setImage(null);
        tags.getChildren().clear();
    }

    /**
     * Loads the selected person's contact details.
     */
    private void loadPersonPage() {
        name.setText(selectedPerson.getName().fullName);
        phone.setText("Phone: " + selectedPerson.getPhone().toString());
        address.setText("Address: " + selectedPerson.getAddress().toString());
        email.setText("Email: " + selectedPerson.getEmail().toString());
        birthday.setText("Birthday: " + selectedPerson.getBirthday().toString());
        avatar.setImage(SwingFXUtils.toFXImage(selectedPerson.getAvatar().getImage(), null));
        selectedPerson.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + tag.tagColour);
            tags.getChildren().add(tagLabel);
        });
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        loadBlankPersonPage();
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectedPersonCard = event.getNewSelection();
        selectedPerson = selectedPersonCard.person;
        isBlankPage = false;
        loadPersonPage();
    }

    @Subscribe
    private void handlePersonPanelNoSelectionEvent(PersonPanelNoSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        isBlankPage = true;
        loadBlankPersonPage();
    }

    /**
     * Clicking the Avatar will trigger a GUI option of uploading pictures.
     * Note: Implementation is not complete; may or may not use this.
     */
    @FXML
    private void avatarPrompt() {
        if (!isBlankPage) {
            new AvatarWindow(selectedPersonCard, logic);
        }
    }
}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
        if (event.isError) {
            setCommandFailureStyle();
        } else {
            setDefaultStyle();
        }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    /**
     * Sets {@code ResultDisplay} style to the default style.
     */
    private void setDefaultStyle() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    /**
     * Sets {@code ResultDisplay} style to the fail-state style.
     */
    private void setCommandFailureStyle() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) { // Previous command was also invalid (failure)
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    /**
     * Sets the total number of contacts.
     */
    private void setNumTotalContacts(int numTotalContacts) {
        this.numTotalContacts.setText("Total: " + numTotalContacts + " contact(s)");
    }
```
###### \java\seedu\address\ui\TagColours.java
``` java
/**
 * A UI feature for each unique tag to have its own colour.
 */
public class TagColours {
    private static final String RED = "#CB4335";
    private static final String DARK_RED = "#911205";
    private static final String ORANGE = "#F39C12";
    private static final String LIGHT_ORANGE = "#FFAA23";
    private static final String YELLOW = "#F4D03F";
    private static final String GREEN = "#27AE60";
    private static final String DARK_GREEN = "#006D36";
    private static final String BLUE = "#2980B9";
    private static final String NAVY_BLUE = "#095282";
    private static final String INDIGO = "#8E44AD";
    private static final String VIOLET = "#DB2B6F";
    private static final String BLACK = "#000000";
    private static final String HOT_PINK = "#FF0062";

    /**
     * Returns a String array of available tag colours.
     */
    public String[] getTagColours() {
        return new String[] { RED, DARK_RED, ORANGE, LIGHT_ORANGE, YELLOW,
                                GREEN, DARK_GREEN, BLUE, NAVY_BLUE, INDIGO, VIOLET, HOT_PINK, BLACK };
    }
}
```
###### \resources\view\AvatarWindow.fxml
``` fxml
<StackPane fx:id="avatarWindowRoot" prefHeight="286.0" prefWidth="250.0" xmlns="http://javafx.com/javafx/9" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <TitledPane animated="false" prefHeight="54.0" prefWidth="250.0" text="Options" StackPane.alignment="BOTTOM_CENTER">
        <content>
          <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="180.0" prefWidth="200.0">
               <children>
                  <Button fx:id="avatarLoad" layoutY="2.0" mnemonicParsing="false" onAction="#loadPrompt" text="Load" />
                  <Button fx:id="avatarSave" layoutX="49.0" layoutY="2.0" mnemonicParsing="false" onAction="#savePrompt" text="Save" />
               </children>
            </AnchorPane>
        </content>
      </TitledPane>
       <ImageView fx:id="avatarW" fitHeight="200.0" fitWidth="200.0" pickOnBounds="true" preserveRatio="true" StackPane.alignment="TOP_CENTER">
         <effect>
            <DropShadow />
         </effect>
         <StackPane.margin>
            <Insets top="15.0" />
         </StackPane.margin>
      </ImageView>
   </children>
</StackPane>
```
###### \resources\view\PersonListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15" />
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                    <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
            </HBox>
            <FlowPane fx:id="tags" />
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\PersonPanel.fxml
``` fxml
<SplitPane fx:id="personPanel" dividerPositions="0.35" orientation="VERTICAL" prefHeight="310.0" prefWidth="900.0"
           xmlns="http://javafx.com/javafx/9" xmlns:fx="http://javafx.com/fxml/1">
    <items>
        <AnchorPane fx:id="primaryDetails">
            <children>
            <HBox prefHeight="150.0" prefWidth="898.0">
               <children>
                  <ImageView fx:id="avatar" fitHeight="150.0" fitWidth="150.0" onMouseClicked="#avatarPrompt"
                             pickOnBounds="true" preserveRatio="true">
                     <cursor>
                        <Cursor fx:constant="OPEN_HAND" />
                     </cursor>
                  </ImageView>
                  <Region prefHeight="150.0" prefWidth="20.0" />
                      <VBox prefHeight="150.0" prefWidth="729.0">
                          <children>
                              <Label fx:id="name" prefHeight="28.0" prefWidth="756.0" wrapText="true">
                                  <padding>
                                      <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                  </padding>
                              </Label>
                              <FlowPane fx:id="tags" alignment="CENTER_LEFT">
                                  <padding>
                                      <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                  </padding>
                                  </FlowPane>
                          </children>
                      </VBox>
               </children>
            </HBox>
            </children>
        </AnchorPane>

        <AnchorPane fx:id="secondaryDetails">
            <children>
            <HBox prefHeight="181.0" prefWidth="898.0">
               <children>
                  <VBox prefHeight="200.0" prefWidth="37.0">
                     <children>
                        <Region prefHeight="20.0" prefWidth="49.0" />
                        <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                           <image>
                              <Image url="@../images/addressWhite.png" />
                           </image>
                        </ImageView>
                        <Region prefHeight="20.0" prefWidth="49.0" />
                        <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                           <image>
                              <Image url="@../images/emailWhite.png" />
                           </image>
                        </ImageView>
                        <Region prefHeight="20.0" prefWidth="49.0" />
                        <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                           <image>
                              <Image url="@../images/phoneWhite.png" />
                           </image>
                        </ImageView>
                        <Region prefHeight="20.0" prefWidth="49.0" />
                        <ImageView fitHeight="24.0" fitWidth="24.0" pickOnBounds="true" preserveRatio="true">
                           <image>
                              <Image url="@../images/birthdayWhite.png" />
                           </image>
                        </ImageView>
                     </children>
                  </VBox>
                      <VBox prefHeight="150.0" prefWidth="900.0">
                          <children>
                        <Region prefHeight="27.0" prefWidth="849.0" />
                              <Label fx:id="address" prefHeight="50.0" prefWidth="850.0" wrapText="true">
                                  <padding>
                                      <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                  </padding>
                              </Label>
                        <Region prefHeight="20.0" prefWidth="49.0" />
                              <Label fx:id="email" prefHeight="50.0" prefWidth="851.0" wrapText="true">
                                  <padding>
                                      <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                  </padding>
                              </Label>
                        <Region prefHeight="20.0" prefWidth="49.0" />
                              <Label fx:id="phone" prefHeight="50.0" prefWidth="847.0" wrapText="true">
                                  <padding>
                                      <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                  </padding>
                              </Label>
                        <Region prefHeight="20.0" prefWidth="49.0" />
                              <Label fx:id="birthday" prefHeight="50.0" prefWidth="848.0" wrapText="true">
                                  <padding>
                                      <Insets bottom="5.0" left="10.0" right="5.0" top="5.0" />
                                  </padding>
                              </Label>
                          </children>
                      </VBox>
               </children>
            </HBox>
            </children>
        </AnchorPane>
    </items>
</SplitPane>
```
