package me.vitblokhin.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.vitblokhin.jwtappdemo.enums.Status;
import me.vitblokhin.jwtappdemo.model.AbstractEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AbstractDto implements Serializable {
    private Long id;
    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updatedAt;*/

    private Status status;

    public AbstractDto() {
    }

    public AbstractDto(AbstractEntity entity) {
        this.id = entity.getId();
        //this.createdAt = entity.getCreatedAt();
        //this.updatedAt = entity.getUpdatedAt();
        this.status = entity.getStatus();
    }
} // class AbstractDto
