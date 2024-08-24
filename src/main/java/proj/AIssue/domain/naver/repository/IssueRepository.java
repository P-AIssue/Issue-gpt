package proj.AIssue.domain.naver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.AIssue.domain.naver.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
