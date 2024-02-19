package lepimond.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nTests {

    @Test
    public void getMessage() {
        String result = I18n.getMessage("no_such_command");
        Assertions.assertEquals("Нет такой команды!", result);
    }

    @Test
    public void getLocale() {
        Locale result = I18n.getLocale();
        Assertions.assertEquals(Locale.getDefault(), result);
    }
}
