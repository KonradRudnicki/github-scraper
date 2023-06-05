

# github-scraper


## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Usage](#usage)



## General Information
> Java and Spring application created in order to retrieve information from Github API - getting all repositories from given user that are not forked and giving information about it's branches.


## Technologies Used
- Java - version 17
- Spring Boot - version 3.1.0


## Features
- Retrieving data about given user from Github
- Listing out all his repositories that are not forked
- Listing out all the branches from each repository with it's last commit's sha


## Usage
In order to get informations that are above-mentioned send 
GET request with URL:

`http://localhost:8080/repos/{username}`

Replace `{username}` with the Github username. 
If the user doesn't exist application gives 404 error.

The application accepts requests with 
`Content-type: application/json header`
Otherwise it gives 406 error.