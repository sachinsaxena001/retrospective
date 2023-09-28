package com.retroapp.retrospective.services;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.retroapp.retrospective.Exceptions.RetorspectiveAlreadyExistsException;
import com.retroapp.retrospective.Repository.FeedbackEntity;
import com.retroapp.retrospective.Repository.RetrospectiveEntity;
import com.retroapp.retrospective.Repository.Retrospectiverepository;
import com.retroapp.retrospective.model.Feedback;
import org.springframework.stereotype.Component;
import com.retroapp.retrospective.model.Retrospective;

@Component
public class RetrospectiveService implements IRetrospectiveService {

  @Override
  public void addRetrospective(Retrospective retrospective) {

    if (Retrospectiverepository.retrospectiveEntities.size() > 0
        && Retrospectiverepository.retrospectiveEntities.stream().anyMatch(i -> i.Name != null)
        && Retrospectiverepository.retrospectiveEntities.stream().anyMatch(i -> i.Name.contains(retrospective.Name))

    ) {
      throw new RetorspectiveAlreadyExistsException("Record already exists!");
    } else if (retrospective.Date == null)
      throw new RetorspectiveAlreadyExistsException("Date is required!");
    else if (retrospective.Participants.size() == 0)
      throw new RetorspectiveAlreadyExistsException("Participants are required!");
    else {
      RetrospectiveEntity retrospectiveEntity = new RetrospectiveEntity();
      retrospectiveEntity.Name = retrospective.Name;
      retrospectiveEntity.Date = retrospective.Date != null && !retrospective.Date.isEmpty()
          ? LocalDate.parse(retrospective.Date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
          : null;
      retrospectiveEntity.Summary = retrospective.Summary;
      retrospectiveEntity.Name = retrospective.Name;
      retrospectiveEntity.Participants = retrospective.Participants;
      Retrospectiverepository.retrospectiveEntities.add(retrospectiveEntity);
    }
  }

  //

  @Override
  public List<RetrospectiveEntity> getRetrospectives() {

    return Retrospectiverepository.retrospectiveEntities;
  }

  @Override
  public List<RetrospectiveEntity> addFeedback(String name, Feedback feedback) {

    RetrospectiveEntity retrospectiveEntity = Retrospectiverepository.retrospectiveEntities.stream()
        .filter(i -> i.Name.equals(name)).findFirst().orElse(null);
    FeedbackEntity feedbackItem = new FeedbackEntity();
    feedbackItem.Name = feedback.Name;
    feedbackItem.Body = feedback.Body;
    feedbackItem.Type = feedback.Type;
    retrospectiveEntity.Feedbacks.add(feedbackItem);
    return Retrospectiverepository.retrospectiveEntities;
  }

  @Override
  public void updateFeedback(String name, Feedback feedback) {
    FeedbackEntity feedbackEntity = Retrospectiverepository.retrospectiveEntities.stream()
        .filter(element -> element.Name.equals(name))
        .flatMap(element -> element.Feedbacks.stream())
        .filter(f -> f.Name.equals(feedback.Name)).findFirst().orElse(null);
    feedbackEntity.Body = feedback.Body;
    feedbackEntity.Type = feedback.Type;
  }

  @Override
  public List<List<RetrospectiveEntity>> getPages(Integer pageNum, Integer pageSize) {

    List<RetrospectiveEntity> list = new ArrayList<RetrospectiveEntity>(Retrospectiverepository.retrospectiveEntities);
    if (pageSize == null || pageSize <= 0 || pageSize > list.size())
      pageSize = list.size();
    int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);
    List<List<RetrospectiveEntity>> pages = new ArrayList<List<RetrospectiveEntity>>(numPages);
    pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
    return pages;
  }

  @Override
  public List<List<RetrospectiveEntity>> getPagesbyDate(String date, Integer pageNum, Integer pageSize) {
    List<RetrospectiveEntity> retroList = Retrospectiverepository.retrospectiveEntities.stream()
        .filter(i -> i.Date.equals(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")))).toList();
    List<RetrospectiveEntity> list = new ArrayList<RetrospectiveEntity>(retroList);
    if (pageSize == null || pageSize <= 0 || pageSize > list.size())
      pageSize = list.size();
    int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);
    List<List<RetrospectiveEntity>> pages = new ArrayList<List<RetrospectiveEntity>>(numPages);
    pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
    return pages;
  }

}
