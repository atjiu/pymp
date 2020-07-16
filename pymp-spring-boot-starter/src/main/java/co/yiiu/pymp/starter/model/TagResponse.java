package co.yiiu.pymp.starter.model;

import lombok.Data;

import java.util.List;

@Data
public class TagResponse {

    private Tag tag;
    private List<Tag> tags;

}
