package com.company.rest.api;

import groovy.json.JsonBuilder

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.http.HttpHeaders
import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.bonitasoft.web.extension.rest.RestAPIContext
import com.bonitasoft.web.extension.rest.RestApiController
import com.company.model.Team
import com.company.model.TeamDAO

class TeamsWithoutScoreIndex implements RestApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeamsWithoutScoreIndex.class)
	private static final TeamMapper TEAM_MAPPER = new TeamMapper()

	@Override
	RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
		def teams = context.getApiClient().getDAO(TeamDAO.class).find(0, 100)
				.findAll { !it.score }
				.sort { team1, team2 -> team1.schedule.compareTo(team2.schedule) }

		def result = TEAM_MAPPER.writeValueAsString(teams)

		return buildResponse(responseBuilder, HttpServletResponse.SC_OK, result)
	}

	RestApiResponse buildResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Serializable body) {
		return responseBuilder.with {
			withResponseStatus(httpStatus)
			withResponse(body)
			build()
		}
	}

}
