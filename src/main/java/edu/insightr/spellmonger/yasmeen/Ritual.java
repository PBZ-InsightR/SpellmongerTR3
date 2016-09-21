package edu.insightr.spellmonger.yasmeen;

/**
 * Created by Yasmeen on 21/09/2016.
 * isCurse (curse or blessing)
 */
public class Ritual {
        private boolean isCurse;
        private int lifePoints;

        public Ritual(boolean isCurse, int lifePoints){
            this.isCurse=isCurse;
            this.lifePoints=lifePoints;
        }

        public int getLifePoints(){return this.lifePoints;}

        public void updatePoints() {
            if (isCurse && lifePoints != 0) {
                lifePoints -= 3;
            }
            if (!isCurse) {
                lifePoints += 3;
            }
        }
}
