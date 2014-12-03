package utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JInternalFrame;

public class SaveUtilities {
	public static void saveAsJPG(JInternalFrame win, String fileName) {
		Dimension size = win.getSize();
		BufferedImage image = (BufferedImage) win.createImage(size.width,
				size.height);
		Graphics g = image.getGraphics();
		win.paint(g);
		g.dispose();
		try {
			ImageIO.write(image, "jpg", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void saveFrame(JInternalFrame frm) {
		// TODO with multiple formats
		saveAsJPG(frm, "C:/MyFrame2.jpg");
	}
}
