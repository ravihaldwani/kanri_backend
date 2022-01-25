package com.kanrisoft.kanri.board.domain;

public record BoardId(Long id) {
    public static BoardId of(Long id) {
        return new BoardId(id);
    }
}
