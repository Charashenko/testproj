package model;

import view.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Pathfinder {

    private final WarehouseView warehouseView;
    private final CartView cartView;
    private List<ShelfView> shelfViewsContainingGoods = new ArrayList<>();
    private List<GoodsType> goodsTypes = new ArrayList<>();

    public Pathfinder(WarehouseView warehouseView, CartView cartView) {
        this.warehouseView = warehouseView;
        this.cartView = cartView;
    }

    public void computePath() {
        Coords homePosition = cartView.getCart().getHomePosition();
        CartRoute cartRoute = cartView.getCart().getPlannedRoute();
        HashMap<Integer, Direction> directionHashMap = new HashMap<>();
        int count = 0;
        Coords lastPos = cartView.getUnitPosition();
        for (ShelfView shelfView : shelfViewsContainingGoods) {
            if (warehouseView.getUnitViewAtCoords(
                    new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() - 1))
                    .getUnitType().equals(UnitTypes.PATHVIEW)) {
                System.out.println(shelfView.getUnitPosition().getRow() + " " + (shelfView.getUnitPosition().getColumn() - 1));
                for (Direction direction :
                        computeRoute(lastPos, new Coords(shelfView.getUnitPosition().getRow(),
                                shelfView.getUnitPosition().getColumn() - 1))) {
                    directionHashMap.put(count++, direction);
                }
                System.out.println(computeRoute(lastPos, new Coords(shelfView.getUnitPosition().getRow(),
                        shelfView.getUnitPosition().getColumn() - 1)));
                lastPos = new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() - 1);
            } else if (warehouseView.getUnitViewAtCoords(
                    new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() + 1))
                    .getUnitType().equals(UnitTypes.PATHVIEW)) {
                System.out.println(shelfView.getUnitPosition().getRow() + " " + (shelfView.getUnitPosition().getColumn() + 1));
                for (Direction direction :
                        computeRoute(lastPos, new Coords(shelfView.getUnitPosition().getRow(),
                                shelfView.getUnitPosition().getColumn() + 1))) {
                    directionHashMap.put(count++, direction);
                }
                System.out.println(computeRoute(lastPos, new Coords(shelfView.getUnitPosition().getRow(),
                        shelfView.getUnitPosition().getColumn() + 1)));
                lastPos = new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() + 1);
            }
        }
        System.out.println(directionHashMap);
        cartRoute.setStep(0);
        cartRoute.setPlannedPath(directionHashMap);
        cartView.getCart().setPlannedRoute(cartRoute);
    }

    /**
     * @param start
     * @param end
     */
    private List<Direction> computeRoute(Coords start, Coords end) {
        List<Direction> directions = new ArrayList<>();
//        List<PathView> visitedPaths = new ArrayList<>();
//        visitedPaths.add(warehouseView.getPathViewAtCoords(start));
//        if(!isEnd(end, start, directions, visitedPaths)){
//            directions.add(Direction.TAKEOUT);
//        }
//        Collections.reverse(directions);
//        return directions;
        List<Node> openNodes = new ArrayList<>();
        List<Node> closedNodes = new ArrayList<>();
        Node startNode = new Node(warehouseView.getPathViewAtCoords(start));
        startNode.setCoords(warehouseView.getPathViewAtCoords(start).getUnitPosition());
        openNodes.add(startNode);
        Node goalNode = new Node(warehouseView.getPathViewAtCoords(end));
        while (!openNodes.isEmpty()) {
            int lowestF = Integer.MAX_VALUE;
            Node parentNode = new Node(startNode.getCurrentNode());
            for (Node lowestNode : new ArrayList<>(openNodes)) {
                if (lowestNode.getF() < lowestF) {
                    lowestF = lowestNode.getF();
                    parentNode = lowestNode;
                }
            }

            openNodes.remove(parentNode);
            Coords parentPos = parentNode.getCoords();

            if(warehouseView.getPathViewAtCoords(parentPos.oneUp()) != null){
                Node upNode = new Node(warehouseView.getPathViewAtCoords(parentPos.oneUp()));
                if (checkValidity(upNode, parentNode)) {
                    upNode.setParentNode(parentNode);
                    upNode.setDirectionFromParent(Direction.UP);
                    if (upNode.getCoords().equals(end)) {
                        while (upNode.getParentNode() != null) {
                            directions.add(upNode.getDirectionFromParent());
                            upNode = upNode.getParentNode();
                        }
                        return directions;
                    } else {
                        upNode.setF(computeG(upNode) + computeH(upNode.getCoords(), end));
                        for (Node node : new ArrayList<>(openNodes)) {
                            if (node.getCoords().equals(upNode.getCoords())) {
                                if(node.getF() < upNode.getF()){
                                    break;
                                }
                            }
                        }
                        for (Node node : new ArrayList<>(closedNodes)) {
                            if (node.getCoords().equals(upNode.getCoords())) {
                                if(node.getF() > upNode.getF()){
                                    openNodes.add(upNode);
                                }
                            }
                        }
                    }
                }
            }

            if(warehouseView.getPathViewAtCoords(parentPos.oneDown()) != null){
                Node downNode = new Node(warehouseView.getPathViewAtCoords(parentPos.oneDown()));
                if (checkValidity(downNode, parentNode)) {
                    downNode.setParentNode(parentNode);
                    downNode.setDirectionFromParent(Direction.DOWN);
                    if (downNode.getCoords().equals(end)) {
                        while (downNode.getParentNode() != null) {
                            directions.add(downNode.getDirectionFromParent());
                            downNode = downNode.getParentNode();
                        }
                        return directions;
                    } else {
                        downNode.setF(computeG(downNode) + computeH(downNode.getCoords(), end));
                        for (Node node : new ArrayList<>(openNodes)) {
                            if (node.getCoords().equals(downNode.getCoords())) {
                                if(node.getF() < downNode.getF()){
                                    break;
                                }
                            }
                        }
                        for (Node node : new ArrayList<>(closedNodes)) {
                            if (node.getCoords().equals(downNode.getCoords())) {
                                if(node.getF() > downNode.getF()){
                                    openNodes.add(downNode);
                                }
                            }
                        }
                    }
                }
            }

            if(warehouseView.getPathViewAtCoords(parentPos.oneLeft()) != null){
                Node leftNode = new Node(warehouseView.getPathViewAtCoords(parentPos.oneLeft()));
                if (checkValidity(leftNode, parentNode)) {
                    leftNode.setParentNode(parentNode);
                    leftNode.setDirectionFromParent(Direction.LEFT);
                    if (leftNode.getCoords().equals(end)) {
                        while (leftNode.getParentNode() != null) {
                            directions.add(leftNode.getDirectionFromParent());
                            leftNode = leftNode.getParentNode();
                        }
                        return directions;
                    } else {
                        leftNode.setF(computeG(leftNode) + computeH(leftNode.getCoords(), end));
                        for (Node node : new ArrayList<>(openNodes)) {
                            if (node.getCoords().equals(leftNode.getCoords())) {
                                if(node.getF() < leftNode.getF()){
                                    break;
                                }
                            }
                        }
                        for (Node node : new ArrayList<>(closedNodes)) {
                            if (node.getCoords().equals(leftNode.getCoords())) {
                                if(node.getF() > leftNode.getF()){
                                    openNodes.add(leftNode);
                                }
                            }
                        }
                    }
                }
            }

            if(warehouseView.getPathViewAtCoords(parentPos.oneRight()) != null){
                Node rightNode = new Node(warehouseView.getPathViewAtCoords(parentPos.oneRight()));
                if (checkValidity(rightNode, parentNode)) {
                    rightNode.setParentNode(parentNode);
                    rightNode.setDirectionFromParent(Direction.RIGHT);
                    if (rightNode.getCoords().equals(end)) {
                        while (rightNode.getParentNode() != null) {
                            directions.add(rightNode.getDirectionFromParent());
                            rightNode = rightNode.getParentNode();
                        }
                        return directions;
                    } else {
                        rightNode.setF(computeG(rightNode) + computeH(rightNode.getCoords(), end));
                        for (Node node : new ArrayList<>(openNodes)) {
                            if (node.getCoords().equals(rightNode.getCoords())) {
                                if(node.getF() < rightNode.getF()){
                                    break;
                                }
                            }
                        }
                        for (Node node : new ArrayList<>(closedNodes)) {
                            if (node.getCoords().equals(rightNode.getCoords())) {
                                if(node.getF() > rightNode.getF()){
                                    openNodes.add(rightNode);
                                }
                            }
                        }
                    }
                }
            }

            openNodes.remove(parentNode);
            closedNodes.add(parentNode);
        }
        return null;
    }

    private boolean isEnd(Coords end, Coords currentCoords, List<Direction> directions, List<PathView> visitedPaths) {
        if (end.equals(currentCoords)) {
            directions.add(Direction.TAKEOUT);
            return true;
        } else {
            if (warehouseView.getPathViewAtCoords(currentCoords.oneUp()) != null
                    && !visitedPaths.contains(warehouseView.getPathViewAtCoords(currentCoords.oneUp()))
                    && !warehouseView.getPathViewAtCoords(currentCoords.oneUp()).getPath().isBlocked()) {
                visitedPaths.add(warehouseView.getPathViewAtCoords(currentCoords.oneUp()));
                if (isEnd(end, currentCoords.oneUp(), directions, visitedPaths)) {
                    directions.add(Direction.UP);
                    return true;
                }
            }
            if (warehouseView.getPathViewAtCoords(currentCoords.oneLeft()) != null
                    && !visitedPaths.contains(warehouseView.getPathViewAtCoords(currentCoords.oneLeft()))
                    && !warehouseView.getPathViewAtCoords(currentCoords.oneLeft()).getPath().isBlocked()) {
                visitedPaths.add(warehouseView.getPathViewAtCoords(currentCoords.oneLeft()));
                if (isEnd(end, currentCoords.oneLeft(), directions, visitedPaths)) {
                    directions.add(Direction.LEFT);
                    return true;
                }
            }
            if (warehouseView.getPathViewAtCoords(currentCoords.oneDown()) != null
                    && !visitedPaths.contains(warehouseView.getPathViewAtCoords(currentCoords.oneDown()))
                    && !warehouseView.getPathViewAtCoords(currentCoords.oneDown()).getPath().isBlocked()) {
                visitedPaths.add(warehouseView.getPathViewAtCoords(currentCoords.oneDown()));
                if (isEnd(end, currentCoords.oneDown(), directions, visitedPaths)) {
                    directions.add(Direction.DOWN);
                    return true;
                }
            }
            if (warehouseView.getPathViewAtCoords(currentCoords.oneRight()) != null
                    && !visitedPaths.contains(warehouseView.getPathViewAtCoords(currentCoords.oneRight()))
                    && !warehouseView.getPathViewAtCoords(currentCoords.oneRight()).getPath().isBlocked()) {
                visitedPaths.add(warehouseView.getPathViewAtCoords(currentCoords.oneRight()));
                if (isEnd(end, currentCoords.oneRight(), directions, visitedPaths)) {
                    directions.add(Direction.RIGHT);
                    return true;
                }
            }
            return false;
        }
    }

    public void setShelfViewsContainingGoods(List<ShelfView> shelfViewsContainingGoods) {
        this.shelfViewsContainingGoods = shelfViewsContainingGoods;
    }

    public List<ShelfView> getShelfViewsContainingGoods() {
        return shelfViewsContainingGoods;
    }

    public List<GoodsType> getGoodsTypes() {
        return goodsTypes;
    }

    public void setGoodsTypes(List<GoodsType> goodsTypes) {
        this.goodsTypes = goodsTypes;
    }

    public void removeGoodsType(GoodsType goodsType) {
        this.goodsTypes.remove(goodsType);
    }

    public void removeShelfViewContainingGoods(ShelfView shelfView) {
        this.shelfViewsContainingGoods.remove(shelfView);
    }

    public int computeH(Coords currentNode, Coords goalNode) {
        return Math.abs(currentNode.getRow() - goalNode.getRow()) + Math.abs(currentNode.getColumn() - goalNode.getColumn());
    }

    public int computeG(Node currentNode) {
        int count = 0;
        while (currentNode.getParentNode() != null) {
            currentNode = currentNode.getParentNode();
            count++;
        }
        return count;
    }

    public boolean checkValidity(Node node, Node parentNode) {
        if (node == null || node.equals(parentNode)) {
            return false;
        } else {
            return !node.getCurrentNode().getPath().isBlocked();
        }
    }
}
