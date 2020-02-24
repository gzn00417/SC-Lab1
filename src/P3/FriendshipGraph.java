package P3;

import java.util.*;
import java.util.LinkedList;
import java.util.Queue;

public class FriendshipGraph {
    public static void main(String[] args[]) {
        return;
    }

    public class Node {
        Node next = null;
        Person person;
        boolean vis;
        int dis = Integer.MAX_VALUE;

        public void LoadData(Person person) {
            this.person = person;
            person.node = this;
        }

        public void addNode(Node nextVertex) {
            nextVertex.next = this.next;
            this.next = nextVertex;
        }

        public class Edge {
            public Node origin = null, terminal = null;
            public Edge nextEdge = null;
        }

        Edge lastEdge = null;

        public void addNodeEdge(Node toVertex) {
            Edge newEdge = new Edge();
            newEdge.origin = this;
            newEdge.terminal = toVertex;
            newEdge.nextEdge = this.lastEdge;
            this.lastEdge = newEdge;
        }
    }

    Node head = null;

    public void addVertex(Person x) {
        Node NewVertex = new Node();
        x.node = NewVertex;
        NewVertex.LoadData(x);
        if (head == null)
            head = NewVertex;
        else
            head.addNode(NewVertex);
        return;
    }

    public void addEdge(Person a, Person b) {
    	Node A = a.node, B = b.node;
        A.addNodeEdge(B);
        B.addNodeEdge(A);
        return;
    }

    public int getDistance(Person sta, Person end) {
    	if (sta == end) return 0;
        Queue<Person> qu = new LinkedList<Person>();
        for (Node p = head; p != null; p = p.next) {
            p.vis = false;
            p.dis = 0;
        }
        sta.node.vis = true;
        for (qu.offer(sta); !qu.isEmpty();) {
            Person p = qu.poll();
            for (Node.Edge e = p.node.lastEdge; e != null; e = e.nextEdge) {
                if (!e.terminal.vis) {
                    qu.offer(e.terminal.person);
                    e.terminal.vis = true;
                    e.terminal.dis = p.node.dis + 1;
                    if (e.terminal.person == end)
                        return end.node.dis;
                }
            }
        }
        return -1;
    }
}