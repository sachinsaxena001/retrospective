package com.retroapp.retrospective.services;

import java.util.List;

import com.retroapp.retrospective.Repository.RetrospectiveEntity;
import com.retroapp.retrospective.model.Feedback;
import com.retroapp.retrospective.model.Retrospective;

public interface IRetrospectiveService {

  void addRetrospective(Retrospective retrospective);
  List<RetrospectiveEntity> getRetrospectives();
  List<RetrospectiveEntity> addFeedback(String Name, Feedback Feedback);
  void updateFeedback(String Name, Feedback Feedback);
  List<List<RetrospectiveEntity>> getPages(Integer numPages, Integer pageSize);
  List<List<RetrospectiveEntity>> getPagesbyDate(String Date, Integer pageNum, Integer pageSize);
  
   
}