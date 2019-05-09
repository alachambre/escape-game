
package com.company.rest.api

import java.time.LocalDateTime

import javax.servlet.http.HttpServletRequest
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.bonitasoft.web.extension.rest.RestApiResponse
import com.bonitasoft.engine.api.APIClient
import com.bonitasoft.web.extension.rest.RestAPIContext
import com.company.model.Score
import com.company.model.Team
import com.company.model.TeamDAO

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import spock.lang.Specification

class FirstTeamsIndexTest extends Specification {

    def httpRequest = Mock(HttpServletRequest)
    def context = Mock(RestAPIContext)
	def apiClient = Mock(APIClient)
	def teamDAO = Mock(TeamDAO)

    def setup(){
		context.getApiClient() >> apiClient
		apiClient.getDAO(TeamDAO.class) >> teamDAO
		httpRequest.getParameter("max") >> 3
		def now = LocalDateTime.now()
		def teams = [createTeam("team1", now, 5, 20),
			createTeam("team2", now, 4, 10),
			createTeam("team3", now, 6, 24),
			createTeam("team4", now, 2, 50),
			createTeam("team5", now, 8, 34),
			createTeam("team6", now, 4, 9)]
		teamDAO.find(0, 100) >> teams
    }

    def should_return_three_first_teams() {
        given: "The team controller"
        def index = new FirstTeamsIndex()

        when: "Retrieving three first teams"
        def apiResponse = index.doHandle(httpRequest, new RestApiResponseBuilder(), context)

        then: "Assert the response only contains three first teams, sorted correctly"
        def jsonResponse = new JsonSlurper().parseText(apiResponse.response)
		jsonResponse.size == 3
		jsonResponse[0].name == "team4"
		jsonResponse[1].name == "team6"
		jsonResponse[2].name == "team2"
    }
	
	private Team createTeam(String name, LocalDateTime schedule, int minutes, int secondes) {
		def score = new Score()
		score.minutes = minutes
		score.secondes = secondes
		
		def team = new Team()
		team.name = name
		team.schedule = schedule
		team.score = score
		team
	}
}