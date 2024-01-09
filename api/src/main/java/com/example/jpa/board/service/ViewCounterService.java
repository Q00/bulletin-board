package com.example.jpa.board.service;

import java.util.Map;

public interface ViewCounterService {
	void increase(Long id);
	Map<Long, Integer> getViewCountsAndReset();
}
