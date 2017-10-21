package seedu.address.model.person.avatar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's avatar in the address book.
 * Guarantees: N/A
 */

public class Avatar {
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar should be a valid online URL or local path.";
    public static final String DEFAULT_PATH = "src/main/java/seedu/address/model/person/avatar/avatarPlaceholders/";
    public final String value;
    private URL url;
    private BufferedImage image;

    /**
     * Sets a default placeholder avatar for new contacts being added.
     */
    public Avatar() throws IllegalValueException {
        try {
            File defaultAvatar = new File(DEFAULT_PATH + "1.png");
            this.url = defaultAvatar.toURI().toURL();
            this.image = ImageIO.read(this.url);
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
        this.value = DEFAULT_PATH + "1.png";
    }

    /**
     * Validates given avatar URL string.
     *
     * @throws IllegalValueException if given URL string is invalid.
     */
    public Avatar(String url) throws IllegalValueException {
        try {
            if (url.isEmpty()) {
                File defaultAvatar = new File(DEFAULT_PATH + "1.png");
                this.url = defaultAvatar.toURI().toURL();
                this.image = ImageIO.read(this.url);
                this.value = DEFAULT_PATH + "1.png";
            } else {
                File defaultAvatar = new File(url);
                if (url.contains("https://") || url.contains("http://")) {
                    this.url = new URL(url);
                } else {
                    this.url = defaultAvatar.toURI().toURL();
                }
                this.image = ImageIO.read(this.url);
                this.value = url;
            }
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    public URL getUrl() {
        return url;
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
