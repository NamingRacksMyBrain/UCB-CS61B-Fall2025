import static com.google.common.truth.Truth.assertThat;

import main.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestGraph {
    @Test
    public void testGraph() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        assertThat(g.adj(0)).isEqualTo(expected);

        expected = new ArrayList<>();
        expected.add(2);
        assertThat(g.adj(1)).isEqualTo(expected);
    }
}
