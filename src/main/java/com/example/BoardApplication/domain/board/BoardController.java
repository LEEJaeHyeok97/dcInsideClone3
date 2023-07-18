package com.example.BoardApplication.domain.board;

import com.example.BoardApplication.domain.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/board") //대표주소 작성
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save")
    public ModelAndView renderSaveForm() {
        ModelAndView mav = new ModelAndView("save");
        return mav;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute BoardDTO boardDTO, HttpSession session, Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);

        //session에 저장된 loginId를 writer에 할당
        String writer = (String) session.getAttribute("loginId");
        boardDTO.setBoardWriter(writer);

        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }


    @GetMapping("/{id}")
    public ModelAndView findById(@PathVariable Long id, Model model) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        ModelAndView mav = new ModelAndView("detail");
        return mav;
    }

    //상세페이지 update 페이지로 이동
    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);

        ModelAndView mav = new ModelAndView("update");
        return mav;
    }

    //업데이트는 아직 미완성
    @PostMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
//        return "redirect:/board/" + boardDTO.getId(); 수정 시 조회수가 올라가는 문제 있음
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boardService.delete(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
