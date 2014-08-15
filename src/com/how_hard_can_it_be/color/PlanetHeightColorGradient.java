package com.how_hard_can_it_be.color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;

/**
 * Captures elevation mappings for a planet map generated by program 'planet'. It's essentially a gradient.
 * @see http://topps.diku.dk/torbenm/maps.msp 
 * @author john
 *
 */
public class PlanetHeightColorGradient
{
    private List<ColorStop> waterColorStops;
    private List<ColorStop> landColorStops;
    
//    private LinearGradient paintGradient;
    
    /**
     * Position is in range [0.0..1.0], even though, in the ultimate output, it'll scaled down to (essentially)
     * the range [0.0..5.0].
     * 
     * @param aColor
     * @param aPosition
     */
    public void addWaterColorStop( Color aColor, double aPosition)
    {
        if (waterColorStops == null)
            waterColorStops = new LinkedList<ColorStop>();
        waterColorStops.add( new ColorStop( aColor, aPosition));
    }
    
    public void addLandColorStop( Color aColor, double aPosition)
    {
        if (landColorStops == null)
            landColorStops = new LinkedList<ColorStop>();
        landColorStops.add( new ColorStop( aColor, aPosition));
    }
    
    /**
     * Returns the {@link ColorStop} closest to the given location, within the given epsilon, or null if there are no
     * ColorStops w/in the given epsilon.
     *  
     * @param aLocation
     * @param anEpsilon
     * @return
     */
    public ColorStop findColorStop( double aLocation, double anEpsilon)
    {
        if (anEpsilon < 0)
            throw new IllegalArgumentException( String.format( "epsilon must be >= 0.0, was %g", anEpsilon));
        
        ColorStop closestCandidate = null;
        
        // How close is the closest candidate?  Pick a starting value guaranteed to be far away.
        double closestCandidateDelta = 2.0; 
        
        for (ColorStop stop : waterColorStops)
        {
            double delta = Math.abs(aLocation - stop.getPosition());
            if (delta < anEpsilon)
            {
                if (closestCandidate == null)
                {
                    closestCandidate = stop;
                    closestCandidateDelta = delta;
                }
                else
                {
                    if (delta < closestCandidateDelta)
                    {
                        closestCandidate = stop;
                        closestCandidateDelta = delta;
                    }
                }
            }
        }
        
        return closestCandidate;
    }

    /**
     * Returns a Paint object that can be used as a gradient.
     * @return
     */
    public Paint getWaterGradient()
    {
        // Was going to be a class member, but I don't know how to invalidate it if the user edits a color stop.
        LinearGradient paintGradient = null;
        if (paintGradient == null)
        {
            LinkedList<Stop> stopsList = new LinkedList<Stop>();
            for (ColorStop colorStop : waterColorStops)
            {
                stopsList.add( new Stop( colorStop.getPosition(), colorStop.getColor()));
            }
            paintGradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stopsList);
        }
        return paintGradient;
        
    }

    public Paint getLandGradient()
    {
        LinearGradient paintGradient;
        LinkedList<Stop> stopsList = new LinkedList<Stop>();
        for (ColorStop colorStop : landColorStops)
        {
            stopsList.add( new Stop( colorStop.getPosition(), colorStop.getColor()));
        }
        paintGradient = new LinearGradient(0,0,1,0,true, CycleMethod.NO_CYCLE, stopsList);
        return paintGradient;
    }
}
