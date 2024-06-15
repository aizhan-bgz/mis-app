package itacademy.misbackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sex {
    MALE("Мужчина"), FEMALE("Женщина");

    private final String gender;
}
