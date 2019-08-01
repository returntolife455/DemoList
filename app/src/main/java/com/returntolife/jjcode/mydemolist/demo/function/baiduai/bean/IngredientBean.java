package com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/8/1.
 * Email:hejj@mama.cn
 * des:
 */
public class IngredientBean {

    /**
     * log_id : 2961154810640606849
     * result_num : 5
     * result : [{"score":0.9944882988929749,"name":"冬瓜"},{"score":0.0032987932208925486,"name":"青木瓜"},{"score":8.471961482428014E-4,"name":"西葫芦"},{"score":2.842599933501333E-4,"name":"青柚"},{"score":2.69905140157789E-4,"name":"香瓜"}]
     */

    private long log_id;
    private int result_num;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * score : 0.9944882988929749
         * name : 冬瓜
         */

        private double score;
        private String name;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
