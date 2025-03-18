package com.example.domains.entities.dtos;

import com.example.domains.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LanguageDTO {
    private int languageId;
    private String name;

    public LanguageDTO from(Language source) {
        return new LanguageDTO(source.getLanguageId(), source.getName());
    }

    public Language from(LanguageDTO source) {
        return new Language(source.getLanguageId(), source.getName());
    }
}
