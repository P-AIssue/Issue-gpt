package proj.AIssue.domain.naver.entity;

import jakarta.persistence.*;
import lombok.*;
import proj.AIssue.global.common.BaseTimeEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Issue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 512)
    private String sourceUrl;  // 이슈 출처 URL

    @Enumerated(EnumType.STRING)
    private Category category;

}
