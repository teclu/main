package seedu.address.model.person.avatar;

import static java.util.Objects.requireNonNull;

import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;

import javafx.scene.image.Image;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.OsCheckUtil;

/**
 * Represents a Person's avatar in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAvatar(String)}
 */

public class Avatar extends Applet {
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar should be a valid online URL or local path.";
    public static final String OPERATING_SYSTEM = new OsCheckUtil().getOsName();
    public final String value;
    private URL url;
    private Image image;

    /**
     * Sets a default placeholder avatar for new contacts being added.
     */
    public Avatar() throws IllegalValueException {
        String defaultPath = "src/main/java/seedu/address/model/person/avatar/avatarPlaceholders/";

        if (OPERATING_SYSTEM == "unix" || OPERATING_SYSTEM == "mac") {
            File homeDirectory = new File(System.getProperty("user.home"));
            File fileToRead = new File(homeDirectory, defaultPath);
            try {
                defaultPath = fileToRead.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
            }
        }

        try {
            this.url = new File(defaultPath + "1.png").toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
        try {
            this.image = SwingFXUtils.toFXImage(ImageIO.read(this.url), null);
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
        this.value = this.url.toString();
    }

    /**
     * Validates given avatar URL string.
     *
     * @throws IllegalValueException if given URL string is invalid.
     */
    public Avatar(String url) throws IllegalValueException {
        requireNonNull(url);
        String trimmedUrl = url.trim();
        try {
            this.url = new URL(trimmedUrl);
        } catch (MalformedURLException e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
        try {
            this.image = SwingFXUtils.toFXImage(ImageIO.read(this.url), null);
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
        this.value = trimmedUrl.toString();
    }

    /**
     * Returns true if a given URL string is valid. (Incomplete)
     */
    public static boolean isValidAvatar(String test) {
        try {
            URL urlCheck = new URL(test);
            HttpURLConnection connection = (HttpURLConnection) urlCheck.openConnection();
            connection.setRequestMethod("HEAD");
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public URL getUrl() {
        return url;
    }

    public Image getImage() {
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
