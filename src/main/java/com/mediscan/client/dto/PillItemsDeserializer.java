package com.mediscan.client.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles MFDS API items field: single object or array both deserialize to List.
 */
public class PillItemsDeserializer extends JsonDeserializer<List<PublicDataPillItem>> {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public List<PublicDataPillItem> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		if (node == null || node.isNull()) {
			return List.of();
		}
		if (node.isArray()) {
			List<PublicDataPillItem> list = new ArrayList<>();
			for (JsonNode element : node) {
				list.add(MAPPER.treeToValue(element, PublicDataPillItem.class));
			}
			return list;
		}
		return List.of(MAPPER.treeToValue(node, PublicDataPillItem.class));
	}
}
