package edu.insightr.spellmonger.Interfaces;

/**
 * The Observer interface
 * Created by Hugues on 04/11/2016.
 */
public interface IObserver {

    /**
     * Updates the IObserver
     */
    void update(IObservable o);

    void setVisible(boolean setVisible);

    void disable();
    void endGame(String nameWinner);


}
