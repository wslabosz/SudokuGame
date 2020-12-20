package sudoku;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;

public class ResourceController {
    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(Locale.getAvailableLocales()));
    }

    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static ObservableValue<Locale> getValueLocale() {
        return localeProperty();
    }

    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }

    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    public static String get(String resBundle, String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle(resBundle, getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }

    public static StringBinding createStringBinding(String resBundle, final String key, Object... args) {
        return Bindings.createStringBinding(() -> get(resBundle, key, args), locale);
    }

    public static void KURWA(List<String> lista, ObservableList<String> observ) {
        Bindings.bindContent(lista, observ);
    }

    public static StringBinding createStringBinding(Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }

    public static Label labelForValue(Callable<String> func) {
        Label label = new Label();
        label.textProperty().bind(createStringBinding(func));
        return label;
    }

    public static Button buttonForKey(String resBundle, final String key, final Object... args) {
        Button button = new Button();
        button.textProperty().bind(createStringBinding(resBundle, key, args));
        return button;
    }
}
