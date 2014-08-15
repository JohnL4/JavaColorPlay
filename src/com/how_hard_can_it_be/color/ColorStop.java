package com.how_hard_can_it_be.color;

import javafx.scene.paint.Color;

/**
 * Holds color and position of a point in the gradient.  Both can be changed. Position is in range [0.0..1.0].
 * 
 * @author john
 *
 */
public class ColorStop
{
    /**
     * The color of the gradient (or elevation map) at this point in the interval [0.0, 1.0].
     */
    private Color color;
    
    /**
     * Position from left.  0.0 means "at the beginning of the gradient"; 1.0 means "at the end of the gradient".
     */
    private double position;

    public ColorStop(Color aColor, double aPosition)
    {
        super();
        color = aColor;
        position = aPosition;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color aColor)
    {
        color = aColor;
    }

    public double getPosition()
    {
        return position;
    }

    public void setPosition(double aPosition)
    {
        position = aPosition;
    }
    
    
}
