package com.jvosantos.games.gameoflife;

import com.jvosantos.games.gameoflife.engine.GameEngineFactory;
import com.jvosantos.games.gameoflife.engine.LifePatterns;
import com.jvosantos.games.gameoflife.settings.ApplicationSettings;
import com.jvosantos.games.gameoflife.ui.ConsoleInterface;
import net.nous.test.GameOfLife;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.ParseException;

/**
 * A running application for game of life. Accepts different setting either by default, file and/or application arguments and runs game of life.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class App {

    private static final ApplicationSettings APPLICATION_SETTINGS = new ApplicationSettings();

    /**
     * Runs game of life with the defined settings.
     * @param args settings defined by command line.
     * @throws Exception
     * @see com.jvosantos.games.gameoflife.settings.ApplicationSettings
     */
    public static void main(String[] args) throws Exception {
        // parse command line settings
        try {
            APPLICATION_SETTINGS.parseSettings(args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return ;
        }

        // instantiate a new game of life
        GameOfLife gameOfLife =
            GameEngineFactory.getEngine(APPLICATION_SETTINGS.getProperty(ApplicationSettings.MODE));

        // create a new console interface
        ConsoleInterface consoleInterface = new ConsoleInterface(
            APPLICATION_SETTINGS.getProperty(ApplicationSettings.ALIVE_CHARACTER),
            APPLICATION_SETTINGS.getProperty(ApplicationSettings.DEAD_CHARACTER));

        // get initial seed
        int[][] seed = (APPLICATION_SETTINGS.getProperty(ApplicationSettings.PATTERN_FILE) != null)?
            LifePatterns.parsePattern(Files.readAllLines(FileSystems.getDefault()
                .getPath(APPLICATION_SETTINGS.getProperty(ApplicationSettings.PATTERN_FILE))))
            :
            ((LifePatterns) APPLICATION_SETTINGS.getProperty(ApplicationSettings.PATTERN))
                .getPattern();

        // print the initial seed
        consoleInterface.printBoard(seed);
        // initialize game of life with the given seed
        gameOfLife.seed(seed);

        // get max generations and endless flag from settings
        int maxGenerations = APPLICATION_SETTINGS.getProperty(ApplicationSettings.MAX_GENERATIONS);
        boolean endless = APPLICATION_SETTINGS.getProperty(ApplicationSettings.ENDLESS);

        // until we reach the defined max generations or forever
        // print the next board and wait a few milliseconds.
        for (int i = 0; i < maxGenerations || endless; i++) {
            consoleInterface.printBoard(gameOfLife.next());
            Thread.sleep(
                APPLICATION_SETTINGS.getProperty(ApplicationSettings.MS_BETWEEN_GENERATIONS));
        }
    }

}
