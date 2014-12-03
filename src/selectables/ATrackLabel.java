package selectables;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import resources.Resources;

import utils.Options;

public abstract class ATrackLabel implements ISelectable {
	public double cornerX = 0D;
	public double cornerY = 0D;
	protected double height = 0D;
	protected double maxWidth = 0D;
	protected double leaderLength = Options.LEADER_LENGTH;
	protected double leaderAngle = 45D;
	public ATrack track = null;

	public void draw(AViewport view, Graphics2D g2) {
		cornerX = track.x + leaderLength
				* Math.cos(Math.toRadians(leaderAngle));
		cornerY = track.y + leaderLength
				* Math.sin(Math.toRadians(leaderAngle));

		g2.setFont(Resources.TRACK_FONT);

		drawLabel(view, g2);

		// leader line
		drawLeaderLine(g2);

		// callsign frame
		if (track.selected) {
			g2.setColor(Resources.SELECTED_TRACK_COLOR);
			g2.fill(new Rectangle2D.Double(cornerX, cornerY, maxWidth,
					height + 2));
			g2.setColor(track.trackColor);
		} else {
			g2.setColor(Resources.TRACK_COLOR);
			g2.draw(new Rectangle2D.Double(cornerX, cornerY, maxWidth,
					height + 2));
		}
	}

	protected abstract void drawLabel(AViewport view, Graphics2D g2);

	protected void drawLeaderLine(Graphics2D g2) {
		g2.setStroke(Resources.NORMAL_LINE);

		// leader line
		if (leaderAngle <= 0) {
			// 1st.quadrant
			g2.draw(new Line2D.Double(track.x, track.y, cornerX, cornerY
					+ height + 2));
		} else if (leaderAngle > 0 && leaderAngle <= 90) {
			// 4th. quadrant
			g2.draw(new Line2D.Double(track.x, track.y, cornerX, cornerY));
		} else if (leaderAngle > 90 && leaderAngle <= 180) {
			// 4th. quadrant
			g2.draw(new Line2D.Double(track.x, track.y, cornerX + maxWidth,
					cornerY));
		} else {
			// 3th. quadrant
			g2.draw(new Line2D.Double(track.x, track.y, cornerX + maxWidth,
					cornerY + height + 2));
		}
	}

	public abstract ASelectable getSelectedField(MouseEvent e);

	public boolean isMouseInside(AViewport view, MouseEvent e) {
		if (track.visible) {
			if ((cornerX <= e.getPoint().getX() && (cornerX + maxWidth) >= e
					.getPoint().getX())
					&& (cornerY <= e.getPoint().getY() && cornerY + height >= e
							.getPoint().getY())) {
				// mouse is inside the label
				return true;
			}
		}

		return false;
	}

	public void moveLabel(MouseEvent e) {
		double xdiff = e.getX() - track.x;
		double ydiff = e.getY() - track.y;

		leaderAngle = Math.toDegrees(Math.atan(ydiff / xdiff));

		if (xdiff < 0) {
			leaderAngle += 180;
		}

		leaderLength = Math.sqrt(xdiff * xdiff + ydiff * ydiff);
	}

	protected void printLine(Graphics2D g2, String line) {
		if (!line.equals("")) {
			line = " " + line + " ";
			double width = 0D;
			height += g2.getFontMetrics().getHeight();
			width = g2.getFontMetrics().stringWidth(line);
			if (width > maxWidth) {
				maxWidth = width;
			}

			g2.drawString(line, (float) cornerX, (float) (cornerY + height));
		}
	}
}
