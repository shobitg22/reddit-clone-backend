package com.reddit.clone.Application.service;

import com.reddit.clone.Application.dto.SubRedditDto;
import com.reddit.clone.Application.exception.NotFoundException;
import com.reddit.clone.Application.exception.SpringRedditException;
import com.reddit.clone.Application.mapper.SubRedditMapper;
import com.reddit.clone.Application.model.Subreddit;
import com.reddit.clone.Application.repository.SubRedditRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubRedditService {


    private final SubRedditRepo subRedditRepo;
    private final SubRedditMapper subRedditMapper;
    private final AuthService authService;

    @Transactional
    public SubRedditDto createSubReddit(SubRedditDto subRedditDto) throws NotFoundException {
        Subreddit subreddit= subRedditMapper.mapDtoToSubReddit(subRedditDto,authService.getCurrentUser());
        Optional<Subreddit> subredditOptional=subRedditRepo.findByName(subRedditDto.getName());
        if(subredditOptional.isPresent()){
            throw new NotFoundException("Subreddit with the name already exist");
        }
        subreddit=subRedditRepo.save(subreddit);
        subRedditDto.setId(subreddit.getId());
        return subRedditDto;
    }

    @Transactional(readOnly = true)
    public List<SubRedditDto> getAll() {
        return subRedditRepo.findAll().stream().map(subRedditMapper::mapSubRedditToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubRedditDto getById(Long id) throws SpringRedditException {
      Subreddit subreddit =  subRedditRepo.findById(id).orElseThrow(()->
              new SpringRedditException("No Subreddit found with "+id));
      return subRedditMapper.mapSubRedditToDto(subreddit);
    }


//    private SubRedditDto mapObjectToDto(Subreddit subreddit){
//        return SubRedditDto.builder()
//                .description(subreddit.getDescription())
//                .id(subreddit.getId())
//                .name(subreddit.getName())
//                .numberOfPosts(subreddit.getPosts().size()).build();
//    }
    //    private Subreddit DtoToSubRedditObject(SubRedditDto subRedditDto) {
//        return Subreddit.builder().name(subRedditDto.getName())
//                .description(subRedditDto.getDescription())
//                .build();
//    }
}
