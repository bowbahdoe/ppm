package dev.mccue.ppm;

import dev.mccue.color.RGB255;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/// https://netpbm.sourceforge.net/doc/ppm.html
public final class PPM {
    private final RGB255[][] pixels;

    private PPM(RGB255[][] pixels) {
        this.pixels = pixels;
    }

    public static PPM create(int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("width must be > 0: " + width);
        }
        if (height <= 0) {
            throw new IllegalArgumentException("height must be > 0: " + height);
        }

        var ppm = new PPM(new RGB255[width][height]);

        for (RGB255[] row : ppm.pixels) {
            Arrays.fill(row, new RGB255(0, 0, 0));
        }

        return ppm;
    }

    public int width() {
        return pixels.length;
    }

    public int height() {
        return pixels[0].length;
    }

    public RGB255 get(int x, int y) {
        return pixels[x][y];
    }

    public void set(int x, int y, RGB255 color) {
        pixels[x][y] = color;
    }

    public void write(OutputStream os) throws IOException {
        write(os, PPMFormat.RAW);
    }

    public void write(OutputStream os, PPMFormat format) throws IOException {
        switch (format) {
            case PLAIN -> {
                os.write(("P3\n" + width() + " " + height() + "\n255\n").getBytes(StandardCharsets.UTF_8));
                for (RGB255[] row : pixels) {
                    for (RGB255 pixel : row) {
                        os.write((pixel.R() + " " + pixel.G() + " " + pixel.B() + "\n").getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
            case RAW -> {
                os.write(("P6\n" + width() + " " + height() + "\n255\n").getBytes(StandardCharsets.UTF_8));
                for (RGB255[] row : pixels) {
                    for (RGB255 pixel : row) {
                        os.write((byte) pixel.R());
                        os.write((byte) pixel.G());
                        os.write((byte) pixel.B());
                    }
                }
            }
        }
    }
}
