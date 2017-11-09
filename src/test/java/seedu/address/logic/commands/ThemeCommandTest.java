package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author teclu
public class ThemeCommandTest {

    @Test
    public void equals() {
        ThemeCommand[] listThemeCommands = new ThemeCommand[5];
        String[] listThemes = { "Light", "Dark", "Red", "Blue", "Green" };
        for (int i = 0; i < 5; i++) {
            listThemeCommands[i] = new ThemeCommand(listThemes[i]);
        }

        // same object -> returns true
        for (int i = 0; i < 5; i++) {
            assertTrue(listThemeCommands[i].equals(new ThemeCommand(listThemes[i])));
        }

        // different types -> returns false
        for (int i = 0; i < 5; i++) {
            assertFalse(listThemeCommands[i].equals(1));
        }

        // null -> returns false
        for (int i = 0; i < 5; i++) {
            assertFalse(listThemeCommands[i].equals(null));
        }

        // different theme -> returns false
        int j = 4;

        for (int i = 0; i < 5; i++) {
            if (i != j) {
                assertFalse(listThemeCommands[i].equals(listThemeCommands[j]));
            }
            j--;
        }
    }

}
