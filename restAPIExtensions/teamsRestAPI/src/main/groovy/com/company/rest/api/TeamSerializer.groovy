package com.company.rest.api

import java.time.format.DateTimeFormatter

import com.company.model.Team
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class TeamSerializer extends JsonSerializer<Team>{

	@Override
	public void serialize(Team team, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
		jgen.writeStartObject()
		
		jgen.writeStringField("name", team.name)
		jgen.writeStringField("schedule", team.schedule.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
		jgen.writeNumberField("score", team.score)
		
		jgen.writeEndObject()
	}
}
