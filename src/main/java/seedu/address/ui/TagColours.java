package seedu.address.ui;

//@@author teclu
/**
 * A UI feature for each unique tag to have its own colour.
 */
public class TagColours {
    public static final String RED = "#CB4335";
    public static final String DARK_RED = "#911205";
    public static final String ORANGE = "#F39C12";
    public static final String LIGHT_ORANGE = "#FFAA23";
    public static final String YELLOW = "#F4D03F";
    public static final String GREEN = "#27AE60";
    public static final String DARK_GREEN = "#006D36";
    public static final String BLUE = "#2980B9";
    public static final String NAVY_BLUE = "#095282";
    public static final String INDIGO = "#8E44AD";
    public static final String VIOLET = "#DB2B6F";
    public static final String BLACK = "#000000";
    public static final String HOT_PINK = "#FF0062";

    private TagColours() { }

    /**
     * Returns a String array of available tag colours.
     */
    public static String[] getTagColours() {
        return new String[] { RED, DARK_RED, ORANGE, LIGHT_ORANGE, YELLOW,
                                GREEN, DARK_GREEN, BLUE, NAVY_BLUE, INDIGO, VIOLET, HOT_PINK, BLACK };
    }
}
