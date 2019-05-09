package com.company.rest.api

import com.company.model.Score
import com.company.model.Team
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule

class TeamMapper extends ObjectMapper {
	
	public TeamMapper () {
		SimpleModule module = new SimpleModule()
		module.addSerializer(Team.class, new TeamSerializer())
		module.addSerializer(Score.class, new ScoreSerializer())
		registerModule(module)
	}
}
