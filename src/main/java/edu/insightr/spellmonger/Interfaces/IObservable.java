package edu.insightr.spellmonger.Interfaces;

/**
 * The Observable interface
 * Created by Hugues on 04/11/2016.
 */
public interface IObservable {

    /**
     * Puts an IOBserver to the subscribed observers of the IObservable
     *
     * @param observer : an IObserver to subscribe
     * @return : true if the subscription was successful
     */
    boolean subscribe(IObserver observer);

    /*
    /**
     * Removes an IOBserver from the subscribed observers of the IObservable
     *
     * @param observer : an IObserver to unsubscribe
     * @return : true if the unsubscription was successful
     */
    /*
    boolean unsubscribe(IObserver observer);
    */

    /**
     * Notify all observers of a change
     */
    void notifyObserver();
}
