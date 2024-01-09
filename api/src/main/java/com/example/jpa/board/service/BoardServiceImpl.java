package com.example.jpa.board.service;

import com.example.jpa.board.entity.*;
import com.example.jpa.board.model.*;
import com.example.jpa.board.repository.*;
import com.example.jpa.common.exception.AccessDeniedException;
import com.example.jpa.common.exception.BizException;
import com.example.jpa.common.exception.NotFoundException;
import com.example.jpa.user.entity.User;
import com.example.jpa.user.model.UserRole;
import com.example.jpa.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ViewCounterService viewCounterService;

	@Override
  @Transactional
  public ServiceResult deleteBoard(String email, Long id) {
      Optional<Board> boardOptional = boardRepository.findById(id);

      if (!boardOptional.isPresent() || boardOptional.get().isDeleted()) {
          throw new NotFoundException("게시글이 존재하지 않습니다.");
      }

      Optional<User> optionalUser = userRepository.findByEmail(email);
      if (!optionalUser.isPresent()) {
          throw new NotFoundException("회원 정보가 존재하지 않습니다.");
      }

      Board board = boardOptional.get();
      User user = optionalUser.get();
      User author = board.getUser();

      if (!user.getEmail().equals(author.getEmail()) && user.getRole().contains(UserRole.NORMAL)) {
          throw new AccessDeniedException("게시글 삭제 권한이 없습니다."); // Assuming the board has a method getAuthorEmail()
      }

      boardRepository.softDelete(id);
      return new ServiceResult(true, "게시글이 삭제되었습니다.");
  }

    @Override
    @Transactional
    public ServiceResult editBoard(String email, Long id, BoardInput boardInput) {
        Optional<Board> boardOptional = boardRepository.findById(id);

        if (!boardOptional.isPresent() || boardOptional.get().isDeleted()) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Board board = boardOptional.get();
        User user = optionalUser.get();
        User author = board.getUser();

        if (!user.getEmail().equals(author.getEmail()) && user.getRole().contains(UserRole.NORMAL)) {
            throw new AccessDeniedException("게시글 수정 권한이 없습니다."); // Assuming the board has a method getAuthorEmail()
        }

        board.setContents(boardInput.getContents());
        boardRepository.save(board);

        return ServiceResult.success();
    }

    @Override
    public void setBoardHits(Map<Long, Integer> boardHits) {
        List<Board> boardsToUpdate = new ArrayList<>();

        boardHits.forEach((boardId, hits) ->
                boardRepository.findById(boardId).ifPresent(board -> {
                    board.setHits(board.getHits() + hits);
                    boardsToUpdate.add(board);
                })
        );

        boardRepository.saveAll(boardsToUpdate); // Batch save
    }

	@Override
    public BoardResponse detail(Long id) {

        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }
        viewCounterService.increase(id);
        Board board = optionalBoard.get();
        return BoardResponse.of(board);
    }

    @Override
    public Page<BoardResponse> list(Pageable pageable) {
        Page<Board> pb = boardRepository.findAllByIsDeletedFalse(pageable);
        return pb.map(BoardResponse::of);
    }

    @Override
    public ServiceResult add(String email, BoardInput boardInput) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        Board board = Board.builder()
                .user(user)
                .title(boardInput.getTitle())
                .contents(boardInput.getContents())
                .createdAt(LocalDateTime.now())
                .hits(0)
                .build();

        boardRepository.save(board);
        return ServiceResult.success();
    }
}
