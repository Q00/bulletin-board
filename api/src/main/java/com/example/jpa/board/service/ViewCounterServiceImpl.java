package com.example.jpa.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ViewCounterServiceImpl implements ViewCounterService{
	private final ConcurrentHashMap<Long, AtomicInteger> viewCounts = new ConcurrentHashMap<>();

	public void increase(Long boardId) {
		viewCounts.computeIfAbsent(boardId, k -> new AtomicInteger(0)).incrementAndGet();
	}

	public Map<Long, Integer> getViewCountsAndReset() {
		Map<Long, Integer> counts = new HashMap<>();
		viewCounts.forEach((key, value) -> {
			counts.put(key, value.getAndSet(0));
		});
		return counts;
	}
}
