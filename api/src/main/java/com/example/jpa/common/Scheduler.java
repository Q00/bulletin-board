package com.example.jpa.common;

import com.example.jpa.board.service.BoardService;
import com.example.jpa.board.service.ViewCounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

	private final ViewCounterService viewCounterService;
	private final BoardService boardService;

	@Scheduled(fixedDelay = 1000 * 30)
	public void batchUpdateViewCount() {
		Map<Long, Integer> counts = viewCounterService.getViewCountsAndReset();
	  boardService.setBoardHits(counts);
  }
}
