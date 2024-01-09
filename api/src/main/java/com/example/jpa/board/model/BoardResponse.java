package com.example.jpa.board.model;

import com.example.jpa.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class BoardResponse {
	private long id;
	private long userId;
	private String userName;

	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private Integer hits;

	public static BoardResponse of(Board board) {
		return BoardResponse.builder()
				.id(board.getId())
				.userId(board.getUser().getId())
				.userName(board.getUser().getUsername())
				.title(board.getTitle())
				.contents(board.getContents())
				.createdAt(board.getCreatedAt())
				.hits(board.getHits())
				.build();
	}

}
