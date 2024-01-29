package com.nas.alreem.fragment.home.reenrollment;

import java.util.ArrayList;

public class EnrollmentFormResponseModel {
    private String responsecode;
    private ResponseData response;
    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public ResponseData getResponse() {
        return response;
    }

    public void setResponse(ResponseData response) {
        this.response = response;
    }

    public static class ResponseData {
        private String response;
        private String statuscode;
        private ResponseArray responseArray;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getStatuscode() {
            return statuscode;
        }

        public void setStatuscode(String statuscode) {
            this.statuscode = statuscode;
        }

        public ResponseArray getResponseArray() {
            return responseArray;
        }

        public void setResponseArray(ResponseArray responseArray) {
            this.responseArray = responseArray;
        }

        public static class ResponseArray{
            private Settings settings;
            private User user;
            private ArrayList<ReEnrollmentFormStudentModel> students;

            public Settings getSettings() {
                return settings;
            }

            public void setSettings(Settings settings) {
                this.settings = settings;
            }

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public ArrayList<ReEnrollmentFormStudentModel> getStudents() {
                return students;
            }

            public void setStudents(ArrayList<ReEnrollmentFormStudentModel> students) {
                this.students = students;
            }

            public static class Settings{
              private String heading;
              private String question;
              private String description;
              private String t_and_c;
              private ArrayList<String> options;

                public String getHeading() {
                    return heading;
                }

                public void setHeading(String heading) {
                    this.heading = heading;
                }

                public String getQuestion() {
                    return question;
                }

                public void setQuestion(String question) {
                    this.question = question;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getT_and_c() {
                    return t_and_c;
                }

                public void setT_and_c(String t_and_c) {
                    this.t_and_c = t_and_c;
                }

                public ArrayList<String> getOptions() {
                    return options;
                }

                public void setOptions(ArrayList<String> options) {
                    this.options = options;
                }
            }
          public static class User{
              private String firstname;
              private String email;

              public String getFirstname() {
                  return firstname;
              }

              public void setFirstname(String firstname) {
                  this.firstname = firstname;
              }

              public String getEmail() {
                  return email;
              }

              public void setEmail(String email) {
                  this.email = email;
              }
          }
        }

    }
    }
