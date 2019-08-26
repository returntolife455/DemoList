package com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean;

import java.util.List;

/**
 * Created by HeJiaJun on 2019/8/1.
 * Email:455hejiajun@gmail
 * des:
 */
public class AnimalBean {

    /**
     * log_id : 710922742194084833
     * result : [{"score":"0.963186","name":"哈士奇犬"},{"score":"0.0139839","name":"阿拉斯加雪橇犬"},{"score":"0.00449159","name":"德牧/德国牧羊犬"},{"score":"0.00125174","name":"因纽特犬"},{"score":"0.000955225","name":"威尔士柯基"},{"score":"0.000700313","name":"拉布拉多哈士奇"}]
     */

    private long log_id;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * score : 0.963186
         * name : 哈士奇犬
         */

        private String score;
        private String name;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                "score='" + score + '\'' +
                ", name='" + name + '\'' +
                '}';
        }
    }

    @Override
    public String toString() {
        return "AnimalBean{" +
            "log_id=" + log_id +
            ", result=" + result +
            '}';
    }
}
