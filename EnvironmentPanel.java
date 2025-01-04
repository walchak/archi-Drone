import javax.swing.*;
import java.awt.*;
class EnvironmentPanel extends JPanel {
    private Environnement env;
    public EnvironmentPanel(Environnement env) {
        this.env = env;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the grid
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < env.getCarte().length; i++) {
            for (int j = 0; j < env.getCarte()[0].length; j++) {
                if (env.getCarte()[i][j]) {
                    g.setColor(Color.DARK_GRAY);  // Obstacles
                    g.fillRect(i * 20, j * 20, 20, 20);
                } else {
                    g.setColor(Color.WHITE);  // Free cells
                    g.drawRect(i * 20, j * 20, 20, 20);
                }
            }
        }
        // Draw drones
        g.setColor(Color.BLUE);
        for (drone d : env.getdrones()) {
            Position pos = d.getPosition();
            g.fillOval((int) pos.getX() * 20, (int) pos.getY() * 20, 20, 20);
        }
        // Draw targets
        g.setColor(Color.GREEN);
        for (Position target : env.getdestination()) {
            g.fillOval((int) target.getX() * 20, (int) target.getY() * 20, 20, 20);
        }
    }
}