package com.example.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.models.Persona;
import com.example.models.PersonaDTO;

public class PersonalItemProcessor implements ItemProcessor<PersonaDTO, Persona> {
    private static final Logger log = LoggerFactory.getLogger(PersonalItemProcessor.class);

    @Override
    public Persona process(PersonaDTO item) throws Exception {
        if (item.getId() % 2 == 0 && "Male".equals(item.getSexo())) return null;
        Persona rslt = new Persona(item.getId(), item.getAppellidos() + ", " + item.getNombre(), item.getCorreo(), item.getIp());
        log.info("Procesando: " + item);
        return rslt;
    }   
} 
