package model;

import view.*;

import java.util.*;

public class Pathfinder {

    private final WarehouseView warehouseView;
    private final CartView cartView;
    private List<ShelfView> shelfViewsContainingGoods = new ArrayList<>();
    private List<GoodsType> goodsTypes = new ArrayList<>();
    private List<Coords> stops = new ArrayList<>();
    private Coords nextStop;

    public Pathfinder(WarehouseView warehouseView, CartView cartView) {
        this.warehouseView = warehouseView;
        this.cartView = cartView;
    }

    public void computePath() {
        Coords homePosition = cartView.getCart().getHomePosition();
        CartRoute cartRoute = cartView.getCart().getPlannedRoute();
        HashMap<Integer, Direction> directionHashMap = new HashMap<>();
        int count = 0;
//        Coords lastPos = cartView.getUnitPosition();
//        for (ShelfView shelfView : shelfViewsContainingGoods) {
//            if (warehouseView.getUnitViewAtCoords(
//                    new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() - 1))
//                    .getUnitType().equals(UnitTypes.PATHVIEW)) {
//                System.out.println(shelfView.getUnitPosition().getRow() + " " + (shelfView.getUnitPosition().getColumn() - 1));
//                for (Direction direction :
//                        computeRoute(lastPos, new Coords(shelfView.getUnitPosition().getRow(),
//                                shelfView.getUnitPosition().getColumn() - 1))) {
//                    directionHashMap.put(count++, direction);
//                }
//                lastPos = new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() - 1);
//            } else if (warehouseView.getUnitViewAtCoords(
//                    new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() + 1))
//                    .getUnitType().equals(UnitTypes.PATHVIEW)) {
//                System.out.println(shelfView.getUnitPosition().getRow() + " " + (shelfView.getUnitPosition().getColumn() + 1));
//                for (Direction direction :
//                        computeRoute(lastPos, new Coords(shelfView.getUnitPosition().getRow(),
//                                shelfView.getUnitPosition().getColumn() + 1))) {
//                    directionHashMap.put(count++, direction);
//                }
//                lastPos = new Coords(shelfView.getUnitPosition().getRow(), shelfView.getUnitPosition().getColumn() + 1);
//            }
//        }
//        UnloadingView unloadingView = warehouseView.getUnloadingViews().get(new Random().nextInt(warehouseView.getUnloadingViews().size()-1));
//        for (Direction direction :
//                computeRoute(lastPos, unloadingView.getUnitPosition())) {
//            directionHashMap.put(count++, direction);
//        }
//        System.out.println(cartView.getUnitPosition() + " " + homePosition);
//        for (Direction direction :
//                computeRoute(cartView.getUnitPosition(), homePosition)) {
//            directionHashMap.put(count++, direction);
//        }

        Coords lastCoords = cartView.getUnitPosition();
        for (Coords coords : stops){
            for(Direction direction : computeRoute(lastCoords, coords)) {
                directionHashMap.put(count++, direction);
            }
            lastCoords = coords;
        }
        System.out.println(directionHashMap);
        cartRoute.setStep(0);
        cartRoute.setPlannedPath(directionHashMap);
        cartView.getCart().setPlannedRoute(cartRoute);

    }

    /**
     * A* pathfinding algorithm
     * @param start Starting coordinates
     * @param end Ending coordinates
     */
    private List<Direction> computeRoute(Coords start, Coords end) {
        List<Direction> directions = new ArrayList<>();

        List<Node> openNodes = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();

        for (PathView pathView : warehouseView.getPathViews()) {
            Node node = new Node(pathView);
            nodes.add(node);
        }

        Node currentNode = null;
        for (Node node : nodes) {
            if (node.getCoords().equals(start)) {
                currentNode = node;
                currentNode.setF(Integer.MAX_VALUE-1);
                currentNode.setVisited(true);
                System.out.println("found matching start");
                break;
            }
        }
        boolean startOfAlg = true;
        openNodes.add(currentNode);
        while (!openNodes.isEmpty()) {
            int lowestF = Integer.MAX_VALUE;
            for (Node node : openNodes) {
                if (node.getF() <= lowestF) {
                    lowestF = node.getF();
                    currentNode = node;
                }
            }
            if(startOfAlg){
                startOfAlg = false;
                openNodes.remove(currentNode);
            }
            Coords currentNodePosition = currentNode.getCoords();
            if(currentNodePosition.equals(end)){
                directions.add(Direction.TAKEOUT);
                System.out.println(directions);
                return directions;
            }
            if (getNodeAtPos(currentNodePosition.oneUp(), nodes) != null) {
                if (!getNodeAtPos(currentNodePosition.oneUp(), nodes).visited()
                        && !getNodeAtPos(currentNodePosition.oneUp(), nodes).getCurrentNode().getPath().isBlocked()) {
                    Node northNode = getNodeAtPos(currentNodePosition.oneUp(), nodes);
                    northNode.setF(computeG(northNode) + computeH(northNode.getCoords(), end));
                    northNode.setParentNode(currentNode);
                    northNode.setDirectionFromParent(Direction.UP);
                    openNodes.add(northNode);
                    northNode.setVisited(true);
                    System.out.println(northNode.getCoords() + " " + end);
                    if (northNode.getCoords().equals(end)) {
                        directions.add(Direction.TAKEOUT);
                        while (northNode.getParentNode() != null) {
                            directions.add(northNode.getDirectionFromParent());
                            northNode = northNode.getParentNode();
                        }
                        Collections.reverse(directions);
                        System.out.println(directions);
                        return directions;
                    }
                }
            }
            if (getNodeAtPos(currentNodePosition.oneDown(), nodes) != null) {
                if (!getNodeAtPos(currentNodePosition.oneDown(), nodes).visited()
                        && !getNodeAtPos(currentNodePosition.oneDown(), nodes).getCurrentNode().getPath().isBlocked()) {
                    Node southNode = getNodeAtPos(currentNodePosition.oneDown(), nodes);
                    southNode.setF(computeG(southNode) + computeH(southNode.getCoords(), end));
                    southNode.setParentNode(currentNode);
                    southNode.setDirectionFromParent(Direction.DOWN);
                    openNodes.add(southNode);
                    southNode.setVisited(true);
                    System.out.println(southNode.getCoords() + " " + end);
                    if (southNode.getCoords().equals(end)) {
                        directions.add(Direction.TAKEOUT);
                        while (southNode.getParentNode() != null) {
                            directions.add(southNode.getDirectionFromParent());
                            southNode = southNode.getParentNode();
                        }
                        Collections.reverse(directions);
                        System.out.println(directions);
                        return directions;
                    }
                }
            }
            if (getNodeAtPos(currentNodePosition.oneLeft(), nodes) != null) {
                if (!getNodeAtPos(currentNodePosition.oneLeft(), nodes).visited()
                        && !getNodeAtPos(currentNodePosition.oneLeft(), nodes).getCurrentNode().getPath().isBlocked()) {
                    Node westNode = getNodeAtPos(currentNodePosition.oneLeft(), nodes);
                    westNode.setF(computeG(westNode) + computeH(westNode.getCoords(), end));
                    westNode.setParentNode(currentNode);
                    westNode.setDirectionFromParent(Direction.LEFT);
                    openNodes.add(westNode);
                    westNode.setVisited(true);
                    System.out.println(westNode.getCoords() + " " + end);
                    if (westNode.getCoords().equals(end)) {
                        directions.add(Direction.TAKEOUT);
                        while (westNode.getParentNode() != null) {
                            directions.add(westNode.getDirectionFromParent());
                            westNode = westNode.getParentNode();
                        }
                        Collections.reverse(directions);
                        System.out.println(directions);
                        return directions;
                    }
                }
            }
            if (getNodeAtPos(currentNodePosition.oneRight(), nodes) != null) {
                if (!getNodeAtPos(currentNodePosition.oneRight(), nodes).visited()
                        && !getNodeAtPos(currentNodePosition.oneRight(), nodes).getCurrentNode().getPath().isBlocked()) {
                    Node eastNode = getNodeAtPos(currentNodePosition.oneRight(), nodes);
                    eastNode.setF(computeG(eastNode) + computeH(eastNode.getCoords(), end));
                    eastNode.setParentNode(currentNode);
                    eastNode.setDirectionFromParent(Direction.RIGHT);
                    openNodes.add(eastNode);
                    eastNode.setVisited(true);
                    System.out.println(eastNode.getCoords() + " " + end);
                    if (eastNode.getCoords().equals(end)) {
                        directions.add(Direction.TAKEOUT);
                        while (eastNode.getParentNode() != null) {
                            directions.add(eastNode.getDirectionFromParent());
                            eastNode = eastNode.getParentNode();
                        }
                        Collections.reverse(directions);
                        System.out.println(directions);
                        return directions;
                    }
                }
            } else {
                openNodes.remove(currentNode);
            }
        }

        Collections.reverse(directions);
        System.out.println(directions);
        return directions;
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

    public Node getNodeAtPos(Coords coords, List<Node> nodes) {
        for (Node node : nodes) {
            if (node.getCoords().equals(coords)) {
                return node;
            }
        }
        return null;
    }

    public void setStops(List<Coords> stops){
        this.stops = stops;
    }

    public void achievedStop(){
        stops.remove(0);
    }
}
