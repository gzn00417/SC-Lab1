package P3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class FriendshipGraphTest {

    @Test
    void GraphTest() {
	FriendshipGraph graph = new FriendshipGraph();

	Person rachel = new Person("Rachel");
	Person ross = new Person("Ross");
	Person ben = new Person("Ben");
	Person kramer = new Person("Kramer");

	graph.addVertex(rachel);
	graph.addVertex(ross);
	graph.addVertex(ben);
	graph.addVertex(kramer);

	graph.addEdge(rachel, ross);
	graph.addEdge(ross, rachel);
	graph.addEdge(ross, ben);
	graph.addEdge(ben, ross);
	/*
	 * System.out.println(graph.getDistance(rachel, ross));// 1
	 * System.out.println(graph.getDistance(rachel, ben));// 2
	 * System.out.println(graph.getDistance(rachel, rachel));// 0
	 * System.out.println(graph.getDistance(rachel, kramer));// -1
	 */
	assertEquals(1, graph.getDistance(rachel, ross));
	assertEquals(2, graph.getDistance(rachel, ben));
	assertEquals(0, graph.getDistance(rachel, rachel));
	assertEquals(-1, graph.getDistance(rachel, kramer));
    }

    /**
     * TODO to test program exit when graph has the same names.
     */
    @Test
    void ExceptionProcess() {
	FriendshipGraph graph = new FriendshipGraph();
	
	Person a = new Person("a");
	graph.addVertex(a);
	
	Person b = new Person("b");
	graph.addVertex(b);

	// Person c = new Person("a");
	// graph.addVertex(c);
    }

}
