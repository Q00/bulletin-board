package com.example.jpa.board.repository;

import com.example.jpa.board.entity.Board;
import com.example.jpa.board.model.ServiceResult;
import com.example.jpa.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b where b.isDeleted = false order by b.id desc")
    Page<Board> findAllByIsDeletedFalse(Pageable pageable);

    @Query("update Board b set b.isDeleted = true where b.id = :id")
    @Modifying
    void softDelete(@Param("id") Long id);
}
