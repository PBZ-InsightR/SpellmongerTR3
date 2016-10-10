package edu.insightr.spellmonger;

/**
 * Created by Stanislas Daniel Claude D on 21/09/2016.
 * Defines a standard beast
 */
public abstract class Beast extends PlayCard {

    private String type;




    /**
     * @param type defined the beast as  a bear an eagle or a wolf
     * @param damage is an heritage from the constructor of PlayCard

     *
     */
    public Beast(int damage, String type) {
        super("creature",damage);
        this.type = type;
       /**
        * if we need in the next steps a switch , find it bellow:
        * switch(type_switch){
            case wolf: type="wolf";
                break;
            case bear: type="bear";
                break;
            case eagle :type="eagle";
                brak;
        }**/

    }

    public String getType() {
        return this.type;
    }
    @Override
    public String toString() {

        return "This Card named"+ this.name+"deals" + this.damage + " damage ."+"This is a beast"+this.type;
    }
    /**
     * @return description string of the class.
     */
}
