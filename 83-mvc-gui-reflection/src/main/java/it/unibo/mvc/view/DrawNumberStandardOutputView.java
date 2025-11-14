package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * Stdout {@link DrawNumberView} implementation.
 */

public class DrawNumberStandardOutputView implements DrawNumberView {

    /**
     * {@inheritDoc} 
     */
    @Override
    public void setController(final DrawNumberController observer) {
        System.out.println("observer not attached, this view is output only"); //NOPMD
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public void start() {
        System.out.println("Start"); //NOPMD
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public void result(final DrawResult res) {
        System.out.println(res.getDescription()); //NOPMD
    }
}
