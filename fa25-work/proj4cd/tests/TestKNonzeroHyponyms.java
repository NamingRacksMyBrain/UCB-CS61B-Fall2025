import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import org.junit.jupiter.api.Test;
import main.AutograderBuddy;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestKNonzeroHyponyms {
    private static final String PREFIX = "./data/";

    /** NGrams Files */
    public static final String WORD_HISTORY_EECS_FILE = PREFIX + "word_history_eecs.csv";
    public static final String WORD_HISTORY_SIZE3_FILE = PREFIX + "word_history_size3.csv";
    public static final String WORD_HISTORY_SIZE4_FILE = PREFIX + "word_history_size4.csv";
    public static final String WORD_HISTORY_SIZE1291_FILE = PREFIX + "word_history_size1291.csv";
    public static final String WORD_HISTORY_SIZE14377_FILE = PREFIX + "word_history_size14377.csv";
    public static final String YEAR_HISTORY_FILE = PREFIX + "year_history.csv";

    /** WordNet Files */
    public static final String SYNSETS_EECS_FILE = PREFIX + "synsets_eecs.txt";
    public static final String HYPONYMS_EECS_FILE = PREFIX + "hyponyms_eecs.txt";
    public static final String SYNSET_SIZE16_FILE = PREFIX + "synsets_size16.txt";
    public static final String HYPONYM_SIZE16_FILE = PREFIX + "hyponyms_size16.txt";
    public static final String SYNSET_SIZE82191_FILE = PREFIX + "synsets_size82191.txt";
    public static final String HYPONYM_SIZE82191_FILE = PREFIX +  "hyponyms_size82191.txt";

    @Test
    public void randomKNonzeroTest() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORD_HISTORY_SIZE14377_FILE, YEAR_HISTORY_FILE, SYNSET_SIZE82191_FILE, HYPONYM_SIZE82191_FILE);
        // Case 1
        List<String> words = new ArrayList<>();
        words.add("hello");
        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[hello, hi, how-do-you-do, howdy, hullo]";
        assertThat(actual).isEqualTo(expected);

        // Case 2
        words = new ArrayList<>();
        words.add("souvenir");
        nq = new NgordnetQuery(words, 0, 0, 0);
        actual = studentHandler.handle(nq);
        expected = "[cracker, cracker_bonbon, favor, favour, keepsake, love-token, memento, party_favor, party_favour, relic, snapper, souvenir, token]";
        assertThat(actual).isEqualTo(expected);

        // Case 3
        words = new ArrayList<>();
        words.add("souvenir");
        nq = new NgordnetQuery(words, 2000, 2020, 3);
        actual = studentHandler.handle(nq);
        expected = "[favor, favour, token]";
        assertThat(actual).isEqualTo(expected);

        // Case 4
        words = new ArrayList<>();
        words.add("cook");
        words.add("food");
        nq = new NgordnetQuery(words, 0, 0, 0);
        actual = studentHandler.handle(nq);
        expected = "[cookie, cooky, roaster, seasoner]";
        assertThat(actual).isEqualTo(expected);

        // Case 5
        words = new ArrayList<>();
        words.add("cook");
        words.add("food");
        nq = new NgordnetQuery(words, 2000, 2020, 3);
        actual = studentHandler.handle(nq);
        expected = "[cookie]";
        assertThat(actual).isEqualTo(expected);

        // Case 6
        words = new ArrayList<>();
        words.add("dog");
        words.add("meat");
        nq = new NgordnetQuery(words, 1950, 2020, 3);
        actual = studentHandler.handle(nq);
        expected = "[dog, frank]";
        assertThat(actual).isEqualTo(expected);
    }

    // below by gemini
    @Test
    public void testEecsTieBreaker() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORD_HISTORY_EECS_FILE, YEAR_HISTORY_FILE, SYNSETS_EECS_FILE, HYPONYMS_EECS_FILE);

        List<String> words = new ArrayList<>();
        words.add("CS61A");

        NgordnetQuery nq = new NgordnetQuery(words, 2010, 2020, 4);
        String actual = studentHandler.handle(nq);
        String expected = "[CS170, CS61A, CS61B, CS61C]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testKGreaterThanAvailable() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORD_HISTORY_SIZE14377_FILE, YEAR_HISTORY_FILE, SYNSET_SIZE82191_FILE, HYPONYM_SIZE82191_FILE);
        List<String> words = new ArrayList<>();
        words.add("dog");
        words.add("meat");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 2020, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[dog, frank]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testKEqualsOne() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymsHandler(
                WORD_HISTORY_SIZE14377_FILE, YEAR_HISTORY_FILE, SYNSET_SIZE82191_FILE, HYPONYM_SIZE82191_FILE);
        List<String> words = new ArrayList<>();
        words.add("cook");
        words.add("food");

        NgordnetQuery nq = new NgordnetQuery(words, 1950, 2020, 1);
        String actual = studentHandler.handle(nq);
        String expected = "[cookie]";
        assertThat(actual).isEqualTo(expected);
    }
}
