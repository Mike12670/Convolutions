
package mikefitzgibbon.convolutions;

import java.awt.image.BufferedImage;

public class ConvolveNoOp extends Convolve{

    public ConvolveNoOp(Kernel kernel) {
        super(kernel);
    }
    
    /**
     * 
     * @param imgX Pixel x from the original image that will be convolved.
     * @param imgY Pixel y from the original image that will be convolved.
     * @param in The original image that will be convolved.
     * @return An int that represents the color in a single pixel.
     */
    @Override
    protected int getConvolvedPixel(int imgX, int imgY, BufferedImage in){
        double a = 0, r = 0, g = 0, b = 0;
        for(int matrixY = 0 ; matrixY < kernel.diameter ; matrixY++){
            for(int matrixX = 0 ; matrixX < kernel.diameter ; matrixX++){
                int indexX = imgX + matrixX - kernel.diameter / 2, 
                        indexY = imgY + matrixY - kernel.diameter / 2;
                if(
                        indexX < 0 || 
                        indexX >= kernel.diameter || 
                        indexY < 0 || 
                        indexY >= kernel.diameter)
                    return in.getRGB(imgX, imgY);
                int c = getPixel(indexX, indexY, in);
                double ff = kernel.getMatrix()[matrixX][matrixY];
                a += ff * ((c >> 24) & 0xff);
                r += ff * ((c >> 16) & 0xff);
                g += ff * ((c >> 8) & 0xff);
                b += ff * (c & 0xff);
            }
        }
        return ((int)a << 24) | ((int)r << 16) | ((int)g << 8) | (int)b;
    }

    /**
     * Does not change the pixel as it comes from the image.
     * @param indexX X location in image.
     * @param indexY Y location in image.
     * @param in Image to be convolved.
     * @return The unaltered pixel.
     */
    @Override
    protected int getPixel(int indexX, int indexY, BufferedImage in) {
        return in.getRGB(indexX, indexY);
    }

}
