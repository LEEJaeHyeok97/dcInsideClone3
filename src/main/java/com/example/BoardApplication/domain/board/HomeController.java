package com.example.BoardApplication.domain.board;

import com.example.BoardApplication.domain.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;
    @GetMapping("/")
    public ModelAndView index(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);

        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}
