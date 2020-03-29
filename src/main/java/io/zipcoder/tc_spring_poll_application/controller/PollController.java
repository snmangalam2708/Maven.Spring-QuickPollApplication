package io.zipcoder.tc_spring_poll_application.controller;

import io.zipcoder.tc_spring_poll_application.domain.Poll;
import io.zipcoder.tc_spring_poll_application.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class PollController {

    PollRepository pollRepository;

    @Autowired
    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

//    @RequestMapping(value="/polls", method= RequestMethod.GET)
    @GetMapping("/polls")
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

//    @RequestMapping(value="/polls", method=RequestMethod.POST)
    @PostMapping("/polls")
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
        poll = pollRepository.save(poll);
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.GET)
    @GetMapping("/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        Poll p = pollRepository.findOne(pollId);
        return new ResponseEntity<> (p, HttpStatus.OK);
    }

//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.PUT)
    @PutMapping("/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        // Save the entity
        Poll p = pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @RequestMapping(value="/polls/{pollId}", method=RequestMethod.DELETE)
    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        pollRepository.delete(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
