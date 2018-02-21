package com.org.effective.lambda;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Dish {
    private DishType dishType;
    private boolean vegetarian;
    private int calories;
    private String name;
    private double price;

    public enum DishType {
        Chinese,
        Western,
        Mideast;

        public Map<String, DishType> strMap2Enum() {
            return Stream.of(DishType.values()).collect(
                    toMap(Object::toString, e -> e)
            );
        }
    }

    public Dish(DishType dishType, boolean vegetarian, int calories, String name, double price) {
        this.dishType = dishType;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.name = name;
        this.price = price;

    }

    public Dish(double price) {
        this.price = price;
        this.dishType = DishType.Chinese;
        this.name = "default name";
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishType=" + dishType +
                ", vegetarian=" + vegetarian +
                ", calories=" + calories +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
