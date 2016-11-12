package io.eidukas.calhacks;

/**
 * Created by solmoms on 11/12/2016.
 */

public class FoodList {
    private int totalResults;
    private FoodId[] results;

    public int getTotalResults() {
        return totalResults;
    }

    public FoodId[] getResults() {
        return results;
    }

    public class FoodId {
        private int id;

        public int getId() {
            return id;
        }
    }
}
