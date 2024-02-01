package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository;

    //http://localhost:8080?page=0
    @GetMapping({ "/", "/board" })
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        //위임
        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList", boardList);

        int currentPage = page;
        int nextPage = currentPage + 1;
        int prevPage = currentPage - 1;
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);
        boolean first = (currentPage == 0 ? true : false);

        int totalCount = boardRepository.count();
        int paging = 3;
        int totalPage = totalCount / paging;
        if (totalCount % paging != 0) {
            totalPage = 1 + (totalCount / paging);
        }
        boolean last = currentPage == (totalPage - 1) ? true : false;

        //t = 7, cp = 0, last = false
        //t = 7, cp = 1, last = false
        //t = 7, cp = 2, last = true

        request.setAttribute("first", first);
        request.setAttribute("last", last);

        return "index";
    }


    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/1")
    public String detail() {
        return "board/detail";
    }
}
