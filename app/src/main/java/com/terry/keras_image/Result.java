package com.terry.keras_image;

import java.util.List;

/**
 * *
 * name     Result
 * Creater  Terry
 * time     2018/4/5
 * *
 **/

public class Result {


    /**
     * predictions : [{"label":"monitor","probability":0.16339100897312164},{"label":"screen","probability":0.15737320482730865},{"label":"shower_curtain","probability":0.07531516999006271},{"label":"toilet_seat","probability":0.07375624030828476},{"label":"electric_ray","probability":0.05510254204273224}]
     * success : true
     */

    public boolean success;
    public List<PredictionsBean> predictions;

    public static class PredictionsBean {
        /**
         * label : monitor
         * probability : 0.16339100897312164
         */

        public String label;
        public double probability;
    }
}
