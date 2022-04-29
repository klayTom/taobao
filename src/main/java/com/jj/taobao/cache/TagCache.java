package com.jj.taobao.cache;

import com.jj.taobao.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDto> get() {
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto shoes = new TagDto();
        shoes.setCategoryName("鞋子");
        shoes.setTags(Arrays.asList("休闲鞋","运动鞋","板鞋","跑鞋","小白鞋"));
        tagDtos.add(shoes);

        TagDto cloths = new TagDto();
        cloths.setCategoryName("上衣");
        cloths.setTags(Arrays.asList("T恤男","T恤女","polo"));
        tagDtos.add(cloths);

        TagDto trousers = new TagDto();
        trousers.setCategoryName("裤子");
        trousers.setTags(Arrays.asList("短裤","运动长裤","速干裤","工装裤","束脚裤"));
        tagDtos.add(trousers);

        TagDto sweater = new TagDto();
        sweater.setCategoryName("卫衣");
        sweater.setTags(Arrays.asList("连帽卫衣","圆领卫衣"));
        tagDtos.add(sweater);
        return tagDtos;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
