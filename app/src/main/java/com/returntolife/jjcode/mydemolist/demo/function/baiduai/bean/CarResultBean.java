package com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/8/1.
 * Email:455hejiajun@gmail
 * des:
 */
public class CarResultBean {


    /**
     * log_id : 789329771458492193
     * location_result : {"width":773,"top":71,"height":330,"left":46}
     * result : [{"score":0.9989306926727295,"name":"雪佛兰科迈罗","year":"2016-2017"},{"score":6.945378263480961E-4,"name":"道奇挑战者(Charger)","year":"2017"},{"score":9.71206754911691E-5,"name":"起亚GT","year":"无年份信息"},{"score":8.93678588909097E-5,"name":"雪佛兰Code","year":"无年份信息"},{"score":2.7398948077461682E-5,"name":"日产IDX","year":"无年份信息"}]
     * color_result : 黄色
     */

    private long log_id;
    private LocationResultBean location_result;
    private String color_result;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public LocationResultBean getLocation_result() {
        return location_result;
    }

    public void setLocation_result(LocationResultBean location_result) {
        this.location_result = location_result;
    }

    public String getColor_result() {
        return color_result;
    }

    public void setColor_result(String color_result) {
        this.color_result = color_result;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class LocationResultBean {
        /**
         * width : 773
         * top : 71
         * height : 330
         * left : 46
         */

        private int width;
        private int top;
        private int height;
        private int left;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }
    }

    public static class ResultBean {
        /**
         * score : 0.9989306926727295
         * name : 雪佛兰科迈罗
         * year : 2016-2017
         */

        private double score;
        private String name;
        private String year;

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

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
