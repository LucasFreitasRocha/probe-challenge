package com.ta2.probechallenge.probe.entity;

import com.ta2.probechallenge.probe.domain.ProbeDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "probe")
public class ProbeEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;
    private Integer x;
    private Integer y;
    private String position;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    public static ProbeEntity from(ProbeDomain probeDomain) {
        return ProbeEntity
                .builder()
                .id(probeDomain.getId())
                .name(probeDomain.getName())
                .code(probeDomain.getCode())
                .y(probeDomain.getY())
                .x(probeDomain.getX())
                .position(probeDomain.getPosition())
                .createAt(probeDomain.getCreateAt())
                .updateAt(probeDomain.getUpdateAt())
                .build();
    }
}
