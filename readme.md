# Bonita escape game application

This project is intended to be used to manage the escape game stand during Bonitasoft events


### Project content

* A business data model, which represent a Team
* Two processes, one to create a Team and schedule the game, and one to enter a score after a game.
* A living application, to display results, register new teams and enter scores
* An other living application, used internally, to display Team contact informations (in order to contact the winner)
* An other living application, used during the escape game
* A REST API Extension used by the application page, which return the x best teams of the day (x integer > 0)
* An other REST API Extension, which return the teams without score (i.e the scheduled teams) of the day, sorted by schedule.

-> Do not forget to configure the processes and to set REST API permissions!
