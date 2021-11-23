package com.example.security.domain.board.scrap;

import com.example.security.domain.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ScrapService extends BaseService<Scrap, Long> {

    private final ScrapRepository scrapRepository;

    public ScrapService(ScrapRepository scrapRepository) {
        super(scrapRepository);
        this.scrapRepository = scrapRepository;
    }
}
