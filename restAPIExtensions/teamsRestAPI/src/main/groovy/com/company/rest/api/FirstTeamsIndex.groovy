package com.company.rest.api

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.bonitasoft.web.extension.rest.RestAPIContext
import com.bonitasoft.web.extension.rest.RestApiController
import com.company.model.Team
import com.company.model.TeamDAO

class FirstTeamsIndex implements RestApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirstTeamsIndex.class)
	private static final TeamMapper TEAM_MAPPER = new TeamMapper()
	private static final TeamComparator comparator = new TeamComparator()

	@Override
	RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
		List<Team> teams = context.getApiClient().getDAO(TeamDAO.class).find(0, 100)
				.findAll { it.score };

		teams.sort(comparator)
		def max = request.getParameter("max") as int
		if (teams.size() > max) {
			teams = teams.subList(0, max)
		}
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

class TeamComparator implements Comparator<Team> {

	@Override
	public int compare(Team team1, Team team2) {
		def score1 = team1.score.secondes + (team1.score.minutes * 60)
		def score2 = team2.score.secondes + (team2.score.minutes * 60)
		return score1.compareTo(score2);
	}
}
