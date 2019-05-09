package com.company.rest.api

import com.company.model.Score
import com.company.model.Team
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class ScoreSerializer extends JsonSerializer<Score>{

	@Override
	public void serialize(Score score, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
		jgen.writeStartObject()
		
		jgen.writeNumberField("minutes", score.minutes)
		jgen.writeNumberField("secondes", score.secondes)
		
		jgen.writeEndObject()
	}
}