package com.example.jpa.board.controller;

import com.example.jpa.board.entity.Board;
import com.example.jpa.board.model.*;
import com.example.jpa.board.service.BoardService;
import com.example.jpa.common.guard.CurrentUser;
import com.example.jpa.common.model.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
public class ApiBoardController {
    private final BoardService boardService;

    @GetMapping()
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {


        Pageable pageable = PageRequest.of(page, size);
        Page<BoardResponse> list = boardService.list(pageable);
        return ResponseResult.success(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {

        BoardResponse board = boardService.detail(id);
        return ResponseResult.success(board);
    }

    @PostMapping()
    public ResponseEntity<?> add(@CurrentUser UserDetails user,@RequestBody BoardInput boardInput) {

        ServiceResult result = boardService.add(user.getUsername(), boardInput);
        return ResponseResult.result(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CurrentUser UserDetails user, @PathVariable Long id) {

        ServiceResult result = boardService.deleteBoard(user.getUsername(), id);
        return ResponseResult.result(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@CurrentUser UserDetails user, @PathVariable Long id, @RequestBody BoardInput boardInput) {
        ServiceResult result = boardService.editBoard(user.getUsername(), id, boardInput);
        return ResponseResult.result(result);
    }
}



















