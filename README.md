# CGV_SERVER
## This project using : 
### Backend: Spring Boot, Spring Security, Spring Data JPA, WebSocket 
### Frontend: Html, css, js(jquery), Thymeleaf
### Other: Docker(Deploy), MySQL(DATABASE)

# How to clone this project(SERVER) 
## Step 1 : Clone this project(Git) in terminal.
## Step 2 : Use cmd : docker pull thuanvn2002/movie-backend:latest
## Step 3 : You need to make sure that you are in this project and dont have any project run in port 8080. Ok, use cmd : docker-compose up 
## Step 4 : Check you are success -> paste this link in browser or postman : http://localhost:8080/api/v1/movies 
## Now you if you can see data, you got it <3

# How to clone this project(Client)
## Step 0 : You need to out project in server and clone this git (Client): https://github.com/guma2k2/CGV_CLIENT
## Step 1 : Use cmd : docker pull thuanvn2002/movie-frontend:latest
## Step 2 : Use cmd : docker run -p 8000:8000 --net backend_movie-app-net  thuanvn2002/movie-frontend
## Step 3 : Check success, past the link: http://localhost:8000/vincinema 


