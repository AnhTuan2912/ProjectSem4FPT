/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package fpt.aptech.Client.component;

import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, formatter);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.format(formatter);
    }
}
