package Effective_Java;

import java.util.Objects;

public class Builder_Model_Pizza {
    private String band;
    private Integer size;
    private Boolean isSpicy;
    private Boolean addCheese;

    public static class Builder {
        private String band;
        private Integer size;

        private Boolean isSpicy = false;
        private Boolean addCheese = false;


        // 流式构建函数
        public Builder(String band, Integer size) {
            this.band = band;
            this.size = size;
        }

        public Builder isSpicy(Boolean val){
            isSpicy = val;
            return this;
        }

        public Builder addCheese(Boolean val){
            addCheese = val;
            return this;
        }

        public Builder_Model_Pizza build(){
            return new Builder_Model_Pizza(this);
        }
    }

    // 自己类里的类的私类，自己可以拿到！
    private Builder_Model_Pizza(Builder builder) {
        band = builder.band;
        size = builder.size;
        isSpicy = builder.isSpicy;
        addCheese = builder.addCheese;
    }
}
