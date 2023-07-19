package com.example.BoardApplication.domain.board;

import com.example.BoardApplication.domain.board.dto.BoardDTO;
import com.example.BoardApplication.domain.board.entity.BoardEntity;
import com.example.BoardApplication.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    EntityManager em;
    private final BoardRepository boardRepository;
    public Long save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
        return boardEntity.getId();
    }

    public List<BoardDTO> findAll() { //엔티티로 받아온 객체를 DTO로 변환해서 옮겨줘야한다.
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        //엔티티 객체를 DTO객체로 옮겨 담는다.
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }

        return boardDTOList;
    }




    @Transactional //jpa에 수동적으로 추가해준 메서드는 Transactional을 붙여준다.
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }


    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        //jpa에는 업데이트를 위한 메서드가 따로 제공되지 않는다. save 메서드를 활용해서 update, insert를 가능하게 한다.(id 유무로 기능 구분)
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());

    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        //한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬한다는 의미
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        //Page 에서 제공해주는 map 메서드를 활용한다
        // 목록: id, writer, title, hits, createdTime
        //페이지 객체를 변환해냈다. 이제 이걸 컨트롤러로 보낸다
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }

    public Page<BoardDTO> paging5(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 5; // 한 페이지에 보여줄 글 갯수
        //한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬한다는 의미
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        //Page 에서 제공해주는 map 메서드를 활용한다
        // 목록: id, writer, title, hits, createdTime
        //페이지 객체를 변환해냈다. 이제 이걸 컨트롤러로 보낸다
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }

    public Page<BoardDTO> paging10(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10; // 한 페이지에 보여줄 글 갯수
        //한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬한다는 의미
        // page 위치에 있는 값은 0부터 시작
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        //Page 에서 제공해주는 map 메서드를 활용한다
        // 목록: id, writer, title, hits, createdTime
        //페이지 객체를 변환해냈다. 이제 이걸 컨트롤러로 보낸다
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }
}
