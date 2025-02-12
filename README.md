# MGM User Management API
We want you to build a Microservice that exposes a couple endpoints to return user information. 
We also want a simple UI to display the information.

## Project Milestones and Issues
In order to track and organize the work, I have created [Milestones](https://github.com/gilfranfer/mgm-user-api/milestones) and [Issues](https://github.com/gilfranfer/mgm-user-api/milestone/1?closed=1) in the GitHub project repository.

## Endpoints
- Endpoint to return a specific user (and all associated information): GET /users/{id}
- Endpoint to return a list of users created between a date range: GET /users?startDate={start}&endDate={end}
- Endpoint to return a list of users based on a specific profession: GET /users?profession={profession}
- (Custom) Endpoint to create user: POST /users

Find [here](https://github.com/gilfranfer/mgm-user-api/blob/master/src/main/resources/API-Specification.yml) the API Specification file.
![API Specification](https://github.com/gilfranfer/mgm-user-api/blob/master/src/main/resources/images/BE-APISpecification.png)

## UI
The UI was intended to have its own repository, but I have just included the files here to keep it simple.

#### Main Page to list Users
[Link to code](https://github.com/gilfranfer/mgm-user-api/blob/master/index.html) 
![UI User List](https://github.com/gilfranfer/mgm-user-api/blob/master/src/main/resources/images/UI-UsersList.png)

#### User details page
[Link to code](https://github.com/gilfranfer/mgm-user-api/blob/master/user-details.html)
![UI User Details](https://github.com/gilfranfer/mgm-user-api/blob/master/src/main/resources/images/UI-UserDetails.png)


## Follow-up Questions

#### What did you think of the project?
Initially I thought it was going to be a very simple and quick project, but as I started thinking in all the things I wanted to cover (Milestones and Issues, API Specification File, Unit Testing, Jacoco, Docker), plus building a UI, I realized it was going to be a lot of work.
At the end I enjoyed working on this little project, but I would have preferred to do it over the weekend instead of at the beginning of a complicated week.

#### What didn’t you like about the project?
Honestly, I don't like doing projects as part of an interview process because I put a lot of time on them and I do not receive any feedback at all which makes me feel I just wasted my time. All that just based on my last year experiences. But this time I am looking forward to a different outcome and get some feedback.

#### How would you change the project or approach?
Right now, I think I wouldn't change my approach. I started by creating "issues" so I can plan ahead and remove things that could affect my deadline, like configuring docker.

#### Anything else you would like to share?
It was nice to get to work with the UI. I think it has been more than 2 years since I touched the front end code.
I faced issues with my IDE to get Lombok working, so I had to write the getter/setter methods and some other boilerplate. I am not sure why lombok annotations were not recognized for this project, but worked for another. I confirmed the plugin was installed and enabled. I was investing much time on that issue, so I left it for another day.
