# Booking movie ticket website

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
    - [Backend (Movie-api)]
    - [Frontend (Movie-ui)]
- [User interfaces](#User interfaces)
- [Demo](#Demo)
- [License](#license)
- [Getting Started](#getting-started)
- [Contributors](#contributors)

## Overview

The Booking movie website is a comprehensive full-stack application designed to booking movie ticket online. The project includes an Admin page for managing movies, cinemas, rooms, events and users. 
## Features
- Use Spring Security with JWT to secure user information.
- Use WebSocket to display real-time booking status.
- Use Spring Mail for account registration and password recovery.
- The project also builds an Admin page for managing movies, cinemas, rooms, events for booking movie.
- User with account can booking ticket online.
- Analytics: View statistics by custom date ranges.

## Technologies Used

### Backend (Elearning-be)

- Spring Boot 3
- Spring Security 6
- JWT Token Authentication
- Spring Data JPA
- JSR-303 and Spring Validation
- OpenAPI and Swagger UI Documentation
- Docker
- GitHub Action

### Frontend (Elearning-fe)

- Thymeleaf
- Bootstrap

## Demo
- Link: https://youtu.be/rFPXV3SIjfA

## User interfaces
- Ideas from https://www.cgv.vn

#### Home 
![ERD diagram](screenshots/Home.png)

#### Login
![ERD diagram](screenshots/Login.png)

#### Register
![ERD diagram](screenshots/Register.png)

#### Search movie ticket by cinema
![ERD diagram](screenshots/searchCinema1.png)

![ERD diagram](screenshots/searchCinema2.png)

#### Booking ticket
![ERD diagram](screenshots/bookingMovive.png)

#### Dashboard (Admin)
![ERD diagram](screenshots/Dashboard.png)

#### Manage movie
![ERD diagram](screenshots/ManageMovie.png)

#### Manage event
![ERD diagram](screenshots/ManageEvent.png)

## System design

#### ERD diagram
![ERD diagram](screenshots/ERD-diagram.png)

#### Usecasae diagram
![Security diagram](screenshots/Usecase-movie-diagram.png)




## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.

## Getting Started

To get started with the Elearning project, follow the setup instructions in the respective directories:
- Clone this project
- Run cmd : docker compose up -d --build

## Contributors

- [NGO DUC THUAN](https://github.com/guma2k2)


