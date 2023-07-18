package com.example.BoardApplication.domain.member;

import com.example.BoardApplication.domain.board.BoardService;
import com.example.BoardApplication.domain.board.dto.BoardDTO;
import com.example.BoardApplication.domain.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    //생성자 주입
    private final MemberService memberService;
    private final BoardService boardService;


    //회원 가입 페이지 요청 출력
    @GetMapping("/save")
    public ModelAndView saveForm() {
        ModelAndView mav = new ModelAndView("userSave");
        return mav;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute MemberDTO memberDTO) {
        ModelAndView mav = new ModelAndView("index");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/login") //세션 정보 저장 (인가)
    public ResponseEntity<?> login(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);

        ModelAndView success = new ModelAndView("index");
        ModelAndView fail = new ModelAndView("login");
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            //login 성공
            session.setAttribute("loginId", loginResult.getMemberId()); // 세션 정보 담아준다.
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/"));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        } else {
            //login 실패
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/board/login"));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
        }

    }

    @GetMapping("/login")
    public ModelAndView loginForm() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?>  logout(HttpSession session, Model model) {

        session.invalidate();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
