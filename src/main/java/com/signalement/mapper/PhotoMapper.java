package com.signalement.mapper;

import com.signalement.dto.PhotoDto;
import com.signalement.entity.Photo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PhotoMapper {
    PhotoDto toPhotoDto(Photo photo);

    Photo fromPhotoDto(PhotoDto photoDto);
}
