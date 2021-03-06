package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

import lesson5.impl.GraphBuilder;
import lesson5.impl.GraphBuilder.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {

    /**
     * Эйлеров цикл.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     * <p>
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     * <p>
     * Time - O(e+v)
     * Space - O(e+v)
     * <p>
     * e - edges
     * v - vertices
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        List<Graph.Edge> result = new ArrayList<>();
        if (!isEulerian(graph)) return result;

        Stack<Graph.Vertex> stack = new Stack<>();
        Set<Graph.Vertex> vertices = graph.getVertices();
        Set<Graph.Edge> edges = graph.getEdges();
        ArrayDeque<Graph.Vertex> subResult = new ArrayDeque<>();

        Iterator<Graph.Vertex> iterator = vertices.iterator();
        Graph.Vertex first = iterator.next();
        stack.push(first);

        while (!stack.empty()) {
            Graph.Vertex currentVertex = stack.peek();
            for (Graph.Vertex vertex : vertices) {
                Graph.Edge currentEdge = graph.getConnection(currentVertex, vertex);
                if (currentEdge != null && edges.contains(currentEdge)) {
                    stack.push(vertex);
                    edges.remove(currentEdge);
                    break;
                }
            }
            if (stack.peek() == currentVertex) {
                stack.pop();
                subResult.add(currentVertex);
            }
        }

        int steps = subResult.size() - 1;


        for (int i = 0; i < steps; i++) {
            result.add(graph.getConnection(subResult.remove(), subResult.getFirst()));
        }

        return result;
    }


    /**
     * Вспомогательная функция для проверки "эйлеровости" графа.
     * Для того, чтобы в графе присутствовал Эйлеров цикл, достаточно:
     * 1) Все вершины имеют чётную степень
     * 2) Граф связный
     */
    private static boolean isEulerian(Graph graph) {
        for (Graph.Vertex vertex : graph.getVertices()) {
            if (graph.getNeighbors(vertex).size() % 2 != 0) return false;
        }
        return true;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ:
     * <p>
     * G    H
     * |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     *
     *
     * Time - O(V+E)
     * Space - O(V+E)
     */
    public static Graph minimumSpanningTree(Graph graph) {

        Set<Graph.Vertex> vertices = graph.getVertices();
        Iterator<Graph.Vertex> iterator = vertices.iterator();
        Graph.Vertex first = iterator.next();

        Map<Graph.Vertex, VertexInfo> info = lesson5.DijkstraKt.shortestPath(graph, first);

        GraphBuilder result = new GraphBuilder();

        vertices.forEach((value) -> result.addVertex(value.getName()));
        info.forEach((key, value) -> {
            if (value.getPrev() != null)
                result.addConnection(value.getPrev(), key, value.getDistance());
        });
        return result.build();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     * <p>
     * Дан граф без циклов (получатель), например
     * <p>
     * G -- H -- J
     * |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     * <p>
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     * <p>
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     * <p>
     * В данном случае ответ (A, E, F, D, G, J)
     * <p>
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     * <p>
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        throw new NotImplementedError();
    }
}
