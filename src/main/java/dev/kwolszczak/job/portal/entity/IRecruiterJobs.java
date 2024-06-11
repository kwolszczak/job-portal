package dev.kwolszczak.job.portal.entity;

public interface IRecruiterJobs {

  Long getTotalCandidates();
  int getJob_post_id();
  int getLocationId();
  int getCompanyId();
  String getJob_title();
  String getCity();
  String getState();
  String getCountry();
  String getName();
}
