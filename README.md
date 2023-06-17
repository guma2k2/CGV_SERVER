# CGV_SERVER
## This project using : 
### Backend: Spring Boot, Spring Security, Spring Data JPA, WebSocket 
### Frontend: Html, css, js(jquery), Thymeleaf
### Other: Docker(Deploy), MySQL(DATABASE)

# How to clone this project(SERVER) 
## Step 0 : Run Docker
## Step 1 : use cmd : git clone https://github.com/guma2k2/CGV_SERVER.git backend_movie
## Step 2 : use cmd : cd backend_movie
## Step 2 : Use cmd : docker pull thuanvn2002/movie-backend:latest
## Step 3 : You need to make sure that you are in this project and dont have any project run in port 8080. Ok, use cmd : docker-compose up 
## Step 4 : Check you are success -> paste this link in browser or postman : http://localhost:8080/api/v1/movies 
## Now you if you can see data, you got it <3

# How to clone this project(Client)
## Step 0 : You need to out project in server 
## Step 1 : Use cmd : docker pull thuanvn2002/movie-frontend:latest
## Step 2 : Use cmd : docker run -p 8000:8000 --net backend_movie-app-net thuanvn2002/movie-frontend
## Step 2.5: if the terminal tell you dont have any network `backend_movie-app-net` , you can use cmd : docker network ls . Now you need to find a network with name with prefix or suffix have `%movie%` -> copy this name and pass to the older net of `Step2`
## Step 3 : Check success, paste the link: http://localhost:8000/vincinema 
### If you were successful, you can login with :
#### + Customer => Account: thu2k2@gmail.com / Pass : thuan2023
#### + Admin => Account: thuanngo307202@gmail.com / Pass : thuan2023


