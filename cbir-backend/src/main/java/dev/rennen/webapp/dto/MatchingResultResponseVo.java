package dev.rennen.webapp.dto;

import lombok.Data;

@Data
public class MatchingResultResponseVo implements Comparable<MatchingResultResponseVo> {
    @Override
    public int compareTo(MatchingResultResponseVo o) {
        return Double.compare(this.similarity, o.similarity);
    }

    private Long imageId;

    private String path;

    private Double similarity;

}
