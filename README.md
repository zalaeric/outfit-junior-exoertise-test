# Outfit7 Backend junior-expertise-test project

This is my solution for the Outfi7 junior expertise test.

The backend is a simple matching web service that provides endpoints for retrieving opponents.

It consisted originaly of two main entry points:

- `UserFacade`: a utility endpoint to see all the data present in the database (DB is implemented as a simple json file)
- `MatchingFacade`: the main (matching) functionality

## My mission was to build the project, find and fix some bugs and implement a new matching service for ranked mode matching functionality.

### Build the project

The first part of my exercise was to fix the part of the code that was the reason for the project not building. I added the lombok plugin and fixed a failing test - UserServiceTest to look for the true exception, which was an instance of EntityNotFoundException class. I also corrected the json file representing the database, which contained an extra comma.

### Find and fix the bugs

The second part of my exercise was to fix the bug where the endpoint `/matching/classic/d7fc5c61-ac15-48ca-9b14-f3d8f55b1946` did not not work as it should as the service returned duplicate opponents, which were in the instructions opponents with the same name.
For this I added a unit test in class ClassicMatchingServiceTest, which specifically checked the player name attributes for duplicates in the returned opponents list. I continued onto debugging this by correcting the distinctByKey function in the ClassicMatchingService class which was wrongly comparing player names and IDs instead of player names to player names - a filter to compare the current user ID to opponent IDs was already implemented and working correctly. 
After this fix I also changed the first test in ClassicMatchingServiceTest (shouldRetrieveOpponentsForUserId) because it did not return four opponents with IDs 2, 3, 5, 6 anymore as opponents with IDs 5 and 6 have the same name, `name4`. It returned 3 opponents, the ones with IDs 2, 3 and 6.

### Implement a new matching service for ranked mode.

The third part of my exercise was to implement a new matching service for ranked mode following the pattern of ClassicMatchingService.

I implemented a new endpoint for ranked mode: `/matching/ranked/{userId}`. I added three new classes in the source code, RankedMatchingService, RankedOpponentsService and RankedMatchingFacade, and sorted them into separate packages from the classic matching service mode for better structure. I mainly followed the given code in classic matching service mode to keep up the consistency.

The main difference in ranked mode is that the matched opponents are also flitered out by rank, as they have to be in the range +/- 100 rank points from the user making the request. I added the field `rank` in the User object and added random ranks to players in the mockup tests. I also kept all other filters from the classic matching service to filter out duplicates by name, filter out the user against himself, and filter out opponents with powerLevel outside of a range +/- 15 from the powerLevel of the user making the request. 

Ranked mode returns 5 randomized opponents for the user making the request and returns an error if there is a discrepancy so the client will know to retry the request.

I also added unit tests in the test directory, RankedMatchingServiceTest, RankedOpponentsServiceTest and RankedMatchingFacadeTest, again sorted in separate packeges. 

## Building and running the application

You can test/build your application using:
```
./gradlew test
./gradlew build
```

You can run your application in dev mode that enables live coding using:
```
./gradlew quarkusDev
```

Check the output logs for an entry like this:
```
(Quarkus Main Thread) Quarkus 1.4.1.Final started in 0.977s. Listening on: http://0.0.0.0:8080
```

Point your browser to the following address, to see the service working:
```
http://0.0.0.0:8080/user
http://0.0.0.0:8080/user/f4afb2fd-b4b9-41fe-ab7c-55059870e78a
```

---
This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, visit its website: [quarkus.io](https://quarkus.io/).
