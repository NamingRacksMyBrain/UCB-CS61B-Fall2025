import static com.google.common.truth.Truth.assertThat;

import main.WordNet;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

public class TestWordNet {
    @Test
    public void testHyponymsSimple(){
        WordNet wn = new WordNet("./data/synsets_size11.txt","./data/hyponyms_size11.txt");
        assertThat(wn.hyponyms("antihistamine")).isEqualTo(Set.of("antihistamine","actifed"));
    }

    @Test
    public void testHyponymsMedium(){
        WordNet wn = new WordNet("./data/synsets_size16.txt","./data/hyponyms_size16.txt");
        Set<String> actual = wn.hyponyms("change");
        assertThat(actual).isEqualTo(Set.of("alteration", "change", "demotion", "increase", "jump", "leap", "modification", "saltation", "transition", "variation"));
    }
}
