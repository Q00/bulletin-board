package com.example.jpa.board.service;

import com.example.jpa.board.entity.Board;
import com.example.jpa.board.model.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BoardService {

    /**
     * 게시글 삭제
     */
    ServiceResult deleteBoard(String email, Long id);

    /**
     * 게시글 수정
     */
    ServiceResult editBoard(String email, Long id, BoardInput boardInput);

    /**
     * 게시글의 조회수 증가
     * */
    void setBoardHits(Map<Long, Integer> boardHits);
    /**
     * 게시글 상세 정보
     */
    BoardResponse detail(Long id);

    /**
     * 게시글 목록
     */
    Page<BoardResponse> list(Pageable pageable);


    /**
     * 게시글 작성
     */
    ServiceResult add(String email, BoardInput boardInput);
}
