package vsp25.pa.gui;

import java.awt.*;

/**
 * An instance represents an object that can be drawn with a Graphics2D.
 */
public interface Drawable {

    /**
     * Draw this Drawable on g. g's settings will not be changed.
     */
    public void draw(Graphics2D g);
}
