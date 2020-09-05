package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.infrastructure.NotFoundException;
import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/speakers")
public class SpeakersController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> getAllSpeakers(){
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker getSpeakerById(@PathVariable Long id){
        return speakerRepository.getOne(id);
    }

//    @GetMapping
//    @RequestMapping("{id}/sessions")
//    public List<Session> getSessionsBySpeakerId(@PathVariable Long Id){
//        return speakerRepository.findAll()
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Speaker createSpeaker(@RequestBody Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteSpeaker(@PathVariable Long id){
        speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    private Speaker updateSpeakerById(@PathVariable Long id, @RequestBody Speaker speaker) throws NotFoundException {
        var existingSpeaker = speakerRepository.getOne(id);
        if (existingSpeaker == null){
            throw  new NotFoundException();
        }
        BeanUtils.copyProperties(speaker, existingSpeaker, "speakerId");
        return speakerRepository.saveAndFlush(existingSpeaker);

    }


}
