package pl.vgtworld.imagedraw.filters.matrix;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageEdgeResizeHelperTest {
	
	private static final int IMAGE_WIDTH = 300;
	
	private static final int IMAGE_HEIGHT = 100;
	
	private static final int ADDITIONAL_EDGE_SIZE = 5;
	
	private static final String IMAGE_PATH = "/image-300-100.jpg";
	
	private ImageEdgeResizeHelper helper = new ImageEdgeResizeHelper();
	
	@Test
	public void shouldCopyTopEdgeOfImage() throws IOException {
		BufferedImage sourceImage = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
		
		BufferedImage result = helper.addEdgesToImage(sourceImage, 5);
		
		assertThat(result.getWidth()).isEqualTo(IMAGE_WIDTH + 2 * ADDITIONAL_EDGE_SIZE);
		assertThat(result.getHeight()).isEqualTo(IMAGE_HEIGHT + 2 * ADDITIONAL_EDGE_SIZE);
		for (int i = 0; i < IMAGE_WIDTH + 2 * ADDITIONAL_EDGE_SIZE; ++i) {
			int firstPixel = result.getRGB(i, 0);
			for (int j = 1; j <= ADDITIONAL_EDGE_SIZE; ++j) {
				assertThat(result.getRGB(i, j)).isEqualTo(firstPixel);
			}
		}
	}
	
	@Test
	public void shouldCopyBottomEdgeOfImage() throws IOException {
		BufferedImage sourceImage = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
		
		BufferedImage result = helper.addEdgesToImage(sourceImage, ADDITIONAL_EDGE_SIZE);
		
		assertThat(result.getWidth()).isEqualTo(IMAGE_WIDTH + 2 * ADDITIONAL_EDGE_SIZE);
		assertThat(result.getHeight()).isEqualTo(IMAGE_HEIGHT + 2 * ADDITIONAL_EDGE_SIZE);
		for (int i = 0; i < IMAGE_WIDTH + 2 * ADDITIONAL_EDGE_SIZE; ++i) {
			int firstPixel = result.getRGB(i, IMAGE_HEIGHT + ADDITIONAL_EDGE_SIZE - 1);
			for (int j = IMAGE_HEIGHT + ADDITIONAL_EDGE_SIZE; j < IMAGE_HEIGHT + 2 * ADDITIONAL_EDGE_SIZE; ++j) {
				assertThat(result.getRGB(i, j)).isEqualTo(firstPixel);
			}
		}
	}
	
	@Test
	public void shouldCopyLeftEdgeOfImage() throws IOException {
		BufferedImage sourceImage = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
		
		BufferedImage result = helper.addEdgesToImage(sourceImage, ADDITIONAL_EDGE_SIZE);
		
		assertThat(result.getWidth()).isEqualTo(IMAGE_WIDTH + 2 * ADDITIONAL_EDGE_SIZE);
		assertThat(result.getHeight()).isEqualTo(IMAGE_HEIGHT + 2 * ADDITIONAL_EDGE_SIZE);
		for (int j = 0; j < IMAGE_HEIGHT + 2 * ADDITIONAL_EDGE_SIZE; ++j) {
			int firstPixel = result.getRGB(0, j);
			for (int i = 1; i <= ADDITIONAL_EDGE_SIZE; ++i) {
				assertThat(result.getRGB(i, j)).isEqualTo(firstPixel);
			}
		}
	}
	
	@Test
	public void shouldCopyRightEdgeOfImage() throws IOException {
		BufferedImage sourceImage = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
		
		BufferedImage result = helper.addEdgesToImage(sourceImage, ADDITIONAL_EDGE_SIZE);
		
		assertThat(result.getWidth()).isEqualTo(IMAGE_WIDTH + 2 * ADDITIONAL_EDGE_SIZE);
		assertThat(result.getHeight()).isEqualTo(IMAGE_HEIGHT + 2 * ADDITIONAL_EDGE_SIZE);
		for (int j = 0; j < IMAGE_HEIGHT + 2 * ADDITIONAL_EDGE_SIZE; ++j) {
			int firstPixel = result.getRGB(IMAGE_WIDTH + ADDITIONAL_EDGE_SIZE - 1, j);
			for (int i = IMAGE_WIDTH + ADDITIONAL_EDGE_SIZE; i < IMAGE_WIDTH + 2 * ADDITIONAL_EDGE_SIZE; ++i) {
				assertThat(result.getRGB(i, j)).isEqualTo(firstPixel);
			}
		}
	}
	
	@Test
	public void shouldRemoveEdgesFromImage() throws IOException {
		BufferedImage sourceImage = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
		
		int upperLeftPixelBefore = sourceImage.getRGB(ADDITIONAL_EDGE_SIZE, ADDITIONAL_EDGE_SIZE);
		int lowerRightPixelBefore = sourceImage.getRGB(
				IMAGE_WIDTH - ADDITIONAL_EDGE_SIZE - 1,
				IMAGE_HEIGHT - ADDITIONAL_EDGE_SIZE - 1
				);
		BufferedImage result = helper.removeEdgesFromImage(sourceImage, ADDITIONAL_EDGE_SIZE);
		int upperLeftPixelAfter = result.getRGB(0, 0);
		int lowerRightPixelAfter = result.getRGB(
				IMAGE_WIDTH - 2 * ADDITIONAL_EDGE_SIZE - 1,
				IMAGE_HEIGHT - 2 * ADDITIONAL_EDGE_SIZE - 1
				);
		
		assertThat(result.getWidth()).isEqualTo(IMAGE_WIDTH - 2 * ADDITIONAL_EDGE_SIZE);
		assertThat(result.getHeight()).isEqualTo(IMAGE_HEIGHT - 2 * ADDITIONAL_EDGE_SIZE);
		assertThat(upperLeftPixelBefore).isEqualTo(upperLeftPixelAfter);
		assertThat(lowerRightPixelBefore).isEqualTo(lowerRightPixelAfter);
	}
	
	@Test
	public void shouldNotChangeImageWhenAddingAndRemovingTheSameAmountOfEdges() throws IOException {
		BufferedImage sourceImage = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
		
		BufferedImage withEdges = helper.addEdgesToImage(sourceImage, 5);
		BufferedImage result = helper.removeEdgesFromImage(withEdges, 5);
		
		assertThat(sourceImage.getWidth()).isEqualTo(result.getWidth());
		assertThat(sourceImage.getHeight()).isEqualTo(result.getHeight());
		for (int i = 0; i < result.getWidth(); ++i) {
			for (int j = 0; j < result.getHeight(); ++j) {
				assertThat(sourceImage.getRGB(i, j)).isEqualTo(result.getRGB(i, j));
			}
		}
	}
}
