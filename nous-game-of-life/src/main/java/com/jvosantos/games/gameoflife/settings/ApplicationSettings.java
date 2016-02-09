package com.jvosantos.games.gameoflife.settings;

import com.jvosantos.games.gameoflife.engine.GameMode;
import com.jvosantos.games.gameoflife.engine.LifePatterns;
import com.jvosantos.games.gameoflife.ui.ConsoleInterface;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Contains the settings of the application that can be configured, either by a properties file or by command line arguments.
 * The defaults are the following:
 * <ul>
 * <li>max-generations: 2/li>
 * <li>endless: false</li>
 * <li>mode: ENDLESS</li>
 * <li>alive-character: '#'</li>
 * <li>dead-character: ' '</li>
 * <li>input-patern: BINARY</li>
 * <li>pattern-file: false</li>
 * <li>ms-between-generations: 500</li>
 * <li>pattern: NOUS_OSCILLATOR</li>
 * <ul>
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class ApplicationSettings {
    /**
     * Maximum number of generations to be generated.
     */
    public static final String MAX_GENERATIONS = "max-generations";
    /**
     * Flag indicating to never stop generating new generations.
     */
    public static final String ENDLESS = "endless";
    /**
     * Pattern to be used. Possible list of values defined in {@link com.jvosantos.games.gameoflife.engine.LifePatterns}
     */
    public static final String PATTERN = "pattern";
    /**
     * Mode to be used. Can be ENDLESS or CONSTRAINED indicating what implementation should be used, respectively {@link com.jvosantos.games.gameoflife.engine.GameOfLifeEndless} or {@link com.jvosantos.games.gameoflife.engine.GameOfLifeConstrained}
     */
    public static final String MODE = "mode";
    /**
     * Character to be used to represent live cells
     */
    public static final String ALIVE_CHARACTER = "alive-character";
    /**
     * Character to be used to represent dead cells
     */
    public static final String DEAD_CHARACTER = "dead-character";
    /**
     * Input pattern to be used when defining a file pattern. Currently BINARY is the only one supported.
     */
    public static final String INPUT_PATTERN = "input-pattern";
    /**
     * File name with initial pattern.
     */
    public static final String PATTERN_FILE = "pattern-file";
    /**
     * Number of milliseconds between generations.
     */
    public static final String MS_BETWEEN_GENERATIONS = "ms-between-generations";

    private static final String COMMAND_PREFIX = "--";
    private static final String DEFAULT_SETTINGS_FILE = "application.properties";
    private Map<String, Object> properties;

    /**
     * Create a new application settings with the defaults.
     */
    public ApplicationSettings() {
        // set default settings
        properties = new HashMap<>();
        properties.put(MAX_GENERATIONS, 2);
        properties.put(PATTERN, LifePatterns.NOUS_OSCILLATOR);
        properties.put(ENDLESS, false);
        properties.put(MODE, GameMode.ENDLESS);
        properties.put(ALIVE_CHARACTER, '#');
        properties.put(DEAD_CHARACTER, ' ');
        properties.put(INPUT_PATTERN, PatternType.BINARY);
        properties.put(MS_BETWEEN_GENERATIONS, 500L);

        // override default settings from application file
        Path defaultSettingsFilePath = FileSystems.getDefault().getPath(DEFAULT_SETTINGS_FILE);
        if (Files.exists(defaultSettingsFilePath)) {
            loadSettings(defaultSettingsFilePath);
        }
    }

    /**
     * Loads settings from a given path.
     *
     * @param settingsFilePath the path containing the settings to be loaded.
     */
    private void loadSettings(Path settingsFilePath) {
        try {
            Properties properties = new Properties();
            properties.load(Files.newBufferedReader(settingsFilePath));
            loadSettinsgFromProperties(properties);
        } catch (IOException e) {
            System.err.printf("Couldn't read settings file \"%s\": %s", settingsFilePath.toString(),
                e.getMessage());
        }
    }

    private void loadSettinsgFromProperties(Properties properties) {
        for(String property : properties.stringPropertyNames()) {
            switch (property) {
                case MAX_GENERATIONS:
                    properties.put(MAX_GENERATIONS, Integer.parseInt(properties.getProperty(property).trim()));
                    break;
                case ENDLESS:
                    properties.put(ENDLESS, Boolean.parseBoolean(properties.getProperty(property).trim()));
                    break;
                case PATTERN:
                    properties.put(PATTERN, LifePatterns.valueOf(properties.getProperty(property).trim()));
                    break;
                case MODE:
                    properties.put(MODE, GameMode.valueOf(properties.getProperty(property).trim()));
                    break;
                case ALIVE_CHARACTER:
                    properties.put(ALIVE_CHARACTER, properties.getProperty(property).charAt(0));
                    break;
                case DEAD_CHARACTER:
                    properties.put(DEAD_CHARACTER, properties.getProperty(property).charAt(0));
                    break;
                case INPUT_PATTERN:
                    properties.put(INPUT_PATTERN, PatternType.valueOf(properties.getProperty(property).trim()));
                    break;
                case PATTERN_FILE:
                    properties.put(PATTERN_FILE, properties.getProperty(property).trim());
                    break;
                case MS_BETWEEN_GENERATIONS:
                    properties.put(MS_BETWEEN_GENERATIONS, Long.valueOf(properties.getProperty(property).trim()));
                    break;
                default:
                    System.err.printf("Ignoring unknown property %s", property);
                    break;
            }
        }
    }

    /**
     * Parses the settings contained in the string array settings.
     *
     * @param settings a string array with settings to be parsed.
     * @throws ParseException whenever a mandatory argument fails to be parsed or isn't provided.
     */
    public void parseSettings(String[] settings) throws ParseException {
        for (int i = 0; i < settings.length; i++) {
            try {
                switch (settings[i]) {
                    case MAX_GENERATIONS:
                    case COMMAND_PREFIX + MAX_GENERATIONS:
                        properties.put(MAX_GENERATIONS, Integer.parseInt(settings[++i]));
                        break;
                    case ENDLESS:
                    case COMMAND_PREFIX + ENDLESS:
                        properties.put(ENDLESS, true);
                        break;
                    case PATTERN:
                    case COMMAND_PREFIX + PATTERN:
                        properties.put(PATTERN, LifePatterns.valueOf(settings[++i]));
                        break;
                    case MODE:
                    case COMMAND_PREFIX + MODE:
                        properties.put(MODE, GameMode.valueOf(settings[++i]));
                        break;
                    case ALIVE_CHARACTER:
                    case COMMAND_PREFIX + ALIVE_CHARACTER:
                        properties.put(ALIVE_CHARACTER, settings[++i].charAt(0));
                        break;
                    case DEAD_CHARACTER:
                    case COMMAND_PREFIX + DEAD_CHARACTER:
                        properties.put(DEAD_CHARACTER, settings[++i].charAt(0));
                        break;
                    case INPUT_PATTERN:
                    case COMMAND_PREFIX + INPUT_PATTERN:
                        properties.put(INPUT_PATTERN, PatternType.valueOf(settings[++i]));
                        break;
                    case PATTERN_FILE:
                    case COMMAND_PREFIX + PATTERN_FILE:
                        properties.put(PATTERN_FILE, settings[++i]);
                        break;
                    case MS_BETWEEN_GENERATIONS:
                    case COMMAND_PREFIX + MS_BETWEEN_GENERATIONS:
                        properties.put(MS_BETWEEN_GENERATIONS, Long.valueOf(settings[++i]));
                        break;
                    case "--help":
                        ConsoleInterface.printHelp();
                        System.exit(0);
                        break;
                    default:
                        System.err.printf("Ignoring unknown property %s", settings[i]);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParseException(
                    "Error on mandatory argument for property \"" + settings[--i] + "\"", i);
            }
        }
    }

    /**
     * Checks if a given property is defined.
     *
     * @param property The property to be checked.
     * @return true if the property is defined, false otherwise.
     */
    public boolean isPropertyDefined(String property) {
        return properties.containsKey(property);
    }

    /**
     * Gets the value of a defined property.
     *
     * @param property The property containing the value to be fetched.
     * @return The value of the defined property or null if the property isn't defined.
     */
    @SuppressWarnings("unchecked") public <X> X getProperty(String property) {
        return (X) properties.get(property);
    }

    /**
     * Returns the names of the defined properties
     *
     * @return an unmodifiable collection with the names of the defined properties.
     */
    public Collection<String> getPropertyNames() {
        return Collections.unmodifiableSet(properties.keySet());
    }

}
