package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.infrastructure.NotFoundException;
import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> getAllSessions(){
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session getSessionById(@PathVariable Long id){
        return sessionRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session createSession(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id){
        // Delete child speaker items in sessions_speakers table
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)

    public Session updateSession(@PathVariable Long id, @RequestBody final Session session) throws NotFoundException {
        var existingSession = sessionRepository.getOne(id);
        if (existingSession == null){
            throw new NotFoundException();
        }
        BeanUtils.copyProperties(session,existingSession,"sessionId");
        return sessionRepository.saveAndFlush(existingSession);

    }

//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public void whenNotFound(){
//    }

}
