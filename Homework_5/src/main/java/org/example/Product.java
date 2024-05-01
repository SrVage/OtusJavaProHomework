package org.example;

public final class Product {
    private final int id;
    private final String title;
    private final String description;
    private final double cost;
    private final double weight;
    private final int width;
    private final int length;
    private final int height;

    private Product(Builder builder){
        id = builder.id;
        title = builder.title;
        description = builder.description();
        cost = builder.cost();
        weight = builder.weight();
        width = builder.width();
        length = builder.length();
        height = builder.height();
    }

    public static Builder builder(){
        return new Builder();
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public double getWeight() {
        return weight;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", weight=" + weight +
                ", width=" + width +
                ", length=" + length +
                ", height=" + height +
                '}';
    }

    public static final class Builder{
        private Builder(){}

        private int id;
        private String title;
        private String description;
        private double cost;
        private double weight;
        private int width;
        private int length;
        private int height;

        public int id() {
            return id;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public String title() {
            return title;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public String description() {
            return description;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public double cost() {
            return cost;
        }

        public Builder cost(double cost) {
            this.cost = cost;
            return this;
        }

        public double weight() {
            return weight;
        }

        public Builder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public int width() {
            return width;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public int length() {
            return length;
        }

        public Builder length(int length) {
            this.length = length;
            return this;
        }

        public int height() {
            return height;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Product build(){
            return new Product(this);
        }
    }
}
