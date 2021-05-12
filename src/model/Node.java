package model;

import view.PathView;

public class Node {

    private Coords coords;
    private int f = 0;
    private Node parentNode;
    private PathView currentNode;
    private Direction directionFromParent;
    private boolean visited = false;

    public Node(PathView currentNode) {
        this.currentNode = currentNode;
        this.setCoords(currentNode.getUnitPosition());
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public PathView getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(PathView currentNode) {
        this.currentNode = currentNode;
    }

    public Direction getDirectionFromParent() {
        return directionFromParent;
    }

    public void setDirectionFromParent(Direction directionFromParent) {
        this.directionFromParent = directionFromParent;
    }

    public boolean visited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
