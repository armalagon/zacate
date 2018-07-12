package com.zacate.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ErrorCollector {

    private final List<Exception> errors;

    public ErrorCollector() {
        this.errors = new ArrayList<>();
    }

    public void add(Exception ex) {
        errors.add(Objects.requireNonNull(ex, "ex"));
    }

    public void addAll(Collection<Exception> exs) {
        errors.addAll(Objects.requireNonNull(exs, "exs"));
    }

    public List<Exception> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public List<String> getMessages() {
        return errors.stream().map(Exception::getMessage).collect(Collectors.toList());
    }

}
