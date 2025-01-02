class Node {
    private position position;
    private Node parent;
    private double g;
    private double f;

    public Node(position position, Node parent, double g, double f) {
        this.position = position;
        this.parent = parent;
        this.g = g;
        this.f = f;
    }

    public position getPosition() {
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
