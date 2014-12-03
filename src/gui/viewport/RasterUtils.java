package gui.viewport;

import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileInputStream;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.SeekableStream;

/**
 * <p>
 * Title: Replay Tool For ACE
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: Eurocontrol - CRDS
 * </p>
 * 
 * @author Mustafa Keskin
 * @version 1.0
 */
public class RasterUtils {

	private static BufferedImage cropImage(AViewport viewport,
			BufferedImage mapImage, double minLat, double minLon,
			double maxLat, double maxLon) {
		// crop the image according the airspace
		int imgX = mapImage.getWidth();
		int imgY = mapImage.getHeight();
		double lonScale = imgX / (maxLon - minLon);
		double latScale = imgY / (maxLat - minLat);

		if (minLon < viewport.lonMin) {
			minLon = viewport.lonMin;
		}

		if (minLat < viewport.latMin) {
			minLat = viewport.latMin;
		}

		if (maxLon < viewport.lonMax) {
			maxLon = viewport.lonMax;
		}

		if (maxLat < viewport.latMax) {
			maxLat = viewport.latMax;
		}

		int h = (int) ((maxLat - minLat) * latScale);
		int w = (int) ((maxLon - minLon) * lonScale);

		mapImage = mapImage.getSubimage(imgX - w, imgY - h, w, h);

		return mapImage;
	}

	public static BufferedImage makeRaster(AViewport viewport, String fileName,
			double minLat, double minLon, double maxLat, double maxLon) {

		BufferedImage mapImage = toBufferedImage(fileName);

		if (mapImage != null) {
			mapImage = cropImage(viewport, mapImage, minLon, minLat, maxLon,
					maxLat);

			// AffineTransform tx = new AffineTransform();

			// scale it if necessary
			// tx.scale(2, 2);

			// AffineTransformOp op = new AffineTransformOp(tx,
			// AffineTransformOp.TYPE_BILINEAR);
			// mapImage = op.filter(mapImage, null);
		}

		return mapImage;
	}

	// This method returns a buffered image with the contents of an image
	private static BufferedImage toBufferedImage(String fileName) {
		BufferedImage image = null;

		try {
			// Read from a file
			int idx = fileName.lastIndexOf(".");
			String fileType = fileName.substring(idx + 1).toLowerCase();

			try {
				if (fileType.equals("jpg") || fileType.equals("jpeg")) {
					FileInputStream in = new FileInputStream(fileName);
					JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
					image = decoder.decodeAsBufferedImage();
					in.close();
				} else {
					File file = new File(fileName);
					SeekableStream stream = new FileSeekableStream(file);

					ParameterBlock params = new ParameterBlock();
					params.add(stream);

					RenderedOp image1 = JAI.create(fileType, params);
					image = image1.getAsBufferedImage();

					stream.close();
				}
			} catch (Exception ex) {
				System.out.println("Unsupported map file format " + fileType);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

}
