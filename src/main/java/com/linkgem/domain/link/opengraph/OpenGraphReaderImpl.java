package com.linkgem.domain.link.opengraph;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OpenGraphReaderImpl implements OpenGraphReader {

    private final OpenGraphCaller openGraphCaller;

    @Override
    public OpenGraph call(String url) {
        Optional<OpenGraphCaller.Result> result = openGraphCaller.call(url);

        return result.map(OpenGraphCaller.Result::to)
            .orElseGet(OpenGraph::createEmpty);
    }
}
