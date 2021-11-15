# 3141year
*The universe has been explored and divided. The Supreme Ruler appoints the Lords of the Planets, whose total number is in the millions. 
Experienced Lords can control several Planets at the same time. There is no democracy, so only one Lord can rule one planet. 
All this disgrace requires a system of accounting and supervision.*

# MySQL username and password prerequisites for project:

* username: 3141year
* password: 3141year
* db url: jdbc:mysql://localhost:3306/3141year


# Build and run projet via cmd:

* mvn clean compile package
* mvn spring-boot:run


# You can use Swagger link to investigate apis:
```
 http://localhost:3141/swagger-ui.html
```

# APIs:
### Lord

* POST/api/lord - add new lord

* POST/api/lord/{lordid}/planets/{planetid}/appoint - appoint lord to the planet

* GET/api/lord/idle-lords - get idle lords

* Get/api/lord/ten-youngest - get ten the youngest lords


### Planet

* POST/api/planet - add new planet

* DELETE/api/planet - delete the planet





