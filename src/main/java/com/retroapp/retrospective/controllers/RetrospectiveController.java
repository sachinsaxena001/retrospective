package com.retroapp.retrospective.controllers;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.retroapp.retrospective.Exceptions.RetorspectiveAlreadyExistsException;
import com.retroapp.retrospective.Repository.RetrospectiveEntity;
import com.retroapp.retrospective.model.Feedback;
import com.retroapp.retrospective.model.Retrospective;
import com.retroapp.retrospective.services.IRetrospectiveService;



 

@RestController
 class RetrospectiveController {
  private static final Logger logger = LogManager.getLogger(RetrospectiveController.class);
    
 
  private IRetrospectiveService service;

  RetrospectiveController(IRetrospectiveService service) {
    this.service = service;
  }

  @GetMapping("/getretro")
  public List<RetrospectiveEntity> getretro() {
    logger.debug("Debugging log");
        logger.info("Info log");
        logger.warn("Hey, This is a warning!");
        logger.error("Oops! We have an Error. OK");
        logger.fatal("Damn! Fatal error. Please fix me.");
    return service.getRetrospectives();
    

  }

  @PostMapping(value = "/addRetrospective")
  public ResponseEntity<Retrospective> addRetrospective(@RequestBody Retrospective retro) {
    try {
      service.addRetrospective(retro);
      
      return new ResponseEntity<Retrospective>(retro, HttpStatus.OK);
    } catch (Exception ex) {
      throw new RetorspectiveAlreadyExistsException(ex.getMessage());
    }

  }

  @PutMapping("addFeedback/{name}")
  public ResponseEntity<String> addFeedback(@PathVariable String name, @RequestBody Feedback feedback) {
    try {
      service.addFeedback(name, feedback);
      return new ResponseEntity<String>("Updated!", HttpStatus.OK);
    } catch (Exception ex) {
      throw new RetorspectiveAlreadyExistsException(ex.getMessage());
    }

  }

  @PutMapping("updateFeedback/{name}")
  public ResponseEntity<String> updateFeedback(@PathVariable String name, @RequestBody Feedback feedback) {
    try {
      service.updateFeedback(name, feedback);
      return new ResponseEntity<String>("Updated!", HttpStatus.OK);
    } catch (Exception e) {
      throw new RetorspectiveAlreadyExistsException(e.getMessage());
    }
  }
  @GetMapping("searchPagination/{pageNum}/{pagesize}")
  public List<List<RetrospectiveEntity>> searchPagination(@PathVariable Integer pageNum , @PathVariable Integer pagesize)
  {
    return service.getPages(pageNum, pagesize);
  }

  @GetMapping(value="searchPaginationDate/{date}/{pageNum}/{pagesize}/{type}")
  public ResponseEntity<List<List<RetrospectiveEntity>>> searchPaginationDate(@PathVariable String date, @PathVariable Integer pageNum , @PathVariable Integer pagesize, @PathVariable Integer type)
  {
     
    HttpHeaders headers = new HttpHeaders();

    if(type==1)
      headers.setContentType(MediaType.APPLICATION_XML);
      else
      headers.setContentType(MediaType.APPLICATION_JSON);

    ResponseEntity<List<List<RetrospectiveEntity>>> entityModel
    = new ResponseEntity<>(service.getPagesbyDate(date,pageNum, pagesize), headers,
                           HttpStatus.CREATED);
    return entityModel;
  }

}