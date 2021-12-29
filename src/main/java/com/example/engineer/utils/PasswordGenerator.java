package com.example.engineer.utils;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PasswordGenerator {

    private final String lowerCase = "abcdefghijkmnopqrstuvwxyz";
    private final String upperCase = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    private final String marks = ".,<>{}[]!?@#$%&-_=+";
    private final Random random = new Random();

    public String generateDefaultPassword() {
        final List<Character> builder = new ArrayList<>(15);
        for (int i = 0; i < 5; i++) {
            builder.add(getRandomChar(lowerCase));
            builder.add(getRandomChar(upperCase));
            builder.add(getRandomChar(marks));
        }
        Collections.shuffle(builder, random);
        return builder.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private char getRandomChar(String chars) {
        return chars.charAt(random.nextInt(chars.length()));
    }

    public static void main(String[] args) {
        PasswordGenerator generator = new PasswordGenerator();
        for (int i = 0; i < 10; i++) {
            System.out.println(generator.generateDefaultPassword());
        }
    }
}
