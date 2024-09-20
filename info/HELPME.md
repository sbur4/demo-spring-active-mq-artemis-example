1. Docker:
- image-> apache/activemq-artemis:latest
- port-> 8161:8161 / 61616:61616 / 5445:5445
- http://localhost:8161
- 
2. How to run:
   + Application run as debug
   - gradle build -x test
   - in git bash ./gradlew build -x test
   - 
3. http://localhost:8080/send?message=abc
4. http://localhost:8080/sendAndReceive?message=aaaaA



