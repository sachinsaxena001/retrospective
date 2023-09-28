package com.retroapp.retrospective.Repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
@XmlRootElement(name="Retrospective")
public class RetrospectiveEntity 
{
    public RetrospectiveEntity(){
        Feedbacks = new ArrayList<FeedbackEntity>() {};
    }
    @XmlElement
    public String Name; 
    @XmlElement
    public String Summary;
    @XmlElement
    public LocalDate  Date; 
    @XmlElement
    public List<String> Participants;
    @XmlElement
    public List<FeedbackEntity> Feedbacks;
    
}