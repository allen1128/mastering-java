package org.effective.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SerializationDemoStudent {
    private String name;
    private List<Integer> orderedGrades;

    public SerializationDemoStudent() {
    }

    public SerializationDemoStudent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getOrderedGrades() {
        return orderedGrades;
    }

    public void setOrderedGrades(List<Integer> grades) {
        this.orderedGrades = grades;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SerializationDemoStudent that = (SerializationDemoStudent) o;

        if (name != null
                ? !name.equals(that.name)
                : that.name != null) return false;
        return orderedGrades != null
                ? orderedGrades.equals(that.orderedGrades)
                : that.orderedGrades == null;
    }

    @Override
    public int hashCode() {
        int result = name != null
                ? name.hashCode()
                : 0;
        result = 31 * result + (orderedGrades != null
                ? orderedGrades.hashCode()
                : 0);
        return result;
    }

    public static void main(String[] args) throws IOException {
        SerializationDemoStudent studentDemo = new SerializationDemoStudent("allen");
        studentDemo.setOrderedGrades(Arrays.asList(new Integer[]{1, 2, 3}));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String output = objectMapper.writeValueAsString(studentDemo);
        System.out.println(output);

        objectMapper.writeValue(new File("temp.json"), studentDemo);

        SerializationDemoStudent studentDemoFromFile = objectMapper.readValue(new File("temp.json"), SerializationDemoStudent.class);

        if (studentDemo.equals(studentDemoFromFile)) {
            System.out.println("serialization successful.");
        } else {
            System.out.println("serialization unsuccessful.");
        }

    }

}
