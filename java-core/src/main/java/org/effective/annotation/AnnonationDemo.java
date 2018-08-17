package org.effective.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class AnnonationDemo {

    @Retention(RUNTIME)
    @Target(FIELD)
    static @interface Label {
        String value() default "";
    }

    @Retention(RUNTIME)
    @Target(FIELD)
    static @interface Format {
        String pattern() default "yyyy-MM-dd HH:mm:ss";

        String timezone() default "GMT+8";
    }

    static class Student {
        @Label("name")
        String name;

        @Label("birthday")
        @Format(pattern = "yyyy/MM/dd")
        Date born;

        double score;

        public Student(String name, Date born, double score) {
            this.name = name;
            this.born = born;
            this.score = score;
        }
    }

    static class SimpleFormat {
        public static String format(Object o) {
            try {
                Class<?> klass = o.getClass();

                StringBuilder sb = new StringBuilder();
                for (Field f : klass.getDeclaredFields()) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }

                    Label label = f.getAnnotation(Label.class);
                    String name = label == null
                            ? f.getName()
                            : label.value();
                    Object value = f.get(o);

                    if (value != null && f.getType() == Date.class) {
                        value = formatDate(f, value);
                    }

                    sb.append(name + ": " + value + "\n");
                }

                return sb.toString();
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex.toString());
            }
        }

        private static Object formatDate(Field f, Object value) {
            Format format = f.getAnnotation(Format.class);
            if (format != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(format.pattern());
                sdf.setTimeZone(TimeZone.getTimeZone(format.timezone()));
                return sdf.format(value);
            }

            return value;
        }
    }

    public static void main(String[] args) throws ParseException, IllegalAccessException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Student student = new Student("allen", sdf.parse("1971-11-11"), 90.5);
        System.out.println(SimpleFormat.format(student));
    }

}
