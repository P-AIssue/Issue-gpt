package proj.AIssue.domain.naver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import proj.AIssue.domain.naver.dto.IssueDTO;
import proj.AIssue.domain.naver.service.IssueService;
import proj.AIssue.global.exception.dto.ResponseVO;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/news")
    public ResponseVO<List<IssueDTO>> getNews(@RequestParam(value = "query") String query) {
        List<IssueDTO> newsList = issueService.searchNews(query);
        return new ResponseVO<>(newsList);
    }
}
