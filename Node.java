class Node {
    private Position position;
    private Node parent;
    private double g;
    private double f;
    public Node(Position position, Node parent, double g, double f) {
        this.position = position;
        this.parent = parent;
        this.g = g;
        this.f = f;
    }
    public Position getPosition() {
        return position;
    }
    public Node getParent() {
        return parent;
    }
    public double getG() {
        return g;
    }
    public double getF() {
        return f;
    }
}
