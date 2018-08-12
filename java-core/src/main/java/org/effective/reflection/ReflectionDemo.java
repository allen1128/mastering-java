package org.effective.reflection;

import java.lang.reflect.Field;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class ReflectionDemo {
    public static class Student {
        private String name;
        private double grade;
        private long studentId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getGrade() {
            return grade;
        }

        public void setGrade(Double grade) {
            this.grade = grade;
        }

        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Student student = (Student) o;

            if (Double.compare(student.grade, grade) != 0) return false;
            if (studentId != student.studentId) return false;
            return name != null
                    ? name.equals(student.name)
                    : student.name == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = name != null
                    ? name.hashCode()
                    : 0;
            temp = Double.doubleToLongBits(grade);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + (int) (studentId ^ (studentId >>> 32));
            return result;
        }
    }

    public static class SimpleMapper {
        public static String toString(Object o) throws IllegalAccessException {
            Class<?> klass = o.getClass();
            StringBuilder sb = new StringBuilder();
            sb.append(klass.getName() + "\n");

            for (Field f : klass.getDeclaredFields()) {
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                sb.append(f.getName() + "=" + f.get(o)
                        .toString() + "\n");
            }

            return sb.toString();
        }

        public static Object fromString(String str) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
            String[] lines = str.split("\n");
            Class<?> klass = Class.forName(lines[0]);
            Object o = klass.newInstance();
            if (lines.length > 1) {
                for (int i = 1; i < lines.length; i++){
                    String[] fv = lines[i].split("=");
                    Field field = klass.getDeclaredField(fv[0]);
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    setFieldValue(o, field, fv[1]);
                }
            }

            return o;
        }

        private static void setFieldValue(Object o, Field f, String v) throws IllegalAccessException {
            Class<?> type = f.getType();
            if (type == int.class) {
                f.setInt(o, parseInt(v));
            } else if (type == double.class) {
                f.setDouble(o, parseDouble(v));
            } else if (type == String.class) {
                f.set(o, v);
            } else if (type == long.class){
                f.setLong(o, parseLong(v));
            } else {
                //more
            }

        }
    }

    public static void main(String[] srgs) throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        Student student = new Student();
        student.setGrade(98d);
        student.setStudentId(1000001L);
        student.setName("allen");

        String str = SimpleMapper.toString(student);
        System.out.println(str);
        Student fromStr = (Student) SimpleMapper.fromString(str);
        String str2 = SimpleMapper.toString(fromStr);
        System.out.println(str2);

        if (student.equals(fromStr)) {
            System.out.println("mapping functioned correct");
        } else {
            System.out.println("mapping functioned incorrect");
        }


    }
}
