package com.mra.model;

import java.util.ArrayList;
import java.util.List;

public class PatternModelMapper {

	public static List<PatternsResponse> prepareResponseList(List<Patterns> patternsList)
	{
		List<PatternsResponse> responseList = new ArrayList<PatternsResponse>();
		for (Patterns patterns : patternsList) {
			PatternsResponse pr = new PatternsResponse();
			pr.setId(patterns.getId().intValue());
			pr.setName(patterns.getPattern_name());
			pr.setUrl("/api/pattern/download/"+patterns.getId());
			responseList.add(pr);
		}
		return responseList;
	}
	
	public static PatternsResponse prepareResponse(Patterns patterns)
	{
		
			PatternsResponse pr = new PatternsResponse();
			pr.setId(patterns.getId().intValue());
			pr.setName(patterns.getPattern_name());
			pr.setUrl("/api/pattern/download/"+patterns.getId());
			
		return pr;
	}
}
